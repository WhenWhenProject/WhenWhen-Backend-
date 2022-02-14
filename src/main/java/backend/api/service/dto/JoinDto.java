package backend.api.service.dto;

import backend.api.entity.Join;
import backend.api.entity.Plan;
import backend.api.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinDto {

    private Long id;
    private User user;
    private Plan plan;

    @Builder
    public JoinDto(Long id, User user, Plan plan) {
        this.id = id;
        this.user = user;
        this.plan = plan;
    }

    public static JoinDto of(Join join) {
        return JoinDto.builder()
                .id(join.getId())
                .user(join.getUser())
                .plan(join.getPlan())
                .build();
    }

}
