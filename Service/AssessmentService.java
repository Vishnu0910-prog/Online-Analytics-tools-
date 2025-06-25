package com.grd.online.paper.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grd.online.paper.PROPERTIES;
import com.grd.online.paper.Bean.ExamTimeTableBean;
import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.CommonException.GlobalException;
import com.grd.online.paper.Model.ExamTimeTable;
import com.grd.online.paper.Model.QuestionMaster;
import com.grd.online.paper.Model.UserModel;
import com.grd.online.paper.Repository.ExamTimeTableRepository;
import com.grd.online.paper.Repository.QuestionMasterRepository;
import com.grd.online.paper.utils.DateParser;
import com.grd.online.paper.utils.XlsxReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private QuestionMasterRepository questionMasterRepository;

    @Autowired
    private ExamTimeTableRepository examTimeTableRepository;

    @Autowired
    private QuestionService questionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseBean saveAssessment(String questionMasterJson, String examTimeTableJson, String userJson,
            MultipartFile file) {
        try {
            QuestionMaster questionMaster = objectMapper.readValue(questionMasterJson,
                    new TypeReference<QuestionMaster>() {
                    });
            ExamTimeTableBean examTimeTableBean = objectMapper.readValue(examTimeTableJson,
                    new TypeReference<ExamTimeTableBean>() {
                    });
            UserModel user = objectMapper.readValue(userJson, new TypeReference<UserModel>() {
            });

            List<ExamTimeTable> isExist = examTimeTableRepository.findByExamEndTimeAfter(
                    DateParser.millisecondsToLocalDateTime(examTimeTableBean.getExamStartTime()));
            if (!isExist.isEmpty())
                throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.ExamAlreadyExists);

            List<List<String>> xlsx = XlsxReaderUtil.readExcelFromMultipart(file, true);

            if (xlsx == null || xlsx.isEmpty())
                throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.xlsxFileShouldNotEmpty);

            // Save question master ...
            questionMaster.setCreatedBy(user);
            QuestionMaster savedQuestionMaster = questionMasterRepository.save(questionMaster);

            // Save exam timeTable ...
            ExamTimeTable examTimeTable = new ExamTimeTable();
            examTimeTable
                    .setExamStartTime(DateParser.millisecondsToLocalDateTime(examTimeTableBean.getExamStartTime()));
            examTimeTable.setExamEndTime(DateParser.millisecondsToLocalDateTime(examTimeTableBean.getExamEndTime()));
            examTimeTable.setQuestionMaster(savedQuestionMaster);
            examTimeTable.setCreatedBy(user);
            examTimeTableRepository.save(examTimeTable);

            // Save question from xlsx and save file path ...
            questionService.uploadFromXLSX(file, questionMaster.getQuestionMasterId(), user.getUserId());

            return ResponseBean.builder()
                    .message(PROPERTIES.MESSAGE.assessmentSaved)
                    .build();
        } catch (Exception e) {
            throw new GlobalException.BadRequest(e.getMessage());
        }
    }
}