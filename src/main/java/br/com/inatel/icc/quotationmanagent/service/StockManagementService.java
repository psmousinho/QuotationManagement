package br.com.inatel.icc.quotationmanagent.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.inatel.icc.quotationmanagent.controller.dto.NotificationDTO;
import br.com.inatel.icc.quotationmanagent.model.Stock;
import br.com.inatel.icc.quotationmanagent.util.exceptions.StockManagerNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockManagementService {

	private static String apiUrl = "http://localhost:8080";
	private RestTemplate restTemplate = new RestTemplate();

	@Cacheable("getAllStocks")
	public List<Stock> getAllStocks() {
		log.info("Acessing service-manager API to colect all stocks");
		String url = apiUrl + "/stock/";
		Stock[] stockArr = restTemplate.getForObject(url, Stock[].class);
		return Arrays.asList(stockArr);
	}

	@Cacheable("getStock")
	public Stock getStock(String stockId) {
		log.info("Acessing service-manager API to colect stock with value {}", stockId);
		String url = apiUrl + "/stock/" + stockId;
		return restTemplate.getForObject(url, Stock.class);
	}

	public static void registerForNotifications() throws StockManagerNotFoundException {
		RestTemplate restTemplete = new RestTemplate();

		String url = "http://localhost:8080/notification";

		Map<String, Object> map = new HashMap<>();
		map.put("host", "localhost");
		map.put("port", 8081);

		ResponseEntity<NotificationDTO[]> response = restTemplete.postForEntity(url, map, NotificationDTO[].class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new StockManagerNotFoundException("Unable to register service for notifications");
		}
	}

}
