package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.QuestionMaster;
import com.grd.online.paper.Service.QuestionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/questionmaster")
@CrossOrigin
public class QuestionMasterController {

    @Autowired
    private QuestionMasterService questionMasterService;

    @GetMapping(path = "")
    private ResponseEntity<ResponseBean> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(questionMasterService.findAll());
    }

    @PostMapping(path = "")
    private ResponseEntity<ResponseBean> save(@RequestBody QuestionMaster questionMaster) {
        return ResponseEntity.status(HttpStatus.OK).body(questionMasterService.save(questionMaster));
    }
}
