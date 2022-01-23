package backend.controller;

import backend.api.personal_schedule.PersonalScheduleListResponse;
import backend.api.common.ApiResponse;
import backend.dto.PersonalScheduleDto;
import backend.service.PersonalScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final PersonalScheduleService personalScheduleService;

    @GetMapping("/user/{id}/personal_schedules/{year}/{month}")
    public ApiResponse<PersonalScheduleListResponse> getPersonalSchedules(@PathVariable("id") Long userId,
                                                                          @PathVariable("year") Long year,
                                                                          @PathVariable("month") Long month) {
        List<PersonalScheduleDto> personalScheduleDtoList = personalScheduleService.findPersonalScheduleList(userId, year, month);

        PersonalScheduleListResponse personalScheduleListResponse = PersonalScheduleListResponse.of(userId, personalScheduleDtoList);

        return new ApiResponse<>(ApiResponse.SUCCESS, personalScheduleListResponse);
    }

}
