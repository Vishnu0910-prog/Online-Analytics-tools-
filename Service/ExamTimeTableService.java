package com.grd.online.paper.Service;

import com.grd.online.paper.PROPERTIES;
import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.CommonException.GlobalException;
import com.grd.online.paper.Model.ExamTimeTable;
import com.grd.online.paper.Model.Result;
import com.grd.online.paper.Repository.ExamTimeTableRepository;
import com.grd.online.paper.Repository.ResultRepository;
import com.grd.online.paper.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamTimeTableService {

    @Autowired
    private ExamTimeTableRepository examTimeTableRepository;

    @Autowired
    private ResultRepository resultRepository;

    public ResponseBean save(ExamTimeTable examTimeTable) {

        if (examTimeTable.getExamStartTime().isEqual(examTimeTable.getExamEndTime())
                || examTimeTable.getExamEndTime().isBefore(examTimeTable.getExamStartTime()))
            throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.endTimeShouldBeGreater);

        List<ExamTimeTable> isExist = examTimeTableRepository.findByExamEndTimeAfter(examTimeTable.getExamStartTime());
        if (!isExist.isEmpty())
            throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.ExamAlreadyExists);

        return ResponseBean.builder().data(examTimeTableRepository.save(examTimeTable)).build();
    }

    public ResponseBean findAll() {
        return ResponseBean.builder()
                .data(examTimeTableRepository.findAll())
                .build();
    }

    public ResponseBean findAllFutureByDate(Long milliSeconds) {
        LocalDateTime dateTime = DateParser.millisecondsToLocalDateTime(milliSeconds);

        return ResponseBean.builder()
                .data(examTimeTableRepository.findByExamEndTimeGreaterThanEqual(dateTime))
                .build();
    }

    public ResponseBean findByDateTime(Long studentId, Long milliSeconds) {
        LocalDateTime dateTime = DateParser.millisecondsToLocalDateTime(milliSeconds).plusMinutes(5);

        List<ExamTimeTable> _response = new ArrayList<ExamTimeTable>();
        List<ExamTimeTable> _examTimeTables = examTimeTableRepository
                .findByExamStartTimeLessThanEqualAndExamEndTimeGreaterThanEqual(dateTime, dateTime);

        for (ExamTimeTable examTimeTable : _examTimeTables) {
            Result _result = resultRepository.findByUser_UserIdAndQuestionMaster_QuestionMasterId(studentId,
                    examTimeTable.getQuestionMaster().getQuestionMasterId()).orElse(null);
            if (_result == null)
                _response.add(examTimeTable);
        }

        return ResponseBean.builder().data(_response).build();
    }
}
