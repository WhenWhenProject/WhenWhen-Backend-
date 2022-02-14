package backend.api.controller.dto.plan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter @Setter
public class CreatePlanRequest {

    @NotNull @Size(max = 100)
    private String title;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Long expectedMemberCnt;

    @Size(max = 100)
    @Column(length = 100)
    private String location;

    @NotNull
    private Long startHour;

}
