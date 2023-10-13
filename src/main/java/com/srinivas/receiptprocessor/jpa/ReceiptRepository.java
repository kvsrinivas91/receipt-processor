package com.srinivas.receiptprocessor.jpa;

import com.srinivas.receiptprocessor.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ReceiptRepository extends JpaRepository<Receipt, String> {

}
