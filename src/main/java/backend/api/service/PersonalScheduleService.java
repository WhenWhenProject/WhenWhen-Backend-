package backend.api.service;

import backend.api.entity.PersonalSchedule;
import backend.api.entity.User;
import backend.api.service.dto.PersonalScheduleDto;
import backend.api.exception.UserNotFountException;
import backend.api.repository.personal_schedule.PersonalScheduleRepository;
import backend.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalScheduleService {

    private final PersonalScheduleRepository personalScheduleRepository;
    private final UserRepository userRepository;

    public List<PersonalScheduleDto> findPersonalScheduleList(String username, Long year, Long month) {
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFountException());

        YearMonth yearMonth = YearMonth.of(year.intValue(), month.intValue());

        List<PersonalSchedule> personalScheduleList = personalScheduleRepository.findPersonalScheduleListByUserAndYearMonth(findUser, yearMonth);

        return personalScheduleList.stream()
                .map(personalSchedule -> PersonalScheduleDto.of(personalSchedule))
                .collect(Collectors.toList());
    }

}
