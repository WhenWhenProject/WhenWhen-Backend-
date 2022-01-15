package backend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Availability availability;

    public JoinInfo(Join join, LocalDate localDate, Availability availability) {
        this.join = join;
        this.localDate = localDate;
        this.availability = availability;
    }

}