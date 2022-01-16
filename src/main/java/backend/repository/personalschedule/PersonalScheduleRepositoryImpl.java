package backend.repository.personalschedule;

import backend.domain.Join;
import backend.domain.PersonalSchedule;
import backend.repository.MyQuerydslRepositorySupport;

public class PersonalScheduleRepositoryImpl extends MyQuerydslRepositorySupport implements PersonalScheduleRepositoryCustom {

    public PersonalScheduleRepositoryImpl() {
        super(PersonalSchedule.class);
    }

}
