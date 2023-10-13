package com.srinivas.receiptprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;
    private int points;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<Item> items;

}
