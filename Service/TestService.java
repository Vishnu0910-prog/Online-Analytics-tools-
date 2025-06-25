package com.grd.online.paper.Service;

import com.grd.online.paper.utils.XlsxReaderUtil;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Object testXlsx() {
        try {
            return XlsxReaderUtil.readExcelFromFile("D:/Project/paper/ProjectpaperQuestion-bank-sample.xlsx", true);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
