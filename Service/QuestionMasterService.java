package com.grd.online.paper.Service;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.QuestionMaster;
import com.grd.online.paper.Repository.QuestionMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionMasterService {

    @Autowired
    private QuestionMasterRepository questionMasterRepository;

    public ResponseBean save(QuestionMaster questionMaster) {
        QuestionMaster savedQuestion = questionMasterRepository.save(questionMaster);
        return ResponseBean.builder()
                .data(savedQuestion)
                .message("Question saved successfully")
                .status(ResponseBean.Status.SUCCESS)
                .build();
    }

    public ResponseBean getActiveQuestionMaster() {
        List<QuestionMaster> activeQuestions = questionMasterRepository.findByIsActiveTrue();
        String message = activeQuestions.isEmpty() ? "No active questions found"
                : "Active questions retrieved successfully";
        return ResponseBean.builder()
                .data(activeQuestions)
                .message(message)
                .status(ResponseBean.Status.SUCCESS)
                .build();
    }

    public ResponseBean findById(Long paperId) {
        QuestionMaster question = questionMasterRepository.findById(paperId).orElse(null);
        String message = (question != null) ? "Question found successfully" : "Question not found";
        return ResponseBean.builder()
                .data(question)
                .message(message)
                .status(ResponseBean.Status.SUCCESS)
                .build();
    }

    public ResponseBean findAll() {
        List<QuestionMaster> questions = questionMasterRepository.findAll();
        String message = questions.isEmpty() ? "No questions found." : "All questions retrieved successfully.";
        return ResponseBean.builder()
                .data(questions)
                .message(message)
                .status(ResponseBean.Status.SUCCESS)
                .build();
    }
}
