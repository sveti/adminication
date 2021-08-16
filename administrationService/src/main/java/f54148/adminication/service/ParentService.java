package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.AddParentDTO;
import f54148.adminication.dto.StudentOfParentDTO;

public interface ParentService {
	
	List<StudentOfParentDTO> getStudentOfParentDTO(Long parentId );

    String addParent(AddParentDTO addParentDTO);
}
