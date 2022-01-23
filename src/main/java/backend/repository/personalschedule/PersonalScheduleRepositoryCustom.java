package backend.repository.personalschedule;

import backend.domain.PersonalSchedule;
import backend.domain.User;

import java.time.YearMonth;
import java.util.List;

public interface PersonalScheduleRepositoryCustom {

    List<PersonalSchedule> findPersonalScheduleList(User user, YearMonth yearMonth);

}
