package backend.service;

import backend.domain.PersonalSchedule;
import backend.domain.User;
import backend.dto.PersonalScheduleDto;
import backend.exception.UserNotFountException;
import backend.repository.personalschedule.PersonalScheduleRepository;
import backend.repository.user.UserRepository;
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

    public List<PersonalScheduleDto> findPersonalScheduleList(Long userId, Long year, Long month) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFountException());

        YearMonth yearMonth = YearMonth.of(year.intValue(), month.intValue());

        List<PersonalSchedule> personalScheduleList = personalScheduleRepository.findPersonalScheduleList(findUser, yearMonth);

        return personalScheduleList.stream()
                .map(personalSchedule -> PersonalScheduleDto.of(personalSchedule))
                .collect(Collectors.toList());
    }

}
