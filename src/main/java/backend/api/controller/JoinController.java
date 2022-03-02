package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.JoinEnrollRequest;
import backend.api.controller.dto.response.JoinInfoResponse;
import backend.api.service.JoinService;
import backend.api.service.dto.JoinInfoDto;
import backend.argumentresolver.CurrentUser;
import backend.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/{planId}")
    public ApiResponse<List<JoinInfoResponse>> findAll(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId) {
        List<JoinInfoDto> joinInfoList = joinService.findAll(userPrincipal.getUsername(), planId);

        List<JoinInfoResponse> result = joinInfoList.stream()
                .map(joinInfoDto -> JoinInfoResponse.of(joinInfoDto))
                .collect(Collectors.toList());

        return ApiResponse.success("list", result);
    }

    @PostMapping("/{planId}")
    public ApiResponse<String> enroll(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId, @RequestBody JoinEnrollRequest joinEnrollRequest) {
        joinService.enroll(userPrincipal.getUsername(), planId, joinEnrollRequest.getJoinInfoRequestList());

        return ApiResponse.success("create", "success");
    }

}