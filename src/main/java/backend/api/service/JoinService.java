package backend.api.service;

import backend.api.entity.Join;
import backend.api.exception.JoinNotFoundException;
import backend.api.repository.join.JoinRepository;
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

    public List<JoinInfoDto> findJoinInfoList(String username, Long planId) {
        Join join = joinRepository.findByUsernameAndPlanId(username, planId)
                .orElseThrow(JoinNotFoundException::new);

        return join.getJoinInfoList().stream()
                .map(joinInfo -> JoinInfoDto.of(joinInfo))
                .collect(Collectors.toList());
    }

}
