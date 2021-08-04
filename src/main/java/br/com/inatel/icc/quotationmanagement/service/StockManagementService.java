package br.com.inatel.icc.quotationmanagement.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.inatel.icc.quotationmanagement.controller.dto.NotificationDTO;
import br.com.inatel.icc.quotationmanagement.model.Stock;
import br.com.inatel.icc.quotationmanagement.util.exceptions.StockManagerNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockManagementService {
	
	@Value("${stock-manager.url}")
	private String apiUrl;
	private RestTemplate restTemplate = new RestTemplate();

	@Cacheable("getAllStocks")
	public List<Stock> getAllStocks() {
		log.info("Acessing StockManager API to colect all stocks");
		
		String url = apiUrl + "/stock/";
		Stock[] stockArr = restTemplate.getForObject(url, Stock[].class);
		return Arrays.asList(stockArr);
	}

	@Cacheable("getStock")
	public Stock getStock(String stockId) {
		log.info("Acessing StockManager API to colect specific stock", stockId);

		String url = apiUrl + "/stock/" + stockId;
		return restTemplate.getForObject(url, Stock.class);
	}

	public void registerForNotifications() throws StockManagerNotFoundException {
		log.info("Registering for notifications from StockManager API");
		
		RestTemplate restTemplete = new RestTemplate();

		String url = apiUrl + "/notification";

		Map<String, Object> map = new HashMap<>();
		map.put("host", "localhost");
		map.put("port", 8081);

		ResponseEntity<NotificationDTO[]> response = restTemplete.postForEntity(url, map, NotificationDTO[].class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new StockManagerNotFoundException("Unable to register service for notifications");
		}
	}

}
