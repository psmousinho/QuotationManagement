package br.com.inatel.icc.quotationmanagent.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.icc.quotationmanagent.controller.dto.ErrorDTO;
import br.com.inatel.icc.quotationmanagent.controller.dto.OperationDTO;
import br.com.inatel.icc.quotationmanagent.controller.form.OperationForm;
import br.com.inatel.icc.quotationmanagent.model.Operation;
import br.com.inatel.icc.quotationmanagent.model.Stock;
import br.com.inatel.icc.quotationmanagent.repository.OperationRepository;
import br.com.inatel.icc.quotationmanagent.service.StockManagementService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("operation")
public class OperationController {

	private OperationRepository operationRepository;
	private StockManagementService stockManagementService;

	@Autowired
	public OperationController(OperationRepository operationRepository, StockManagementService stockManagementService) {
		this.operationRepository = operationRepository;
		this.stockManagementService = stockManagementService;
	}

	@GetMapping
	public ResponseEntity<?> getStocks(@RequestParam(required = false) String stockId) {
		log.info("Running get Stocks request");

		List<Operation> operations;
		if (stockId != null) {

			Stock stock = stockManagementService.getStock(stockId);
			if (stock == null) {
				log.warn("StockId received in the request is not valid");
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorDTO("StockId", "Unregistered StockId"));
			}

			log.info("Getting by StockId");
			operations = operationRepository.findByStockId(stockId);
		} else {
			log.info("Getting all Stocks");
			operations = operationRepository.findAll();
		}

		List<OperationDTO> operationsDTO = operations.stream().map(OperationDTO::new).collect(Collectors.toList());;
		return ResponseEntity.ok(operationsDTO);
	}

	@PostMapping
	public ResponseEntity<?> postOperation(@RequestBody @Valid OperationForm form) {
		log.info("Running post operation request");

		Operation operation = form.convert();
		Stock stock = stockManagementService.getStock(operation.getStockId());

		if (stock == null) {
			log.warn("StockId received in the request is not valid");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("StockId", "Unregistered StockId"));
		}

		if (operation.getQuotes().isEmpty()) {
			log.warn("Operation received contains zero quotes");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("quotes", "Empty field"));
		}

		operation = operationRepository.save(operation);
		return ResponseEntity.created(null).body(new OperationDTO(operation));
	}

}
