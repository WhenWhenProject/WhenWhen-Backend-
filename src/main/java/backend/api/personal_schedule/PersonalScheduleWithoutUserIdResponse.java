package backend.api.personal_schedule;

import backend.dto.PersonalScheduleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PersonalScheduleWithoutUserIdResponse {

    private LocalDate localDate;
    private String title;

    public static PersonalScheduleWithoutUserIdResponse of(PersonalScheduleDto personalScheduleDto) {
        return PersonalScheduleWithoutUserIdResponse.builder()
                .localDate(personalScheduleDto.getLocalDate())
                .title(personalScheduleDto.getTitle())
                .build();
    }

}
