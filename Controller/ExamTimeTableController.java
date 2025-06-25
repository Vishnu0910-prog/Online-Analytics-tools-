package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.ExamTimeTable;
import com.grd.online.paper.Service.ExamTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/examtimetable")
@CrossOrigin
public class ExamTimeTableController {

    @Autowired
    private ExamTimeTableService examTimeTableService;

    @GetMapping(path = "")
    private ResponseEntity<ResponseBean> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(examTimeTableService.findAll());
    }

    // @GetMapping(path = "/greaterOrEqualDate/{milliSeconds}")
    // private ResponseEntity<ResponseBean>
    // findAllFuture(@PathVariable("milliSeconds") Long milliSeconds) {
    // return
    // ResponseEntity.status(HttpStatus.OK).body(examTimeTableService.findAllFutureByDate(milliSeconds));
    // }

    @GetMapping(path = "/ondate/{studentId}/{milliSeconds}")
    private ResponseEntity<ResponseBean> findByDateTime(@PathVariable("studentId") Long studentId,
            @PathVariable("milliSeconds") Long milliSeconds) {
        return ResponseEntity.status(HttpStatus.OK).body(examTimeTableService.findByDateTime(studentId, milliSeconds));
    }

    @PostMapping(path = "")
    private ResponseEntity<ResponseBean> save(@RequestBody ExamTimeTable examTimeTable) {
        return ResponseEntity.status(HttpStatus.OK).body(examTimeTableService.save(examTimeTable));
    }
}