package com.srinivas.receiptprocessor.controller;

import com.srinivas.receiptprocessor.DTO.PointsResponseDTO;
import com.srinivas.receiptprocessor.DTO.PostResponseDTO;
import com.srinivas.receiptprocessor.model.Receipt;
import com.srinivas.receiptprocessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/receipts")
public class Controller {

    @Autowired
    ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<? extends Object> saveReceipt(@RequestBody Receipt receipt){


        try{
            String receiptId = receiptService.save(receipt);
            return new ResponseEntity<>(new PostResponseDTO(receiptId), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("The receipt is invalid", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}/points")
    public ResponseEntity<? extends Object> getPoints(@PathVariable String id){

        Optional<Receipt> optionalReceipt = receiptService.findById(id);

        if (optionalReceipt.isPresent()) {
            int points = receiptService.getPoints(id);
            return new ResponseEntity<>(new PointsResponseDTO(points), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Receipt not found", HttpStatus.NOT_FOUND);
        }
    }



}
