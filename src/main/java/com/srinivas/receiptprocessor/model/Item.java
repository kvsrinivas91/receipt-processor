package com.srinivas.receiptprocessor.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String shortDescription;
    private double price;

    @ManyToOne()
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;


}
