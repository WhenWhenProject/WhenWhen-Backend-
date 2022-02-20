package backend.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "_personal_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate localDate;

    private Integer startHour;

    private Integer endHour;

    private String title;

    @Builder
    private PersonalSchedule(
            @NotNull User user,
            @NotNull LocalDate localDate,
            @NotNull Integer startHour,
            @NotNull Integer endHour,
            @NotNull String title) {
        this.user = user;
        this.localDate = localDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.title = title;
    }

}
