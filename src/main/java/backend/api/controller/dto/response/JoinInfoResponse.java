package backend.api.controller.dto.response;

import backend.api.service.dto.JoinInfoDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinInfoResponse {

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    @Builder
    private JoinInfoResponse(LocalDate localDate, Integer startHour, Integer endHour) {
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static JoinInfoResponse of(JoinInfoDto joinInfoDto) {
        return JoinInfoResponse.builder()
                .localDate(joinInfoDto.getLocalDate())
                .startHour(joinInfoDto.getStartHour())
                .endHour(joinInfoDto.getEndHour())
                .build();
    }

}
