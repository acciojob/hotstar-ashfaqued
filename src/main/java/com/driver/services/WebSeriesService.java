package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
WebSeries webSeries= webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName());
 if(webSeries!=null)
 {
     throw new Exception("Series is already present");
 }
   WebSeries webSeriesNew = new WebSeries();
 webSeriesNew.setSeriesName(webSeriesEntryDto.getSeriesName());
 webSeriesNew.setAgeLimit(webSeriesEntryDto.getAgeLimit());
 webSeriesNew.setRating(webSeriesEntryDto.getRating());
 webSeriesNew.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

 ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();

 webSeriesNew.setProductionHouse(productionHouse);

 productionHouse.getWebSeriesList().add(webSeriesNew);

 double oldRating = productionHouse.getRatings();
 double newRating=webSeriesNew.getRating();
 //int listSize = productionHouse.getWebSeriesList().size();

 double avRating= (oldRating+newRating)/2;
   productionHouse.setRatings(avRating);

   productionHouseRepository.save(productionHouse);
   //webSeriesNew.setProductionHouse(productionHouse);
     int n= webSeriesRepository.save(webSeriesNew).getId();
        return n;
    }

}
