package backend.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "_join_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    @Builder
    private JoinInfo(
            @NotNull LocalDate localDate,
            @NotNull Integer startHour,
            @NotNull Integer endHour) {
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}