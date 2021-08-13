package com.robobank.services;

import com.robobank.dtos.requestDTOs.RecordRequestDTO;
import com.robobank.dtos.responseDTOs.RecordResponseDTO;
import com.robobank.helpers.ResourceNotFoundException;
import com.robobank.models.Record;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:40 AM
 */
public interface RecordService {

    RecordResponseDTO saveRecord(RecordRequestDTO recordRequestDTO);

    Record getSingleRecordByTransactionRef(RecordRequestDTO recordRequestDTO) throws ResourceNotFoundException;

}
