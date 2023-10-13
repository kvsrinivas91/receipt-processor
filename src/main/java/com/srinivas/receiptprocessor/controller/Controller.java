package com.srinivas.receiptprocessor.controller;

import com.srinivas.receiptprocessor.DTO.PostResponseDTO;
import com.srinivas.receiptprocessor.model.Receipt;
import com.srinivas.receiptprocessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class Controller {

    @Autowired
    ReceiptService receiptService;

    @PostMapping("/process")
    public PostResponseDTO saveReceipt(@RequestBody Receipt receipt){
        String receiptId = receiptService.save(receipt);
        return new PostResponseDTO(receiptId);
    }

    @GetMapping("/{id}/points")
    public int getPoints(@PathVariable String id){
        return receiptService.getPoints(id);
    }



}
