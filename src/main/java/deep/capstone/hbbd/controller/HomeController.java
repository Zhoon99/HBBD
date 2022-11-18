package deep.capstone.hbbd.controller;


import deep.capstone.hbbd.dto.BoundsDto;
import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.repository.ClassesRepository;
import deep.capstone.hbbd.repository.custom.CustomClassesRepository;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final CustomClassesRepository customClassesRepository;

	private final ClassesRepository classesRepository;

	private final ClassService classService;

	//개발 페이지
	@GetMapping(value="/")
	public String index() throws Exception {
		return "index";
	}

	@PostMapping(value = "/class/classesInBounds")
	@ResponseBody
	public List<PreviewDto> getClassesInBounds(@RequestBody BoundsDto boundsDto) {
		List<Classes> classesInBounds = classesRepository.getClassesInBounds(boundsDto.getSwLat(), boundsDto.getNeLat(), boundsDto.getSwLng(), boundsDto.getNeLng());
		List<PreviewDto> previewList = classService.getPreviewList(classesInBounds);
		return previewList;
	}

	@GetMapping(value="/home")
	public String home() throws Exception {
		return "home";
	}

}
