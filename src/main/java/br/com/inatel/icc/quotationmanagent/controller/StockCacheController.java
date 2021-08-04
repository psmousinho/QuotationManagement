package br.com.inatel.icc.quotationmanagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.icc.quotationmanagent.service.StockManagementService;
import br.com.inatel.icc.quotationmanagent.util.exceptions.StockManagerNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("stockcache")
public class StockCacheController {

	private StockManagementService stockManagementService;

	@Autowired
	public StockCacheController(StockManagementService stockManagementService) {
		this.stockManagementService = stockManagementService;

		try {
			this.stockManagementService.registerForNotifications();
		} catch (StockManagerNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	@DeleteMapping
	@CacheEvict(value = { "getAllStocks", "getStock" }, allEntries = true)
	public ResponseEntity<?> clearCache() {
		log.info("Clearing Caches by request of StockManager API");
		return ResponseEntity.ok().build();
	}
}
