package com.grd.online.paper.Controller;

import com.grd.online.paper.Service.TestService;
import com.grd.online.paper.utils.DirectoryUtil;
import com.grd.online.paper.utils.XlsxReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(path = "/xlsx")
    private ResponseEntity<?> xlsxReader() {
        return ResponseEntity.status(HttpStatus.OK).body(testService.testXlsx());
    }

    @PostMapping(path = "/upload")
    private ResponseEntity<?> upload(@RequestParam MultipartFile file) {
        try {
            DirectoryUtil.createFile(null, file, "Abhi-" + new Date());
            return ResponseEntity.status(HttpStatus.OK).body(XlsxReaderUtil.readExcelFromMultipart(file, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
