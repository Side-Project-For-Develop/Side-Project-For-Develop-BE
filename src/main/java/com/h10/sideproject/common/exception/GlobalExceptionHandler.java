package com.h10.sideproject.common.exception;

import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ResponseMessage(ex.getErrorCode().getMsg(), ex.getErrorCode().getStatusCode(), ex.getErrorCode())
                , HttpStatus.OK);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity(new ResponseMessage<>(ErrorCode.POLL_REQUIRED_NOT_ENOUGH.getMsg(), HttpStatus.BAD_REQUEST.value(), null)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity ConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity(new ResponseMessage<>(ErrorCode.POLL_REQUIRED_NOT_ENOUGH.getMsg(), HttpStatus.BAD_REQUEST.value(), null)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append("[")
                    .append(error.getField())
                    .append("] ")
                    .append(":")
                    .append(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ResponseMessage(errMessage.toString(), 404, "error"), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({Exception.class})
//    protected ResponseEntity handleServerException(Exception ex) {
//        return new ResponseEntity(new ResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "error")
//                , HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}