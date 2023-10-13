package com.srinivas.receiptprocessor.service;

import com.srinivas.receiptprocessor.jpa.ItemRepository;
import com.srinivas.receiptprocessor.jpa.ReceiptRepository;
import com.srinivas.receiptprocessor.model.Item;
import com.srinivas.receiptprocessor.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

    @Autowired
    public ReceiptRepository receiptRepository;

    @Autowired
    public ItemRepository itemRepository;

    public String save(Receipt receipt){

        try{
            int points = calculatePoints(receipt);

            Receipt toSave =
                    Receipt.builder()
                            .id(receipt.getId())
                            .items(receipt.getItems())
                            .points(points)
                            .purchaseTime(receipt.getPurchaseTime())
                            .purchaseDate(receipt.getPurchaseDate())
                            .retailer(receipt.getRetailer())
                            .total(receipt.getTotal())
                            .build();
            Receipt savedReceipt = receiptRepository.save(toSave);

            List<Item> items = receipt.getItems().stream()
                    .peek(item -> item.setReceipt(savedReceipt)).toList();



            itemRepository.saveAll(items);
            return savedReceipt.getId();
        }
        catch (Exception e){
            throw e;
        }



    }

    public int getPoints(String id){
        Receipt receipt = receiptRepository.findById(id).get();
        return receipt.getPoints();
    }



    public int calculatePoints(Receipt receipt){

        List<Item> items = receipt.getItems();
        DecimalFormat df = new DecimalFormat("0.00");


        int points = 0;
        double total = Double.parseDouble(receipt.getTotal());

        String retailer = receipt.getRetailer();
        String retailerAlphaNumeric = retailer.replaceAll("[^A-Za-z0-9]", "");
        points+=retailerAlphaNumeric.length();



        if(Double.parseDouble(df.format(total % 1)) == 0){
            points += 50;
        }

        if(Double.parseDouble(df.format(total % 0.25)) == 0){
            points += 25;
        }

        points += (items.size()/2)*5;

        for(Item item: items){
            String temp1 = item.getShortDescription().trim();
            if(item.getShortDescription().trim().length() % 3 == 0){
//                double temp = Math.ceil(item.getShortDescription().trim().length() * 0.2);

                points += Math.ceil(item.getPrice() * 0.2);

            }
        }

        String dateString = receipt.getPurchaseDate();
        String[] dateParts = dateString.split("-");
        int day = Integer.parseInt(dateParts[2]);

        if(day% 2 != 0){
            points += 6;
        }

        String timeString = receipt.getPurchaseTime();
        String[] timeParts = timeString.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        if((hours>14 || (hours == 14 && minutes > 0))&& hours <16){
            points += 10;
        }

        return points;
    }

    public Optional<Receipt> findById(String id) {
        return receiptRepository.findById(id);
    }
}
