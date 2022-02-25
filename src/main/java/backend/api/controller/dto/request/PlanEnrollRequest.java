package backend.api.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlanEnrollRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long expectedMemberCnt;

    @Size(max = 100)
    private String location;

    private Integer startHour;

}
