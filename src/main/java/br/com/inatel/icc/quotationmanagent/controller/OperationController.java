package br.com.inatel.icc.quotationmanagent.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.icc.quotationmanagent.controller.dto.OperationDTO;
import br.com.inatel.icc.quotationmanagent.controller.form.OperationForm;
import br.com.inatel.icc.quotationmanagent.model.Operation;
import br.com.inatel.icc.quotationmanagent.model.Stock;
import br.com.inatel.icc.quotationmanagent.repository.OperationRepository;
import br.com.inatel.icc.quotationmanagent.service.StockManagementService;

@RestController
@RequestMapping("operation")
public class OperationController {

	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private StockManagementService stockManagementService;

	@GetMapping
	public ResponseEntity<List<OperationDTO>> getByStockId(@RequestParam(required = false) String stockId) {

		List<Operation> operations;
		if (stockId != null) {
			operations = operationRepository.findByStockId(stockId);
		} else {
			operations = operationRepository.findAll();
		}

		List<OperationDTO> operationsDTO = operations.stream().map(OperationDTO::new).toList();
		return ResponseEntity.ok(operationsDTO);
	}

	@PostMapping
	public ResponseEntity<OperationDTO> postOperation(@RequestBody @Valid OperationForm form,
			UriComponentsBuilder uriBuilder) {

		Operation operation = form.convert();
		Stock stock = stockManagementService.getStock(operation.getStockId());

		if (stock != null) {
			operationRepository.save(operation);
			URI uri = uriBuilder.path("/operation/{id}").buildAndExpand(operation.getOpId()).toUri();
			return ResponseEntity.created(uri).body(new OperationDTO(operation));
		}

		return ResponseEntity.notFound().build();
	}

}
