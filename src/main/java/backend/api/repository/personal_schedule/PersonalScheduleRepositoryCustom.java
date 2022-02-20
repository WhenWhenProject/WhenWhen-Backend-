package backend.api.repository.personal_schedule;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;

import java.util.List;

public interface PersonalScheduleRepositoryCustom {

    List<PersonalSchedule> findPersonalScheduleListByUserAndYearAndMonth(User user, Integer year, Integer month);

}
