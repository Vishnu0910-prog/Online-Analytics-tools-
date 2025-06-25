package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.Result;
import com.grd.online.paper.Service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
@CrossOrigin
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<ResponseBean> saveResult(@RequestBody Result result) {
        ResponseBean response = resultService.saveResult(result);
        response.setMessage("Result saved successfully"); // Set message in the controller
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/paper/{paperId}")
    public ResponseEntity<ResponseBean> getResultsByPaperId(@PathVariable Long paperId) {
        ResponseBean response = resultService.getResultsByPaperId(paperId);
        response.setMessage("Results retrieved successfully for paper ID " + paperId); // Set message
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}