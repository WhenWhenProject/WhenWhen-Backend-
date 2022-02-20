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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_id")
    private Join join;

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Builder
    private JoinInfo(
            @NotNull Join join,
            @NotNull LocalDate localDate,
            @NotNull Integer startHour,
            @NotNull Integer endHour,
            @NotNull Availability availability) {
        this.join = join;
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.availability = availability;
    }

}