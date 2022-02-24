package backend.api.service.dto;

import backend.api.entity.JoinInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinInfoDto {

    private Long id;

    private JoinDto joinDto;

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    @Builder
    private JoinInfoDto(Long id, JoinDto joinDto, LocalDate localDate, Integer startHour, Integer endHour) {
        this.id = id;
        this.joinDto = joinDto;
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static JoinInfoDto of(JoinInfo joinInfo) {
        return JoinInfoDto.builder()
                .id(joinInfo.getId())
                .joinDto(JoinDto.of(joinInfo.getJoin()))
                .localDate(joinInfo.getLocalDate())
                .startHour(joinInfo.getStartHour())
                .endHour(joinInfo.getEndHour())
                .build();
    }

}