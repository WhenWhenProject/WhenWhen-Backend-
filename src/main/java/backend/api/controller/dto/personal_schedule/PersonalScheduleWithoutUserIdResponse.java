package backend.api.controller.dto.personal_schedule;

import backend.api.service.dto.PersonalScheduleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PersonalScheduleWithoutUserIdResponse {

    private LocalDate localDate;
    private String title;

    @Builder
    private PersonalScheduleWithoutUserIdResponse(LocalDate localDate, String title) {
        this.localDate = localDate;
        this.title = title;
    }

    public static PersonalScheduleWithoutUserIdResponse of(PersonalScheduleDto personalScheduleDto) {
        return PersonalScheduleWithoutUserIdResponse.builder()
                .localDate(personalScheduleDto.getLocalDate())
                .title(personalScheduleDto.getTitle())
                .build();
    }

}
