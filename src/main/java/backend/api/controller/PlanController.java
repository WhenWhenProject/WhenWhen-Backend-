package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.personal_schedule.PersonalScheduleListResponse;
import backend.api.controller.dto.plan.CreatePlanRequest;
//import backend.api.service.PlanService;
import backend.api.controller.dto.plan.CreatePlanResponse;
import backend.api.service.PlanService;
import backend.api.service.dto.PersonalScheduleDto;
import backend.api.service.dto.PlanDto;
import backend.oauth.entity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ApiResponse<CreatePlanResponse> createPlan(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CreatePlanRequest createPlanRequest) {
        PlanDto planDto = planService.create(principalDetails.getUsername(), createPlanRequest);

        return ApiResponse.success("plan", CreatePlanResponse.of(planDto));
    }

}