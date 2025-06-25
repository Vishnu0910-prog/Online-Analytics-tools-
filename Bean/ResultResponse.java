package com.grd.online.paper.Bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultResponse {
    public Integer totalMarks;
    public Integer marksObtained;
    public Integer correctAnswers;
    public Integer wrongAnswers;
    public ResultStatus status;

    public static enum ResultStatus {
        PASS,
        FAIL,
    }
}
