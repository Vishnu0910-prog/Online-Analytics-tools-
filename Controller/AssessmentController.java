package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Service.AssessmentService;
import com.grd.online.paper.Service.ExamTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(path = "/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private ExamTimeTableService examTimeTableService;

    @GetMapping(path = "/{dateTime}")
    private ResponseEntity<ResponseBean> findAllFutureExamTimeTable(@PathVariable("dateTime") Long milliSeconds) {
        ResponseBean response = examTimeTableService.findAllFutureByDate(milliSeconds);
        response.setMessage("Future exams retrieved successfully from the given date."); // Set custom message
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/create")
    private ResponseEntity<ResponseBean> createAssessment(@RequestParam String questionMasterJson,
            @RequestParam String examTimeTableJson,
            @RequestParam String userJson,
            @RequestParam MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(assessmentService.saveAssessment(questionMasterJson, examTimeTableJson, userJson, file));
    }

}