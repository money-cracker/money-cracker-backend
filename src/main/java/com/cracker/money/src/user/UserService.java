package com.cracker.money.src.user;
import com.cracker.money.common.entity.BaseEntity;
import com.cracker.money.common.exception.BaseException;
import com.cracker.money.src.user.entity.User;
import com.cracker.money.src.user.model.*;
import com.cracker.money.utils.JwtService;
import com.cracker.money.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.cracker.money.common.response.BaseResponseStatus.*;
import static com.cracker.money.src.user.entity.User.UserStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByEmailAndStatus(email, BaseEntity.Status.ACTIVE);
    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) {

        if (!postUserReq.getIsOAuth()) {
            String encryptPwd;
            try {
                encryptPwd = new SHA256().encrypt(postUserReq.getPassword());
                postUserReq.setPassword(encryptPwd);
            } catch (Exception exception) {
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }
        }

        postUserReq.setUserStatus(ACTIVE);

        User savedUser;

        if (postUserReq.getIsOAuth()) {

            Long jwtUserId = jwtService.getUserId();
            postUserReq.setPassword("NONE");
//            modifyUserBasicInfo(jwtUserId, postUserReq);
//
            savedUser = userRepository.findByIdAndStatus(jwtUserId, BaseEntity.Status.ACTIVE)
                    .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        } else {
            savedUser = userRepository.save(postUserReq.toEntity());
        }

        String jwtToken = jwtService.createJwt(savedUser.getId());
        return new PostUserRes(savedUser.getId(), jwtToken);

    }

    public PostUserRes createOAuthUser(User user) {
        User saveUser = userRepository.save(user);

        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserRes(saveUser.getId(), jwtToken);
    }

    public GetUserRes getUserByEmail(String email) {
        User user = userRepository.findByEmailAndStatus(email, BaseEntity.Status.ACTIVE
        ).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }
}
