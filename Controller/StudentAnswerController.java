package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.StudentAnswer;
import com.grd.online.paper.Service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentanswers")
@CrossOrigin
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping
    public ResponseEntity<ResponseBean> saveStudentAnswer(@RequestBody StudentAnswer studentAnswer) {
        ResponseBean response = studentAnswerService.saveStudentAnswer(studentAnswer);
        response.setMessage("Student Answer created successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/bulkSave")
    public ResponseEntity<ResponseBean> bulkSaveStudentAnswer(@RequestBody List<StudentAnswer> studentAnswers) {
        ResponseBean response = studentAnswerService.bulkSaveStudentAnswer(studentAnswers);
        response.setMessage("Student Answers created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}/{paperId}")
    public ResponseEntity<ResponseBean> getStudentAnswers(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "paperId") Long paperId) {
        ResponseBean response = studentAnswerService.getStudentAnswers(userId, paperId);
        response.setMessage("Student Answers retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
