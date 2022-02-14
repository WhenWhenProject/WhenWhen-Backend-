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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDate localDate;

    @NotNull
    private String title;

    @Builder
    public PersonalSchedule(
            @NotNull User user,
            @NotNull LocalDate localDate,
            @NotNull String title) {
        this.user = user;
        this.localDate = localDate;
        this.title = title;
    }

}
