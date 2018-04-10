package com.web.processor;

import org.springframework.batch.item.ItemProcessor;

import com.web.model.Order;

public class OrderProcessor implements ItemProcessor<Order, Order>{

	
	@Override
	public Order process(Order item) throws Exception {
		
		 item.setOffer(item.getOffer().toUpperCase());
		 item.setServiceType(item.getServiceType().toUpperCase());
         return item; 
		
	}

}
