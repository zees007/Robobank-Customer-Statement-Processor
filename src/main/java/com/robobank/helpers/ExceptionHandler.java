package com.robobank.helpers;

import com.robobank.dtos.responseDTOs.ExceptionResponse;
import com.robobank.dtos.responseDTOs.RecordResponseDTO;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;



import java.util.ArrayList;
import java.util.List;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 12, 2021 - 9:24 AM
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<RecordResponseDTO> handleAllExceptions(Exception ex, WebRequest request) {
        RecordResponseDTO recordResponseDTO = new RecordResponseDTO();
        if (ex.getMessage().contains("JSON parse error")) {
            recordResponseDTO.setResult(ApplicationException.BAD_REQUEST.toString());
            List<ExceptionResponse> exceptionResponses = new ArrayList<>();
            recordResponseDTO.setErrorRecords(exceptionResponses);
            return new ResponseEntity<>(recordResponseDTO, HttpStatus.BAD_REQUEST);
        } else {
            recordResponseDTO.setResult(ApplicationException.INTERNAL_SERVER_ERROR.toString());
            List<ExceptionResponse> exceptionResponses = new ArrayList<>();
            recordResponseDTO.setErrorRecords(exceptionResponses);
            return new ResponseEntity<>(recordResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
