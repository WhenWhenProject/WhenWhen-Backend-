package backend.initializer;

import backend.api.entity.*;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.api.repository.user_refresh_token.UserRefreshTokenRepository;
import backend.config.properties.AppProperties;
import backend.oauth.entity.RoleType;
import backend.token.AuthToken;
import backend.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final PlanRepository planRepository;
        private final JoinRepository joinRepository;
        private final AuthTokenProvider tokenProvider;
        private final AppProperties appProperties;
        private final UserRefreshTokenRepository userRefreshTokenRepository;

        public void dbInit() {
            // 유저 등록
            User user1 = User.builder()
                    .username("abc123")
                    .password(passwordEncoder.encode("1234"))
                    .nickName("최한영")
                    .email("abc@naver.com")
                    .roleType(RoleType.USER)
                    .build();

            User user2 = User.builder()
                    .username("qwe123")
                    .password(passwordEncoder.encode("1234"))
                    .nickName("권순우")
                    .email("abc@naver.com")
                    .roleType(RoleType.USER)
                    .build();

            userRepository.save(user1);
            userRepository.save(user2);

            // 유저 리프레시 토큰 등록
            // 리프레시 토큰 db 저장 및 쿠키에 담기
            Date now = new Date();
            AuthToken refreshToken1 = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
            );

            UserRefreshToken userRefreshToken1 = UserRefreshToken.builder()
                    .username(user1.getUsername())
                    .refreshToken(refreshToken1.getToken())
                    .build();

            AuthToken refreshToken2 = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + 10 + appProperties.getAuth().getRefreshTokenExpiry())
            );

            UserRefreshToken userRefreshToken2 = UserRefreshToken.builder()
                    .username(user2.getUsername())
                    .refreshToken(refreshToken2.getToken())
                    .build();

            userRefreshTokenRepository.save(userRefreshToken1);
            userRefreshTokenRepository.save(userRefreshToken2);

            // 플랜 등록
            Plan plan1 = Plan.builder()
                    .host(user1)
                    .title("동아리")
                    .startDate(LocalDate.of(2021, 3, 5))
                    .endDate(LocalDate.of(2021, 3, 7))
                    .expectedMemberCnt(2L)
                    .location("왕십리")
                    .startHour(16)
                    .build();

            planRepository.save(plan1);

            // 투표 정보 등록
            Join join1 = Join.builder()
                    .user(user1)
                    .plan(plan1)
                    .build();

            join1.addJoinInfo(
                    JoinInfo.builder()
                            .join(join1)
                            .localDate(LocalDate.of(2021, 3, 5))
                            .startHour(17)
                            .endHour(21)
                            .build()
            );

            join1.addJoinInfo(
                    JoinInfo.builder()
                            .join(join1)
                            .localDate(LocalDate.of(2021, 3, 6))
                            .startHour(18)
                            .endHour(22)
                            .build()
            );

            join1.addJoinInfo(
                    JoinInfo.builder()
                            .join(join1)
                            .localDate(LocalDate.of(2021, 3, 7))
                            .startHour(19)
                            .endHour(23)
                            .build()
            );

            Join join2 = Join.builder()
                    .user(user2)
                    .plan(plan1)
                    .build();

            join2.addJoinInfo(
                    JoinInfo.builder()
                            .join(join2)
                            .localDate(LocalDate.of(2021, 3, 5))
                            .startHour(16)
                            .endHour(17)
                            .build()
            );

            join2.addJoinInfo(
                    JoinInfo.builder()
                            .join(join2)
                            .localDate(LocalDate.of(2021, 3, 6))
                            .startHour(17)
                            .endHour(18)
                            .build()
            );

            join2.addJoinInfo(
                    JoinInfo.builder()
                            .join(join2)
                            .localDate(LocalDate.of(2021, 3, 7))
                            .startHour(18)
                            .endHour(19)
                            .build()
            );

            joinRepository.save(join1);
            joinRepository.save(join2);
        }

    }

}