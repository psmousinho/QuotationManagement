package br.com.inatel.icc.quotationmanagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import br.com.inatel.icc.quotationmanagent.service.StockManagementService;
import br.com.inatel.icc.quotationmanagent.util.exceptions.StockManagerNotFoundException;

@SpringBootApplication
@EnableCaching
public class QuotationManagementApplication {

	public static void main(String[] args) {
		
		try {
			StockManagementService.registerForNotifications();
		} catch (StockManagerNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		SpringApplication.run(QuotationManagementApplication.class, args);
	}

}
