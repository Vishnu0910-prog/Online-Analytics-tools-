package com.grd.online.paper.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTimeTableBean {

    private Long examStartTime;

    private Long examEndTime;
}
