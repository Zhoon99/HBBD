package deep.capstone.hbbd.controller.common.classes;

import deep.capstone.hbbd.dto.BoundsDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.repository.ClassesRepository;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/common/class")
public class CommonClassController {

    private final ClassService classService;
    private final ClassesRepository classesRepository;

    @GetMapping("/detail/{cId}")
    public String classDetail(@PathVariable Long cId, Model model) {

        ClassesDto.detail classesDetail = classService.getClassesDetail(cId);

        model.addAttribute("class", classesDetail);
        return "common/class/class_detail";
    }

    @GetMapping("/map")
    public String classMap() {
        return "common/class/class_map";
    }

    @PostMapping("cluster")
    @ResponseBody
    public List<PreviewDto> cluster() {
        List<Classes> classesInCluster = classesRepository.findAll();
        List<PreviewDto> previewList = classService.getPreviewList(classesInCluster);
        return previewList;
    }

    @PostMapping("clusterInside")
    @ResponseBody
    public List<PreviewDto> clusterInside(@RequestBody List<Long> cIdArr) {
        List<Classes> classesInCluster = classesRepository.getClassesInCluster(cIdArr);
        List<PreviewDto> previewList = classService.getPreviewList(classesInCluster);
        return previewList;
    }


}
