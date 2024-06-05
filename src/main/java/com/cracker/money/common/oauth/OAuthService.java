package com.cracker.money.common.oauth;

import com.cracker.money.common.Constant.SocialLoginProvider;
import com.cracker.money.common.exception.BaseException;
import com.cracker.money.src.user.UserService;
import com.cracker.money.src.user.entity.User;
import com.cracker.money.src.user.model.*;
import com.cracker.money.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cracker.money.common.response.BaseResponseStatus.INVALID_OAUTH_TYPE;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final UserService userService;
    private final JwtService jwtService;


    public void accessRequest(SocialLoginProvider provider) throws IOException {
        String redirectURL;
        switch (provider){
            case GOOGLE:{
                System.out.println("google");
                redirectURL= googleOauth.getOauthRedirectURL();
            }break;

            default:{
                throw new BaseException(INVALID_OAUTH_TYPE);
            }

        }

        response.sendRedirect(redirectURL);
    }


    public GetSocialOAuthRes oAuthLoginOrJoin(SocialLoginProvider socialLoginType, String code) throws IOException {

        switch (socialLoginType) {
            case GOOGLE: {
                ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
                GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

                ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
                GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

                if (userService.checkEmailDuplicate(googleUser.getEmail())) {
                    GetUserRes getUserRes = userService.getUserByEmail(googleUser.getEmail());

                    String jwtToken = jwtService.createJwt(getUserRes.getId());

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(jwtToken, getUserRes.getId(), oAuthToken.getAccess_token(), oAuthToken.getToken_type());
                    return getSocialOAuthRes;
                } else {
                    PostUserRes postUserRes = userService.createOAuthUser(googleUser.toEntity());
                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(postUserRes.getJwt(), postUserRes.getId(), oAuthToken.getAccess_token(), oAuthToken.getToken_type());
                    return getSocialOAuthRes;
                }
            }
            default: {
                throw new BaseException(INVALID_OAUTH_TYPE);
            }

        }
    }
}
