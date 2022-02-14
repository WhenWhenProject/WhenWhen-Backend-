package backend.api.service.dto;

import backend.api.entity.Plan;
import backend.api.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlanDto {

    private Long id;
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
    private PlanDto(Long id, User host, String title, LocalDate startDate, LocalDate endDate, Long expectedMemberCnt, boolean fixed, String linkUrl, String location, Long startHour) {
        this.id = id;
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

    public static PlanDto of(Plan plan) {
        return PlanDto.builder()
                .id(plan.getId())
                .host(plan.getHost())
                .title(plan.getTitle())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .expectedMemberCnt(plan.getExpectedMemberCnt())
                .fixed(plan.isFixed())
                .linkUrl(plan.getLinkUrl())
                .location(plan.getLocation())
                .startHour(plan.getStartHour())
                .build();
    }

}