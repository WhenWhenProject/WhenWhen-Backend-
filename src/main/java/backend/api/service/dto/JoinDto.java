package backend.api.service.dto;

import backend.api.entity.Join;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinDto {

    private Long id;

    private UserDto userDto;

    private PlanDto planDto;

    @Builder
    private JoinDto(Long id, UserDto userDto, PlanDto planDto) {
        this.id = id;
        this.userDto = userDto;
        this.planDto = planDto;
    }

    public static JoinDto of(Join join) {
        return JoinDto.builder()
                .id(join.getId())
                .userDto(UserDto.of(join.getUser()))
                .planDto(PlanDto.of(join.getPlan()))
                .build();
    }

}