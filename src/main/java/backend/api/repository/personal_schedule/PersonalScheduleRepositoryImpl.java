package backend.api.repository.personal_schedule;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import java.util.List;

import static backend.api.entity.QPersonalSchedule.personalSchedule;

public class PersonalScheduleRepositoryImpl extends MyQuerydslRepositorySupport implements PersonalScheduleRepositoryCustom {

    public PersonalScheduleRepositoryImpl() {
        super(PersonalSchedule.class);
    }

    public List<PersonalSchedule> findPersonalScheduleListByUserAndYearAndMonth(User user, Integer year, Integer month) {
        return getQueryFactory()
                .select(personalSchedule)
                .select(personalSchedule)
                .from(personalSchedule)
                .where(
                        personalSchedule.user.eq(user)
                                .and(personalSchedule.localDate.year().eq(year))
                                .and(personalSchedule.localDate.month().eq(month))
                )
                .fetch();
    }

}
