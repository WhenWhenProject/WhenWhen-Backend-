package backend.initializer;

import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import backend.api.entity.Plan;
import backend.api.entity.User;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.oauth.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

// @Component
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

            // 플랜 등록
            Plan plan2 = Plan.builder()
                    .host(user1)
                    .title("소모임")
                    .startDate(LocalDate.of(2021, 2, 5))
                    .endDate(LocalDate.of(2021, 2, 7))
                    .expectedMemberCnt(3L)
                    .location("건대입구")
                    .startHour(15)
                    .build();

            planRepository.save(plan2);

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

            Join join3 = Join.builder()
                    .user(user1)
                    .plan(plan2)
                    .build();

            join3.addJoinInfo(
                    JoinInfo.builder()
                            .join(join3)
                            .localDate(LocalDate.of(2021, 2, 5))
                            .startHour(17)
                            .endHour(21)
                            .build()
            );

            join3.addJoinInfo(
                    JoinInfo.builder()
                            .join(join3)
                            .localDate(LocalDate.of(2021, 2, 6))
                            .startHour(18)
                            .endHour(22)
                            .build()
            );

            join3.addJoinInfo(
                    JoinInfo.builder()
                            .join(join3)
                            .localDate(LocalDate.of(2021, 2, 7))
                            .startHour(19)
                            .endHour(23)
                            .build()
            );

            joinRepository.save(join1);
            joinRepository.save(join2);
            joinRepository.save(join3);
        }

    }

}