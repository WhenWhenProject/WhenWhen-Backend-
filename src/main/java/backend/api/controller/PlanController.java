package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.PlanEnrollRequest;
import backend.api.controller.dto.response.PlanResponse;
import backend.api.service.PlanService;
import backend.api.service.dto.PlanDto;
import backend.argumentresolver.CurrentUser;
import backend.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanService planService;

    @GetMapping("/host/all")
    public ApiResponse<List<PlanResponse>> findAllCreatedByMe(@CurrentUser UserPrincipal userPrincipal) {
        List<PlanDto> planDtoList = planService.findAllCreatedByMe(userPrincipal.getUsername());

        List<PlanResponse> result = planDtoList.stream()
                .map(planDto -> PlanResponse.of(planDto))
                .collect(Collectors.toList());

        return new ApiResponse<>(result);
    }

    @GetMapping("/all")
    public ApiResponse<List<PlanResponse>> findAllParticipatedIn(@CurrentUser UserPrincipal userPrincipal) {
        List<PlanDto> planDtoList = planService.findAllParticipatedIn(userPrincipal.getUsername());

        List<PlanResponse> result = planDtoList.stream()
                .map(planDto -> PlanResponse.of(planDto))
                .collect(Collectors.toList());

        return new ApiResponse<>(result);
    }

    @GetMapping("/fixed/all")
    public ApiResponse<List<PlanResponse>> findAllFixed(@CurrentUser UserPrincipal userPrincipal) {
        List<PlanDto> planDtoList = planService.findAllFixed(userPrincipal.getUsername());

        List<PlanResponse> result = planDtoList.stream()
                .map(planDto -> PlanResponse.of(planDto))
                .collect(Collectors.toList());

        return new ApiResponse<>(result);
    }

    @GetMapping("/unfixed/all")
    public ApiResponse<List<PlanResponse>> findAllUnfixed(@CurrentUser UserPrincipal userPrincipal) {
        List<PlanDto> planDtoList = planService.findAllUnfixed(userPrincipal.getUsername());

        List<PlanResponse> result = planDtoList.stream()
                .map(planDto -> PlanResponse.of(planDto))
                .collect(Collectors.toList());

        return new ApiResponse<>(result);
    }

    @PostMapping
    public ApiResponse<String> create(@CurrentUser UserPrincipal userPrincipal, @RequestBody PlanEnrollRequest planEnrollRequest) {
        planService.create(userPrincipal.getUsername(), planEnrollRequest);

        return new ApiResponse<>("success");
    }

    @DeleteMapping("/{planId}")
    public ApiResponse<String> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long planId) {
        planService.delete(userPrincipal.getUsername(), planId);

        return new ApiResponse<>("success");
    }

}
