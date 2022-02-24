package backend.api.repository.user_refresh_token;

import backend.api.controller.dto.request.JoinInfoRequest;
import backend.api.entity.*;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.api.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@Transactional
@Rollback(value = false)
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

    @Autowired
    JoinService joinService;

    @Test
    void test() {
        User user = userRepository.findById(1L).get();
        Plan plan = planRepository.findById(1L).get();
        em.clear();

        System.out.println("=============================");

        Join join = joinRepository.findByUsernameAndPlanId(user.getUsername(), plan.getId()).get();
        for (JoinInfo joinInfo : join.getJoinInfoList()) {
            System.out.println(joinInfo.getId());
        }
        System.out.println("=============================");
    }

    @Test
    void test2() {
        User user = userRepository.findById(1L).get();
        Plan plan = planRepository.findById(1L).get();
        em.clear();

        JoinInfoRequest joinInfoRequest1 = new JoinInfoRequest();
        joinInfoRequest1.setLocalDate(LocalDate.of(1921, 2, 5));
        joinInfoRequest1.setStartHour(17);
        joinInfoRequest1.setEndHour(21);

        JoinInfoRequest joinInfoRequest2 = new JoinInfoRequest();
        joinInfoRequest2.setLocalDate(LocalDate.of(1921, 3, 6));
        joinInfoRequest2.setStartHour(18);
        joinInfoRequest2.setEndHour(22);

        JoinInfoRequest joinInfoRequest3 = new JoinInfoRequest();
        joinInfoRequest3.setLocalDate(LocalDate.of(1921, 4, 7));
        joinInfoRequest3.setStartHour(19);
        joinInfoRequest3.setEndHour(23);


        ArrayList<JoinInfoRequest> JoinInfoRequestList = new ArrayList<>();
        JoinInfoRequestList.add(joinInfoRequest1);
        JoinInfoRequestList.add(joinInfoRequest2);
        JoinInfoRequestList.add(joinInfoRequest3);

        System.out.println("=============================");
        joinService.enrollJoin(user.getUsername(), plan.getId(), JoinInfoRequestList);
        System.out.println("=============================");
    }


}