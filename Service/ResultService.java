package com.grd.online.paper.Service;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.Result;
import com.grd.online.paper.Repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    public ResponseBean saveResult(Result result) {
        return ResponseBean.builder()
                .data(resultRepository.save(result))
                .build();
    }

    public ResponseBean getResultsByPaperId(Long paperId) {
        return ResponseBean.builder()
                .data(resultRepository.findByQuestionMaster_QuestionMasterId(paperId))
                .build();
    }
}