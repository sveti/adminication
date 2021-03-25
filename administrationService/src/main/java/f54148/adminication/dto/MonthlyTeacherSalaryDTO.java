package f54148.adminication.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyTeacherSalaryDTO {
	
	List<TeachingSalaryDTO> teachings;
	List<TeachingSalaryDTO> substitutings;
	List<LessonSalaryDTO> lessons;

}
