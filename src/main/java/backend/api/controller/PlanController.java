package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.CreatePlanRequest;
import backend.api.controller.dto.response.CreatePlanResponse;
import backend.api.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")

public class PlanController {

    private final PlanService planService;

    @PostMapping("/create-plan")
    public ApiResponse<CreatePlanResponse> createPlan(@Validated @RequestBody CreatePlanRequest createPlanRequest){
        String
    }
}
