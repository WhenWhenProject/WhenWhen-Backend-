package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.personal_schedule.PersonalScheduleListResponse;
import backend.api.service.PersonalScheduleService;
import backend.api.service.dto.PersonalScheduleDto;
import backend.oauth.entity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal_schedules/")
public class PersonalScheduleController {

    private final PersonalScheduleService personalScheduleService;

    @GetMapping("/{year}/{month}")
    public ApiResponse<PersonalScheduleListResponse> getPersonalSchedules(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                          @PathVariable("year") Long year,
                                                                          @PathVariable("month") Long month) {
        String username = principalDetails.getUsername();

        List<PersonalScheduleDto> personalScheduleDtoList = personalScheduleService.findPersonalScheduleList(username, year, month);

        return ApiResponse.success("schedule", PersonalScheduleListResponse.of(username, personalScheduleDtoList));
    }

}
