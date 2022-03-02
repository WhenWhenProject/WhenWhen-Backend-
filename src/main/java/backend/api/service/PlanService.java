package backend.api.service;

import backend.api.controller.dto.request.PlanEnrollRequest;
import backend.api.entity.Plan;
import backend.api.entity.User;
import backend.api.exception.PlanNotFoundException;
import backend.api.exception.UserNotFoundException;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.api.service.dto.PlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(String username, PlanEnrollRequest planEnrollRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Plan plan = Plan.builder()
                .host(user)
                .title(planEnrollRequest.getTitle())
                .startDate(planEnrollRequest.getStartDate())
                .endDate(planEnrollRequest.getEndDate())
                .expectedMemberCnt(planEnrollRequest.getExpectedMemberCnt())
                .location(planEnrollRequest.getLocation())
                .startHour(planEnrollRequest.getStartHour())
                .build();

        planRepository.save(plan);
    }

    @Transactional
    public void delete(String username, Long planId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Plan plan = planRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);

        if (plan.getHost() != user) throw new PlanNotFoundException("해당 일정의 소유자가 아닙니다.");

        planRepository.delete(plan);
    }

    public List<PlanDto> findAllCreatedByMe(String username) {
        List<Plan> planList = planRepository.findAllByHostUsername(username);

        return planList.stream()
                .map(plan -> PlanDto.of(plan))
                .collect(Collectors.toList());
    }

    public List<PlanDto> findAllParticipatedIn(String username) {
        List<Plan> planList = planRepository.findAllParticipatingPlanByUsername(username);

        return planList.stream()
                .map(plan -> PlanDto.of(plan))
                .collect(Collectors.toList());
    }

}



