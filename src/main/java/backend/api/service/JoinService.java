package backend.api.service;

import backend.api.controller.dto.request.JoinInfoRequest;
import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import backend.api.entity.Plan;
import backend.api.entity.User;
import backend.api.exception.JoinAlreadyExistException;
import backend.api.exception.JoinNotFoundException;
import backend.api.exception.PlanNotFoundException;
import backend.api.exception.UserNotFoundException;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.join_info.JoinInfoRepository;
import backend.api.repository.plan.PlanRepository;
import backend.api.repository.user.UserRepository;
import backend.api.service.dto.JoinInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinService {

    private final JoinRepository joinRepository;
    private final JoinInfoRepository joinInfoRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public List<JoinInfoDto> findAll(String username, Long planId) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        return join.getJoinInfoList().stream()
                .map(joinInfo -> JoinInfoDto.of(joinInfo))
                .collect(Collectors.toList());
    }

    @Transactional
    public void enroll(String username, Long planId, List<JoinInfoRequest> joinInfoRequestList) {
        if (joinRepository.findByUsernameAndPlanId(username, planId).isPresent()) {
            throw new JoinAlreadyExistException();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Plan plan = planRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);

        Join join = Join.builder()
                .user(user)
                .plan(plan)
                .build();

        joinInfoRequestList.stream()
        .forEach(request -> {
            JoinInfo joinInfo = JoinInfo.builder()
                    .join(join)
                    .localDate(request.getLocalDate())
                    .startHour(request.getStartHour())
                    .endHour(request.getEndHour())
                    .build();
            join.addJoinInfo(joinInfo);
        });

        joinRepository.save(join);
    }

    @Transactional
    public void update(String username, Long planId, List<JoinInfoRequest> joinInfoRequestList) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        joinRepository.delete(join);

        joinInfoRequestList.stream()
                .forEach(request -> {
                    JoinInfo joinInfo = JoinInfo.builder()
                            .join(join)
                            .localDate(request.getLocalDate())
                            .startHour(request.getStartHour())
                            .endHour(request.getEndHour())
                            .build();
                    join.addJoinInfo(joinInfo);
                });

        joinRepository.save(join);
    }

    @Transactional
    public void delete(String username, Long planId) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        joinRepository.delete(join);
    }

}
