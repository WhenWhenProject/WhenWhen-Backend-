package backend.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private LocalDate date;

    private String title;

    @Builder
    public PersonalSchedule(User user, LocalDate date, String title) {
        this.user = user;
        this.date = date;
        this.title = title;
    }

}
