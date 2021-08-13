package com.robobank.repositories;

import com.robobank.models.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 9:28 AM
 */
@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {

    Record findFirstByTransactionRef(Integer txn);
    Record findByTransactionRef(Integer txn);
    Record findFirstById(Long id);
    List<Record> findAllBy();
}
