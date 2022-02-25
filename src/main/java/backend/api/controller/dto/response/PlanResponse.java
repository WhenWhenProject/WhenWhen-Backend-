package backend.api.controller.dto.response;

import backend.api.service.dto.PlanDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlanResponse {

    private Long id;

    private String hostNickName;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long expectedMemberCnt;

    private boolean fixed;

    private String linkUrl;

    private String location;

    private Integer startHour;

    @Builder
    private PlanResponse(Long id, String hostNickName, String title, LocalDate startDate, LocalDate endDate, Long expectedMemberCnt, boolean fixed, String linkUrl, String location, Integer startHour) {
        this.id = id;
        this.hostNickName = hostNickName;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedMemberCnt = expectedMemberCnt;
        this.fixed = fixed;
        this.linkUrl = linkUrl;
        this.location = location;
        this.startHour = startHour;
    }

    public static PlanResponse of(PlanDto planDto) {
        return PlanResponse.builder()
                .id(planDto.getId())
                .hostNickName(planDto.getHost().getNickName())
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
