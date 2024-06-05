package com.cracker.money.src.user;
import com.cracker.money.common.exception.BaseException;
import com.cracker.money.common.oauth.OAuthService;
import com.cracker.money.common.response.BaseResponse;
import com.cracker.money.src.user.model.GetSocialOAuthRes;
import com.cracker.money.src.user.model.PostUserReq;
import com.cracker.money.src.user.model.PostUserRes;
import com.cracker.money.common.Constant.SocialLoginProvider;
import com.cracker.money.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.cracker.money.common.response.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/v1/user")
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;
    private final JwtService jwtService;

    /**
     * Create User API
     * [POST] /app/user
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @Validated
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody @Validated PostUserReq postUserReq) {

        if(userService.checkEmailDuplicate(postUserReq.getEmail())) {
          throw new BaseException(DUPLICATED_EMAIL);
        }

        if(postUserReq.getPassword() == null){
            return new BaseResponse<>(USERS_EMPTY_PASSWORD);
        } else if (postUserReq.getPassword().length() < 6) {
            return new BaseResponse<>(POST_USERS_RECEDES_MIN_LEN_PASSWORD);
        }
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new BaseResponse<>(postUserRes);

//        // 소셜 로그인
//        if (postUserReq.getIsOAuth()){
//            Long jwtUserId = jwtService.getUserId();
//            GetUserRes userRes = userService.getActiveUserById(jwtUserId);
//
//            switch (userRes.getUserState()) {
//                case PENDING:
//                    PostUserRes postUserRes = userService.createUser(postUserReq);
//                    return new BaseResponse<>(postUserRes);
//                case ACTIVE:
//                    return new BaseResponse<>(EXISTING_USER);
//                case BLOCKED:
//                    return new BaseResponse<>(BLOCKED_USER);
//                case DORMANT:
//                    return new BaseResponse<>(DORMANT_USER);
//                default:
//                    throw new IllegalStateException("Unexpected user state: " + userRes.getUserState());
//            }
//        } else {
//            if(postUserReq.getPassword() == null){
//                return new BaseResponse<>(USERS_EMPTY_PASSWORD);
//            } else if (postUserReq.getPassword().length() < 6) {
//                return new BaseResponse<>(POST_USERS_RECEDES_MIN_LEN_PASSWORD);
//            }
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        }

    }

    /**
     * [GET] /app/users/auth/:socialLoginType/login
     * @return void
     */
    @GetMapping("/auth/{socialLoginType}/login")
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        System.out.println("herreee");
        SocialLoginProvider socialLoginType= SocialLoginProvider.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.accessRequest(socialLoginType);
    }

    /**
     * @param socialLoginPath (GOOGLE - for now)
     * @param code code passed by API server
     */
    @ResponseBody
    @GetMapping(value = "/auth/{socialLoginType}/login/callback")
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException, BaseException{
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginProvider socialLoginProvider = SocialLoginProvider.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginProvider,code);
        return new BaseResponse<>(getSocialOAuthRes);
    }

}
