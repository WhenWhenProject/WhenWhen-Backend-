package backend.api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinInfoRequest {

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

}
