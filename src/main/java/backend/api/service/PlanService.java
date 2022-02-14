package backend.api.service;

import backend.api.controller.dto.plan.CreatePlanRequest;
import backend.api.entity.Plan;
import backend.api.entity.User;
import backend.api.exception.UserNotFountException;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.api.service.dto.PlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlanDto create(String hostname, CreatePlanRequest createPlanRequest) {
        User host = userRepository.findByUsername(hostname)
                .orElseThrow(UserNotFountException::new);

        Plan plan = Plan.builder()
                .host(host)
                .title(createPlanRequest.getTitle())
                .startDate(createPlanRequest.getStartDate())
                .endDate(createPlanRequest.getEndDate())
                .expectedMemberCnt(createPlanRequest.getExpectedMemberCnt())
                .location(createPlanRequest.getLocation())
                .startHour(createPlanRequest.getStartHour())
                .build();

        Plan savedPlan = planRepository.save(plan);

        return PlanDto.of(savedPlan);
    }

}
