package com.grd.online.paper.CommonException;

import com.grd.online.paper.Bean.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.grd.online.paper")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = GlobalException.UnAuthorized.class)
    private ResponseEntity<ResponseBean> throwUnAuthorizedException(GlobalException.UnAuthorized ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ResponseBean response = ResponseBean.builder()
                .status(ResponseBean.Status.FAILED)
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(value = GlobalException.BadRequest.class)
    private ResponseEntity<ResponseBean> throwBadRequestException(GlobalException.BadRequest ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseBean response = ResponseBean.builder()
                .status(ResponseBean.Status.FAILED)
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(value = GlobalException.NotFound.class)
    private ResponseEntity<ResponseBean> throwNotFoundException(GlobalException.NotFound ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseBean response = ResponseBean.builder()
                .status(ResponseBean.Status.FAILED)
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}