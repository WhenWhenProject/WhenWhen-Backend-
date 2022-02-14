package backend.api.controller.dto.plan;

import backend.api.controller.dto.personal_schedule.PersonalScheduleWithoutUserIdResponse;
import backend.api.entity.User;
import backend.api.service.dto.PersonalScheduleDto;
import backend.api.service.dto.PlanDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
public class CreatePlanResponse {

    private User host;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long expectedMemberCnt;
    private boolean fixed;
    private String linkUrl;
    private String location;
    private Long startHour;

    @Builder
    public CreatePlanResponse(User host, String title, LocalDate startDate, LocalDate endDate, Long expectedMemberCnt, boolean fixed, String linkUrl, String location, Long startHour) {
        this.host = host;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedMemberCnt = expectedMemberCnt;
        this.fixed = fixed;
        this.linkUrl = linkUrl;
        this.location = location;
        this.startHour = startHour;
    }

    public static CreatePlanResponse of(PlanDto planDto) {
        return CreatePlanResponse.builder()
                .host(planDto.getHost())
                .title(planDto.getTitle())
                .startDate(planDto.getStartDate())
                .endDate(planDto.getEndDate())
                .expectedMemberCnt(planDto.getExpectedMemberCnt())
                .fixed(planDto.isFixed())
                .linkUrl(planDto.getLinkUrl())
                .location(planDto.getLocation())
                .startHour(planDto.getStartHour())
                .build();
    }

}
