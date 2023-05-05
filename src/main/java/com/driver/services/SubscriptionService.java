package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
          Subscription subscription= new Subscription();
          User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        // get name of subscribtion in string and calculate cost
        String sub= subscriptionEntryDto.getSubscriptionType().toString();
        int totalAmount =0;

        if(sub.equalsIgnoreCase("elite") || sub.equalsIgnoreCase("pro")||sub.equalsIgnoreCase("basic")) {
            if (sub.equalsIgnoreCase("elite")) {
                subscription.setSubscriptionType(SubscriptionType.ELITE);
                totalAmount = 1000 + (350 * subscriptionEntryDto.getNoOfScreensRequired());
                subscription.setTotalAmountPaid(totalAmount);
            } else if (sub.equalsIgnoreCase("pro")) {
                subscription.setSubscriptionType(SubscriptionType.PRO);
                totalAmount = 800 + (250 * subscriptionEntryDto.getNoOfScreensRequired());
                subscription.setTotalAmountPaid(totalAmount);
            } else if (sub.equalsIgnoreCase("basic")) {
                subscription.setSubscriptionType(SubscriptionType.BASIC);
                totalAmount = 500 + (200 * subscriptionEntryDto.getNoOfScreensRequired());
                subscription.setTotalAmountPaid(totalAmount);
            }

        }
        // set all subcriber parameter
        subscription.setStartSubscriptionDate(new Date());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setUser(user);

        // save
        user.setSubscription(subscription);
        userRepository.save(user);
        return totalAmount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user = userRepository.findById(userId).get();
        Subscription userSubscription = user.getSubscription();
        int paidAmount= user.getSubscription().getTotalAmountPaid();
        int newAmount=0;
        if(userSubscription.getSubscriptionType().toString().equals("ELITE"))
        {
            throw new Exception("Already the best Subscription");

        }
     //  int amount=0;
        if (userSubscription.getSubscriptionType().toString().equals("BASIC")) {
            userSubscription.setSubscriptionType(SubscriptionType.ELITE);
            newAmount = 1000 + (350 * user.getSubscription().getNoOfScreensSubscribed());
            userSubscription.setStartSubscriptionDate(new Date());
            //    userSubscription.setSubscriptionType();
            //subscription.setTotalAmountPaid(totalAmount);
        }
        else  {
            userSubscription.setSubscriptionType(SubscriptionType.PRO);
            newAmount = 800 + (250 * user.getSubscription().getNoOfScreensSubscribed());
                userSubscription.setStartSubscriptionDate(new Date());
        }
        subscriptionRepository.save(userSubscription);
        return newAmount-paidAmount;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        List<Subscription> subscriptionList= subscriptionRepository.findAll();
         int totalRevenue=0;
         for(Subscription subscriptionX:subscriptionList){
             totalRevenue += subscriptionX.getTotalAmountPaid();
         }
        return totalRevenue;
    }

}
