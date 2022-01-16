package backend.repository.personalschedule;

import backend.domain.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long>, PersonalScheduleRepositoryCustom {

}