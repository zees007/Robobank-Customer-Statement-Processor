package com.robobank.services.implementations;

import com.robobank.dtos.requestDTOs.RecordRequestDTO;
import com.robobank.dtos.responseDTOs.ExceptionResponse;
import com.robobank.dtos.responseDTOs.RecordResponseDTO;
import com.robobank.helpers.ApplicationException;
import com.robobank.helpers.CustomModelMapper;
import com.robobank.helpers.ResourceNotFoundException;
import com.robobank.models.Record;
import com.robobank.repositories.RecordRepository;
import com.robobank.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:41 AM
 */

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    CustomModelMapper modelMapper;

    @Override
    public RecordResponseDTO saveRecord(RecordRequestDTO recordRequestDTO) {
        if (recordRequestDTO.getAccountNumber() == null) {
            throw new RuntimeException("Parameter customer account number is not found in request..!!");
        } else if (recordRequestDTO.getStartBalance() == null) {
            throw new RuntimeException("Parameter customer start balance not found in request..!!");
        } else if (recordRequestDTO.getEndBalance() == null) {
            throw new RuntimeException("Parameter customer end balance is not found in request..!!");
        } else if (recordRequestDTO.getTransactionRef() == null) {
            throw new RuntimeException("Parameter customer transaction reference is not found in request..!!");
        }

        Record savedRecord = null;
        Record record = modelMapper.map(recordRequestDTO, Record.class);
        ExceptionResponse exceptionResponse = null;
        RecordResponseDTO recordResponseDTO = new RecordResponseDTO();
        List<ExceptionResponse> listOfExpRes = null;

        if(recordRequestDTO.getId() != null){
            Record oldRecord = recordRepository.findFirstById(recordRequestDTO.getId());
            if(oldRecord != null){
                oldRecord.setId(record.getId());
                oldRecord.setTransactionRef(record.getTransactionRef());
                oldRecord.setAccountNumber(record.getAccountNumber());
                oldRecord.setStartBalance(record.getStartBalance());
                oldRecord.setMutation(record.getMutation());
                oldRecord.setEndBalance(record.getEndBalance());
                oldRecord.setDescription(record.getDescription());
                savedRecord = recordRepository.save(oldRecord);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + recordRequestDTO.getId());
            }
        } else {

            List<Record> records = recordRepository.findAllBy();
            Boolean hasDuplicateTnx = records.stream().anyMatch(txn -> txn.getTransactionRef().equals(recordRequestDTO.getTransactionRef()));
            exceptionResponse = new ExceptionResponse();
            listOfExpRes = new ArrayList<>();

            //No duplicate reference and Correct End Balance
            if(!hasDuplicateTnx && ((recordRequestDTO.getStartBalance() + recordRequestDTO.getMutation() == recordRequestDTO.getEndBalance()) ||
                    (recordRequestDTO.getStartBalance() - recordRequestDTO.getMutation() == recordRequestDTO.getEndBalance())) ){
                recordResponseDTO.setResult(ApplicationException.SUCCESSFUL.toString());
                recordResponseDTO.setErrorRecords(listOfExpRes);
                savedRecord = recordRepository.save(record);

            }
            // Duplicate reference and correct End Balance
            else if(hasDuplicateTnx && ((recordRequestDTO.getStartBalance() + recordRequestDTO.getMutation() == recordRequestDTO.getEndBalance()) ||
                    (recordRequestDTO.getStartBalance() - recordRequestDTO.getMutation() == recordRequestDTO.getEndBalance())) ){
                recordResponseDTO.setResult(ApplicationException.DUPLICATE_REFERENCE.toString());
                exceptionResponse.setReference(recordRequestDTO.getTransactionRef());
                exceptionResponse.setAccountNumber(recordRequestDTO.getAccountNumber());
                listOfExpRes.add(exceptionResponse);
                recordResponseDTO.setErrorRecords(listOfExpRes);

            }
            // No duplicate Reference and Incorrect End Balance
            else if (!hasDuplicateTnx && ((recordRequestDTO.getStartBalance() + recordRequestDTO.getMutation() != recordRequestDTO.getEndBalance()) ||
                    (recordRequestDTO.getStartBalance() - recordRequestDTO.getMutation() != recordRequestDTO.getEndBalance()))) {

                recordResponseDTO.setResult(ApplicationException.INCORRECT_END_BALANCE.toString());
                exceptionResponse.setReference(recordRequestDTO.getTransactionRef());
                exceptionResponse.setAccountNumber(recordRequestDTO.getAccountNumber());
                listOfExpRes.add(exceptionResponse);
                recordResponseDTO.setErrorRecords(listOfExpRes);
            }
            // Duplicate Reference and Incorrect End Balance
            else if(hasDuplicateTnx && ((recordRequestDTO.getStartBalance() + recordRequestDTO.getMutation() != recordRequestDTO.getEndBalance()) ||
                    (recordRequestDTO.getStartBalance() - recordRequestDTO.getMutation() != recordRequestDTO.getEndBalance()) )){
                recordResponseDTO.setResult(ApplicationException.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString());
                //Duplicate reference record
                Record duplicateRecord = recordRepository.findByTransactionRef(recordRequestDTO.getTransactionRef());
                ExceptionResponse duplicateExceptionRes = new ExceptionResponse();
                duplicateExceptionRes.setReference(duplicateRecord.getTransactionRef());
                duplicateExceptionRes.setAccountNumber(duplicateRecord.getAccountNumber());
                listOfExpRes.add(duplicateExceptionRes);

                //Incorrect Balance record
                exceptionResponse.setReference(recordRequestDTO.getTransactionRef());
                exceptionResponse.setAccountNumber(recordRequestDTO.getAccountNumber());
                listOfExpRes.add(exceptionResponse);

                recordResponseDTO.setErrorRecords(listOfExpRes);
            }
        }

        return recordResponseDTO;
    }

    @Override
    public Record getSingleRecordByTransactionRef(RecordRequestDTO recordRequestDTO) throws ResourceNotFoundException {
        Record record = recordRepository.findByTransactionRef(recordRequestDTO.getTransactionRef());
        if(record == null){
            throw new ResourceNotFoundException("Internal Server Error");
        }
        return record;
    }
}
