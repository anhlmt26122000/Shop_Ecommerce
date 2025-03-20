package com.final_project.exception;

import com.final_project.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    //other exception
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e){
//        ApiResponse response = new ApiResponse();
//        response.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
//        response.setMessage(ErrorCode.UNKNOWN_ERROR.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }

    //EXCEPTION
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                //error code defined in enum
                .status(errorCode.getStatusCode())
                .body(response);
    }

    //Access Denied exception
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    //VALIDATE
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e){
        String enumKey= Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
