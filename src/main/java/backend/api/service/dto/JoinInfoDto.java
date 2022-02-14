package backend.api.service.dto;

import backend.api.entity.Availability;
import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinInfoDto {

    private Long id;
    private Join join;
    private LocalDate localDate;
    private Availability availability;

    @Builder
    public JoinInfoDto(Long id, Join join, LocalDate localDate, Availability availability) {
        this.id = id;
        this.join = join;
        this.localDate = localDate;
        this.availability = availability;
    }

    public static JoinInfoDto of(JoinInfo joinInfo) {
        return JoinInfoDto.builder()
                .id(joinInfo.getId())
                .join(joinInfo.getJoin())
                .localDate(joinInfo.getLocalDate())
                .availability(joinInfo.getAvailability())
                .build();
    }

}
