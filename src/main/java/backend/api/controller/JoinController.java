package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.JoinInfoRequest;
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

        return new ApiResponse<>(result);
    }

    @PostMapping("/{planId}")
    public ApiResponse<String> enroll(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId, @RequestBody List<JoinInfoRequest> list) {
        joinService.enroll(userPrincipal.getUsername(), planId, list);

        return new ApiResponse<>("success");
    }

    @PatchMapping("/{planId}")
    public ApiResponse<String> update(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId, @RequestBody List<JoinInfoRequest> list) {
        joinService.update(userPrincipal.getUsername(), planId, list);

        return new ApiResponse<>("success");
    }

    @DeleteMapping("/{planId}")
    public ApiResponse<String> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable("planId") Long planId) {
        joinService.delete(userPrincipal.getUsername(), planId);

        return new ApiResponse<>("success");
    }

}