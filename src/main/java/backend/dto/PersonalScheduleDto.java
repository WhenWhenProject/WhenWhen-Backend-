package backend.dto;

import backend.domain.PersonalSchedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PersonalScheduleDto {

    private Long personalScheduleId;
    private Long userId;
    private LocalDate localDate;
    private String title;

    public static PersonalScheduleDto of(PersonalSchedule personalSchedule) {
        return PersonalScheduleDto.builder()
                .personalScheduleId(personalSchedule.getId())
                .userId(personalSchedule.getUser().getId())
                .localDate(personalSchedule.getLocalDate())
                .title(personalSchedule.getTitle())
                .build();
    }

}

