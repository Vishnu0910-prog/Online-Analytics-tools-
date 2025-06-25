package com.grd.online.paper.Bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseBean {

    private String message;

    @Builder.Default
    private Status status = Status.SUCCESS;

    private Object data;

    public static enum Status {
        SUCCESS,
        FAILED,
        WARNING,
    }
}
