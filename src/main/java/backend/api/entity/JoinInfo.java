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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_id")
    private Join join;

    @NotNull
    private LocalDate localDate;



    @NotNull
    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Builder
    public JoinInfo(
            @NotNull Join join,
            @NotNull LocalDate localDate,
            @NotNull Availability availability) {
        this.join = join;
        this.localDate = localDate;
        this.availability = availability;
    }

}