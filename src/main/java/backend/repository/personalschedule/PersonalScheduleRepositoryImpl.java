package backend.repository.personalschedule;

import backend.domain.PersonalSchedule;
import backend.domain.User;
import backend.repository.MyQuerydslRepositorySupport;

import java.time.YearMonth;
import java.util.List;

import static backend.domain.QPersonalSchedule.personalSchedule;

public class PersonalScheduleRepositoryImpl extends MyQuerydslRepositorySupport implements PersonalScheduleRepositoryCustom {

    public PersonalScheduleRepositoryImpl() {
        super(PersonalSchedule.class);
    }

    @Override
    public List<PersonalSchedule> findPersonalScheduleList(User user, YearMonth yearMonth) {
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
