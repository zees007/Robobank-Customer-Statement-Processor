package com.robobank.dtos.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:30 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordResponseDTO {

    private String result;
    private List<ExceptionResponse> errorRecords;
}
