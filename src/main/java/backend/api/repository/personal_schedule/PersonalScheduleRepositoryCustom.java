package backend.api.repository.personal_schedule;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;

import java.time.YearMonth;
import java.util.List;

public interface PersonalScheduleRepositoryCustom {

    List<PersonalSchedule> findPersonalScheduleListByUserAndYearMonth(User user, YearMonth yearMonth);

}
