package br.com.inatel.icc.quotationmanagent.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("stockcache")
public class StockCacheController {

	@DeleteMapping
	@CacheEvict(value = { "getAllStocks", "getStock" }, allEntries = true)
	public ResponseEntity<?> clearCache() {
		log.info("Cleaning caches");
		return ResponseEntity.ok().build();
	}
}
