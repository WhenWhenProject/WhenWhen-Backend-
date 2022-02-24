package backend.api.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class JoinInfoRequest {

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

}
