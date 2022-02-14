package backend.api.repository.personal_schedule;

import backend.api.entity.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long>, PersonalScheduleRepositoryCustom {

}