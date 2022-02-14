package backend.api.controller.dto.personal_schedule;

import backend.api.service.dto.PersonalScheduleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PersonalScheduleListResponse {

    private String username;
    private Long totalCount;
    private List<PersonalScheduleWithoutUserIdResponse> list;

    @Builder
    private PersonalScheduleListResponse(String username, Long totalCount, List<PersonalScheduleWithoutUserIdResponse> list) {
        this.username = username;
        this.totalCount = totalCount;
        this.list = list;
    }

    public static PersonalScheduleListResponse of(String username, List<PersonalScheduleDto> dtoList) {
        return PersonalScheduleListResponse.builder()
                .username(username)
                .totalCount(Long.valueOf(dtoList.size()))
                .list(dtoList.stream()
                        .map(personalScheduleDto -> PersonalScheduleWithoutUserIdResponse.of(personalScheduleDto))
                        .collect(Collectors.toList())
                )
                .build();
    }

}
