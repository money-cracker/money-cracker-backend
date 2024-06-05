package com.cracker.money.common.exception;

import com.cracker.money.common.response.BaseResponse;
import com.cracker.money.common.response.BaseResponseStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<BaseResponseStatus> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid argument type: " + ex.getName();
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(errorMessage);
        return new BaseResponse<>(responseStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<BaseResponseStatus> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        log.warn("MethodArgumentNotValidException. field: {}, error: {}", fieldError.getField(), fieldError.getDefaultMessage());
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(fieldError.getDefaultMessage());
        return new BaseResponse<>(responseStatus);
    }

}
