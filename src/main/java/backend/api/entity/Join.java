package backend.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_join")
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

    @OneToMany(mappedBy = "join", cascade = CascadeType.ALL)
    private List<JoinInfo> joinInfoList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Plan getPlan() {
        return plan;
    }

    public List<JoinInfo> getJoinInfoList() {
        return new ArrayList<>(joinInfoList);
    }

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

    public void changeJoinInfoList(List<JoinInfo> list) {
        this.joinInfoList = list;
    }

}