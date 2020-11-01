package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import f54148.adminication.entity.File;
import f54148.adminication.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	FileService fileservice;

	@PostMapping(path = "/addFile")
	public @ResponseBody String addFile(@RequestBody File file) {

		if (fileservice.addFile(file)) {
			return "Added file";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateFile")
	public @ResponseBody String updateFile(@RequestBody File file) {

		if (fileservice.updateFile(file)) {
			return "Updated file";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/file/{id}")
	public @ResponseBody File getFileById(@PathVariable("id") Long id) {
		return fileservice.getFileById(id);
	}
	
	@DeleteMapping(path = "/file/{id}")
	public @ResponseBody String deleteFileById(@PathVariable("id") Long id) {
		if (fileservice.deleteFile(id)) {
			return "Deleted file!";
		} else {
			return "An error has occured";
		}
	}

	@GetMapping(path = "/files")
	public @ResponseBody List<File> getAllFiles() {
		return fileservice.getFiles();
	}

}
