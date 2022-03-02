package backend.api.service;

import backend.api.controller.dto.request.JoinInfoRequest;
import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import backend.api.exception.JoinNotFoundException;
import backend.api.repository.join.JoinRepository;
import backend.api.repository.join_info.JoinInfoRepository;
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

    public List<JoinInfoDto> findAll(String username, Long planId) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        return join.getJoinInfoList().stream()
                .map(joinInfo -> JoinInfoDto.of(joinInfo))
                .collect(Collectors.toList());
    }

    @Transactional
    public void enroll(String username, Long planId, List<JoinInfoRequest> joinInfoRequestList) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        joinInfoRepository.deleteByJoin(join);

        joinInfoRequestList.stream()
                .forEach(request -> {
                    JoinInfo joinInfo = JoinInfo.builder()
                            .join(join)
                            .localDate(request.getLocalDate())
                            .startHour(request.getStartHour())
                            .endHour(request.getEndHour())
                            .build();
                    joinInfoRepository.save(joinInfo);
                });
    }

}
