package backend.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_join")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Join {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "join_id")
    private List<JoinInfo> joinInfoList = new ArrayList<>();

    @Builder
    private Join(
            @NotNull User user,
            @NotNull Plan plan) {
        this.user = user;
        this.plan = plan;
    }

    public void addJoinInfo(JoinInfo joinInfo) {
        joinInfoList.add(joinInfo);
    }

}