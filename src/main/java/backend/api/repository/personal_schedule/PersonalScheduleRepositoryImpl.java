package backend.api.repository.personal_schedule;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import java.time.YearMonth;
import java.util.List;

import static backend.api.entity.QPersonalSchedule.personalSchedule;


public class PersonalScheduleRepositoryImpl extends MyQuerydslRepositorySupport implements PersonalScheduleRepositoryCustom {

    public PersonalScheduleRepositoryImpl() {
        super(PersonalSchedule.class);
    }

    @Override
    public List<PersonalSchedule> findPersonalScheduleListByUserAndYearMonth(User user, YearMonth yearMonth) {
        return getQueryFactory()
                .select(personalSchedule)
                .from(personalSchedule)
                .where(
                        personalSchedule.user.eq(user)
                                .and(personalSchedule.localDate.year().eq(yearMonth.getYear()))
                                .and(personalSchedule.localDate.month().eq(yearMonth.getMonthValue()))
                )
                .fetch();
    }

}
