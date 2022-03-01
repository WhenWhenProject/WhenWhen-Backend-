package backend.api.service.dto;


import backend.api.entity.User;
import lombok.Builder;

import java.time.LocalDate;

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
    private Integer startHour;

    @Builder
    public PlanDto(Long id, User host, String title, LocalDate startDate, LocalDate endDate, Long expectedMemberCnt, boolean fixed, String linkUrl, String location, Integer startHour) {
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
}
