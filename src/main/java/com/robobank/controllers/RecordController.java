package com.robobank.controllers;

import com.robobank.dtos.requestDTOs.RecordRequestDTO;
import com.robobank.dtos.responseDTOs.RecordResponseDTO;
import com.robobank.helpers.ApplicationException;
import com.robobank.models.Record;
import com.robobank.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 10:21 AM
 */

@RestController
@RequestMapping(value = "transaction")
public class RecordController {

    @Autowired
    RecordService recordService;


    @PostMapping(value = "/save")
    public ResponseEntity saveRecord(@RequestBody RecordRequestDTO recordRequestDTO) {
        try {
            RecordResponseDTO recordResponseDTO = recordService.saveRecord(recordRequestDTO);
            return ResponseEntity.ok(recordResponseDTO);
        } catch (Exception e) {
             throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/record")
    public ResponseEntity getSingleRecord(@RequestBody RecordRequestDTO recordRequestDTO) {
        try {
            Record record = recordService.getSingleRecordByTransactionRef(recordRequestDTO);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @GetMapping("/test")
        public ResponseEntity<String> get() throws Exception{
            throw new Exception("not nice");
        }


}
