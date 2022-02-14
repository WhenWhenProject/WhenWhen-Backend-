package backend.api.entity;

import backend.api.entity.common.BaseTimeEntity;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "_plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan  extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;

    @NotNull @Size(max = 100)
    @Column(length = 100)
    private String title;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Long expectedMemberCnt;

    private boolean fixed;

    @NotNull
    private String linkUrl;

    @Size(max = 100)
    @Column(length = 100)
    private String location;

    @NotNull
    private Long startHour;

    @Builder
    public Plan(
            @NotNull User host,
            @NotNull String title,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotNull Long expectedMemberCnt,
            @NotNull String location,
            @NotNull Long startHour
    ) {
        this.host = host;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedMemberCnt = expectedMemberCnt;
        this.fixed = false;
        this.linkUrl =  UUID.randomUUID().toString().substring(0, 30);
        this.location = StringUtils.hasText(location) ? location : "미정";
        this.startHour = startHour;
    }

}
