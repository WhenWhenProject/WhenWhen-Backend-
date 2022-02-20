package backend.api.entity;

import backend.api.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "_plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;

    @Column(length = 100)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long expectedMemberCnt;

    private boolean fixed;

    private String linkUrl;

    @Column(length = 100)
    private String location;

    private Integer startHour;

    @Builder
    private Plan(
            @NotNull User host,
            @NotNull String title,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotNull Long expectedMemberCnt,
            String location,
            @NotNull Integer startHour
    ) {
        this.host = host;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedMemberCnt = expectedMemberCnt;
        this.fixed = false;
        this.linkUrl = UUID.randomUUID().toString().substring(0, 50);
        this.location = location != null ? location : "미정";
        this.startHour = startHour;
    }

}
