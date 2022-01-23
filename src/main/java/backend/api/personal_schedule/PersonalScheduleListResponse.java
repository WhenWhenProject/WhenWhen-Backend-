package backend.api.personal_schedule;

import backend.dto.PersonalScheduleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PersonalScheduleListResponse {

    private Long userId;
    private Long totalCount;
    private List<PersonalScheduleWithoutUserIdResponse> list;

    public static PersonalScheduleListResponse of(Long userId, List<PersonalScheduleDto> dtoList) {
        return PersonalScheduleListResponse.builder()
                .userId(userId)
                .totalCount(Long.valueOf(dtoList.size()))
                .list(dtoList.stream()
                        .map(personalScheduleDto -> PersonalScheduleWithoutUserIdResponse.of(personalScheduleDto))
                        .collect(Collectors.toList())
                )
                .build();
    }

}
