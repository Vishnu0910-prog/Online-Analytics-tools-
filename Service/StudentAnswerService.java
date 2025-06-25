package com.grd.online.paper.Service;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Bean.ResultResponse;
import com.grd.online.paper.Repository.QuestionRepository;
import com.grd.online.paper.Repository.ResultRepository;
import com.grd.online.paper.Model.*;
import com.grd.online.paper.Repository.StudentAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    public ResponseBean saveStudentAnswer(StudentAnswer studentAnswer) {
        return ResponseBean.builder()
                .data(studentAnswerRepository.save(studentAnswer))
                .build();
    }

    public ResponseBean bulkSaveStudentAnswer(List<StudentAnswer> studentAnswers) {
        Double totalMarks = 0.0;
        Double marksObtained = 0.0;
        int correctAnswers = 0;
        int wrongAnswers = 0;

        UserModel user = new UserModel();
        QuestionMaster questionMaster = new QuestionMaster();

        for (StudentAnswer studentAnswer : studentAnswers) {
            Question question = questionRepository.findById(studentAnswer.getQuestion().getQuestionId()).orElse(null);

            if (question != null) {
                user = studentAnswer.getUser();
                questionMaster = question.getQuestionMaster();

                totalMarks = (double) question.getQuestionMaster().getTotalMarks();

                if (question.getCorrectOption().replaceAll(" ", "")
                        .equalsIgnoreCase(studentAnswer.getSelectedOption())) {
                    correctAnswers += 1;
                    marksObtained += question.getMarks();
                    studentAnswer.setIsCorrect(true);
                } else {
                    wrongAnswers += 1;
                    studentAnswer.setIsCorrect(false);
                }
            }
        }

        Double _total = (marksObtained / totalMarks) * 100;
        ResultResponse resultResponse = ResultResponse.builder()
                .status((_total >= 50) ? ResultResponse.ResultStatus.PASS : ResultResponse.ResultStatus.FAIL)
                .marksObtained(marksObtained.intValue())
                .correctAnswers(correctAnswers)
                .wrongAnswers(wrongAnswers)
                .totalMarks(totalMarks.intValue())
                .build();

        Result result = new Result();
        result.setUser(user);
        result.setScore(marksObtained.intValue());
        result.setQuestionMaster(questionMaster);

        studentAnswerRepository.saveAll(studentAnswers);
        resultRepository.save(result);

        return ResponseBean.builder()
                .data(resultResponse)
                .build();
    }

    public ResponseBean getStudentAnswers(Long userId, Long paperId) {
        return ResponseBean.builder()
                .data(studentAnswerRepository.findByUser_UserIdAndQuestion_QuestionMaster_QuestionMasterId(userId,
                        paperId))
                .build();
    }
}
