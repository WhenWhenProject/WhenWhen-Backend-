package backend.api.service.dto;

import backend.api.entity.JoinInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinInfoDto {

    private Long id;

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    @Builder
    private JoinInfoDto(Long id, LocalDate localDate, Integer startHour, Integer endHour) {
        this.id = id;
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static JoinInfoDto of(JoinInfo joinInfo) {
        return JoinInfoDto.builder()
                .id(joinInfo.getId())
                .localDate(joinInfo.getLocalDate())
                .startHour(joinInfo.getStartHour())
                .endHour(joinInfo.getEndHour())
                .build();
    }

    public JoinInfo toEntity() {
        return JoinInfo.builder()
                .localDate(localDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();
    }

}