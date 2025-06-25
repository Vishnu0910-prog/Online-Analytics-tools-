package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.Question;
import com.grd.online.paper.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/questions")
@CrossOrigin
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<ResponseBean> createQuestion(@RequestBody Question question) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.saveQuestion(question));
    }

    @PostMapping(path = "/uploadXlsx")
    public ResponseEntity<ResponseBean> uploadFromXlsx(@RequestParam Long userId, @RequestParam Long questionMasterId,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.uploadFromXLSX(file, questionMasterId, userId));
    }

    @GetMapping("/paper/{questionMasterId}")
    public ResponseEntity<ResponseBean> getQuestionsByPaperId(
            @PathVariable(name = "questionMasterId") Long questionMasterId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionService.getQuestionsByQuestionMasterId(questionMasterId));
    }
}