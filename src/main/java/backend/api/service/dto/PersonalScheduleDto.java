package backend.api.service.dto;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PersonalScheduleDto {

    private Long id;
    private User user;
    private LocalDate localDate;
    private String title;

    @Builder
    private PersonalScheduleDto(Long id, User user, LocalDate localDate, String title) {
        this.id = id;
        this.user = user;
        this.localDate = localDate;
        this.title = title;
    }

    public static PersonalScheduleDto of(PersonalSchedule personalSchedule) {
        return PersonalScheduleDto.builder()
                .id(personalSchedule.getId())
                .user(personalSchedule.getUser())
                .localDate(personalSchedule.getLocalDate())
                .title(personalSchedule.getTitle())
                .build();
    }

}

