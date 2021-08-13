package com.robobank.dtos.requestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:29 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordRequestDTO {

    private Long id;

    private Integer transactionRef;

    private Integer accountNumber;

    private Double startBalance;

    private Double mutation;

    private Double endBalance;

    private String description;
}
