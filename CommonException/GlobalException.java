package com.grd.online.paper.CommonException;

public class GlobalException {

    public static class UnAuthorized extends RuntimeException {
        public UnAuthorized(String message) {
            super(message);
        }
    }

    public static class BadRequest extends RuntimeException {
        public BadRequest(String message) {
            super(message);
        }
    }

    public static class NotFound extends RuntimeException {
        public NotFound(String message) {
            super(message);
        }
    }
}
