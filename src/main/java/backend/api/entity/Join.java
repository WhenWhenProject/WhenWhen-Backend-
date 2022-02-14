package backend.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "_join")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Join {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Builder
    public Join(
            @NotNull User user,
            @NotNull Plan plan) {
        this.user = user;
        this.plan = plan;
    }

}