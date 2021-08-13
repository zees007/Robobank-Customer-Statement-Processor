package com.robobank;

import com.robobank.dtos.requestDTOs.RecordRequestDTO;
import com.robobank.helpers.CustomModelMapper;
import com.robobank.repositories.RecordRepository;
import com.robobank.services.RecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RobobankApplicationTests {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    /*
    Record Successful Case
    if no duplicate reference and correct end balance
     */
    @Test
    public void saveRecordTest_SuccessfulCase(){
        //Record record = new Record(1L,111,222,50.0,5.0,55.0,"successful case");
        RecordRequestDTO recordRequestDTO = new RecordRequestDTO();
        recordRequestDTO.setId(null);
        recordRequestDTO.setTransactionRef(1111);
        recordRequestDTO.setAccountNumber(2222);
        recordRequestDTO.setStartBalance(50.0);
        recordRequestDTO.setMutation(5.0);
        recordRequestDTO.setEndBalance(55.0);
        recordRequestDTO.setDescription("Successful_Case");
        recordService.saveRecord(recordRequestDTO);
        assertNotNull(recordRepository.findFirstById(1L));
    }

    /*
       Record Incorrect Case
       if no duplicate reference and Incorrect end balance
       Record will not save
     */
    @Test
    public void saveRecordTest_IncorrectEndBalance(){
        RecordRequestDTO recordRequestDTO = new RecordRequestDTO();
        recordRequestDTO.setId(null);
        recordRequestDTO.setTransactionRef(5555);
        recordRequestDTO.setAccountNumber(66666);
        recordRequestDTO.setStartBalance(50.0);
        recordRequestDTO.setMutation(5.0);
        recordRequestDTO.setEndBalance(52.0);
        recordRequestDTO.setDescription("IncorrectEndBalance_Case");
        recordService.saveRecord(recordRequestDTO);
        assertNull(recordRepository.findByTransactionRef(5555));
    }

    /*
    Record Duplicate and Incorrect Case
    if  duplicate reference and Incorrect end balance
     */
    @Test
    public void saveRecordTest_Duplicate_IncorrectEndBalance(){
        //Record record = new Record(1L,111,222,50.0,5.0,55.0,"successful case");
        RecordRequestDTO recordRequestDTO = new RecordRequestDTO();
        recordRequestDTO.setId(null);
        recordRequestDTO.setTransactionRef(2222);
        recordRequestDTO.setAccountNumber(3333);
        recordRequestDTO.setStartBalance(50.0);
        recordRequestDTO.setMutation(5.0);
        recordRequestDTO.setEndBalance(55.0);
        recordRequestDTO.setDescription("Successful_Case");
        recordService.saveRecord(recordRequestDTO);
        RecordRequestDTO recordRequestDTO1 = new RecordRequestDTO();
        recordRequestDTO1.setId(null);
        recordRequestDTO1.setTransactionRef(2222);
        recordRequestDTO1.setAccountNumber(4444);
        recordRequestDTO1.setStartBalance(50.0);
        recordRequestDTO1.setMutation(5.0);
        recordRequestDTO1.setEndBalance(52.0);
        recordRequestDTO1.setDescription("Duplicate_IncorrectEndBalance_Case");
        recordService.saveRecord(recordRequestDTO1);
        assertNotNull(recordRepository.findByTransactionRef(2222));
    }

    /*
    Record Duplicate and correct end Balance Case
    if duplicate reference and correct end balance
     */
    @Test
    public void saveRecordTest_Duplicate_CorrectEndBalance(){
        RecordRequestDTO recordRequestDTO = new RecordRequestDTO();
        recordRequestDTO.setId(null);
        recordRequestDTO.setTransactionRef(9999);
        recordRequestDTO.setAccountNumber(87777);
        recordRequestDTO.setStartBalance(50.0);
        recordRequestDTO.setMutation(5.0);
        recordRequestDTO.setEndBalance(55.0);
        recordRequestDTO.setDescription("Successful_Case");
        recordService.saveRecord(recordRequestDTO);
        RecordRequestDTO recordRequestDTO1 = new RecordRequestDTO();
        recordRequestDTO1.setId(null);
        recordRequestDTO1.setTransactionRef(9999);
        recordRequestDTO1.setAccountNumber(656565);
        recordRequestDTO1.setStartBalance(50.0);
        recordRequestDTO1.setMutation(5.0);
        recordRequestDTO1.setEndBalance(52.0);
        recordRequestDTO1.setDescription("Duplicate_CorrectEndBalance_Case");
        recordService.saveRecord(recordRequestDTO1);
        assertNotNull(recordRepository.findByTransactionRef(9999));
    }
    
     @Test
    public void internalServerErrorTest(){
        RecordRequestDTO recordRequestDTO = new RecordRequestDTO();
        recordRequestDTO.setId(null);
        recordRequestDTO.setTransactionRef(9999);
        recordRequestDTO.setAccountNumber(87777);
        recordRequestDTO.setStartBalance(50.0);
        recordRequestDTO.setMutation(5.0);
        recordRequestDTO.setEndBalance(55.0);
        recordRequestDTO.setDescription("Successful_Case");
        recordService.saveRecord(recordRequestDTO);
        //fetching not exist transaction record
        assertNull(recordRepository.findByTransactionRef(100));
    }

}
