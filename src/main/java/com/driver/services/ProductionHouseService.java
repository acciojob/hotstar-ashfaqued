package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse =new ProductionHouse();
        // set production name fro dto and rating =0
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setRatings(0);
        //create empty list and add to production house
        List<WebSeries> webList = new ArrayList<>();
        productionHouse.setWebSeriesList(webList);


        return  productionHouseRepository.save(productionHouse).getId();
    }



}
