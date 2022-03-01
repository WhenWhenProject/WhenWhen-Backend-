package backend.api.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class CreatePlanRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long expectedMemberCnt;
    private String location;
    private Integer startHour;
}
