package f54148.adminication.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import f54148.adminication.entity.Schedule;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

	Optional<Schedule> findByStartTimeAndEndTimeAndStartDate(LocalTime startTime, LocalTime endTime,
			LocalDate dayOfTheWeek);

}
