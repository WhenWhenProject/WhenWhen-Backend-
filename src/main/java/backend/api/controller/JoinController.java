package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.response.JoinInfoResponse;
import backend.api.service.JoinService;
import backend.api.service.dto.JoinInfoDto;
import backend.argumentresolver.CurrentUser;
import backend.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/{planId}")
    public ApiResponse<List<JoinInfoResponse>> find(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId) {
        List<JoinInfoDto> joinInfoList = joinService.findJoinInfoList(userPrincipal.getUsername(), planId);

        List<JoinInfoResponse> result = joinInfoList.stream()
                .map(joinInfoDto -> JoinInfoResponse.of(joinInfoDto))
                .collect(Collectors.toList());

        return ApiResponse.success("join-info-list", result);
    }

}