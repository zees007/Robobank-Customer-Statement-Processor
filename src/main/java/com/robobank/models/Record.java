package com.robobank.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:12 AM
 */

@Entity
@Table(name = "RECORDS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    private Integer transactionRef;

    private Integer accountNumber;

    private Double startBalance;

    private Double mutation;

    private Double endBalance;

    private String description;

}
