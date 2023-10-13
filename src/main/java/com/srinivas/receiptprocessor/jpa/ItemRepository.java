package com.srinivas.receiptprocessor.jpa;

import com.srinivas.receiptprocessor.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}
