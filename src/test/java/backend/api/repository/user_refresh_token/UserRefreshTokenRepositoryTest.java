package backend.api.repository.user_refresh_token;

import backend.api.entity.*;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
@Transactional
class UserRefreshTokenRepositoryTest {

    @Autowired
    UserRefreshTokenRepository userRefreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    JoinRepository joinRepository;

    @Autowired
    EntityManager em;

    @Test
    void test() {
        User user = userRepository.findByUsername("abc123").get();
        Plan plan = planRepository.findByTitle("동아리").get();

        em.clear();

        System.out.println("=============================");

        Join join = joinRepository.findByUsernameAndPlanId(user.getUsername(), plan.getId()).get();
        for (JoinInfo joinInfo : join.getJoinInfoList()) {
            System.out.println(joinInfo.getId());
        }
        System.out.println("=============================");
//
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUsername("abc123").get();
//
//        em.clear();
//
//        userRefreshTokenRepository.findByUserAndRefreshToken(user, userRefreshToken.getRefreshToken());
////
//        Optional<UserRefreshToken> userRefreshToken = (Optional<UserRefreshToken>) userRefreshToken1;
//        userRefreshTokenRepository.findByUserAndRefreshToken(user, user
//
//
//        Optional<UserRefreshToken> result = userRefreshTokenRepository.findByUsername("abc123");
//        System.out.println(result);
//




//
//
//
//        UserRefreshToken
//
//
//        // 유저 등록
//        User user1 = User.builder()
//                .username("abc123")
//                .password(passwordEncoder.encode("1234"))
//                .nickName("최한영")
//                .email("abc@naver.com")
//                .roleType(RoleType.USER)
//                .build();
//
//        User user2 = User.builder()
//                .username("qwe123")
//                .password(passwordEncoder.encode("1234"))
//                .nickName("권순우")
//                .email("abc@naver.com")
//                .roleType(RoleType.USER)
//                .build();
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//        // 유저 리프레시 토큰 등록
//        // 리프레시 토큰 db 저장 및 쿠키에 담기
//        Date now = new Date();
//
//        AuthToken refreshToken1 = tokenProvider.createAuthToken(
//                appProperties.getAuth().getTokenSecret(),
//                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
//        );
//
//        UserRefreshToken userRefreshToken1 = UserRefreshToken.builder()
//                .username(user1.getUsername())
//                .refreshToken(refreshToken1.getToken())
//                .build();
//
//        AuthToken refreshToken2 = tokenProvider.createAuthToken(
//                appProperties.getAuth().getTokenSecret(),
//                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
//        );
//
//        UserRefreshToken userRefreshToken2 = UserRefreshToken.builder()
//                .username(user2.getUsername())
//                .refreshToken(refreshToken2.getToken())
//                .build();
//
//        userRefreshTokenRepository.save(userRefreshToken1);
//        userRefreshTokenRepository.save(userRefreshToken2);
//
//        // 플랜 등록
//        Plan plan1 = Plan.builder()
//                .host(user1)
//                .title("동아리")
//                .startDate(LocalDate.of(2021, 3, 5))
//                .endDate(LocalDate.of(2021, 3, 7))
//                .expectedMemberCnt(2L)
//                .location("왕십리")
//                .startHour(16)
//                .build();
//
//        planRepository.save(plan1);
//
//        // 투표 정보 등록
//        Join join1 = Join.builder()
//                .user(user1)
//                .plan(plan1)
//                .build();
//
//        join1.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 5))
//                        .startHour(17)
//                        .endHour(21)
//                        .build()
//        );
//
//        join1.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 6))
//                        .startHour(18)
//                        .endHour(22)
//                        .build()
//        );
//
//        join1.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 7))
//                        .startHour(19)
//                        .endHour(23)
//                        .build()
//        );
//
//        Join join2 = Join.builder()
//                .user(user2)
//                .plan(plan1)
//                .build();
//
//        join2.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 5))
//                        .startHour(16)
//                        .endHour(17)
//                        .build()
//        );
//
//        join2.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 6))
//                        .startHour(17)
//                        .endHour(18)
//                        .build()
//        );
//
//        join2.getJoinInfoList().add(
//                JoinInfo.builder()
//                        .localDate(LocalDate.of(2021, 3, 7))
//                        .startHour(18)
//                        .endHour(19)
//                        .build()
//        );
//
//        joinRepository.save(join1);
//        joinRepository.save(join2);
    }



}