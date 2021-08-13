package com.robobank.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public enum ApplicationException {

    SUCCESSFUL("SUCCESSFUL","SUCCESSFUL", HttpStatus.OK),
    DUPLICATE_REFERENCE("DUPLICATE_REFERENCE","DUPLICATE_REFERENCE",HttpStatus.OK),
    DUPLICATE_REFERENCE_INCORRECT_END_BALANCE("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE","DUPLICATE_REFERENCE_INCORRECT_END_BALANCE",HttpStatus.OK),
    BAD_REQUEST("BAD_REQUEST","BAD_REQUEST",HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR),
    INCORRECT_END_BALANCE("INCORRECT_END_BALANCE","INCORRECT_END_BALANCE",HttpStatus.OK);

    private final HttpStatus serverErrorCode;
    private final String errorCode;
    private final String errorKey;

    private ApplicationException(String errorCode, String errorKey, HttpStatus serverErrorCode){
        this.errorCode = errorCode;
        this.errorKey = errorKey;
        this.serverErrorCode = serverErrorCode;
    }

    public ResponseEntity errorEntity(Exception exception, Map<String, Object> extraParameters){
        Map<String, Object> responseJSON = Utilities.packetObject("code", errorCode);
//        responseJSON.put("messageEn", ExceptionTranslator.toLocale(errorKey, Enumerations.Language.ENGLISH));
//        responseJSON.put("messageAr", ExceptionTranslator.toLocale(errorKey, Enumerations.Language.ARABIC));
        responseJSON.put("originalError", exception.getLocalizedMessage());
        if(extraParameters != null){
            responseJSON.putAll(extraParameters);
        }
        return ResponseEntity.status(serverErrorCode).body(responseJSON);
    }
    public ResponseEntity errorEntity(Exception exception){
        return errorEntity(exception, null);
    }

}
