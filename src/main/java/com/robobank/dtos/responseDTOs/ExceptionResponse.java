package com.robobank.dtos.responseDTOs;

import com.robobank.helpers.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Convert;
import java.util.List;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 12:24 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionResponse {

    private Integer reference;

    private Integer accountNumber;
}
