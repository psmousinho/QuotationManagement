package br.com.inatel.icc.quotationmanagement.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.inatel.icc.quotationmanagement.controller.dto.OperationDTO;
import br.com.inatel.icc.quotationmanagement.controller.form.OperationForm;
import br.com.inatel.icc.quotationmanagement.model.Operation;
import br.com.inatel.icc.quotationmanagement.model.Quote;
import br.com.inatel.icc.quotationmanagement.model.Stock;
import br.com.inatel.icc.quotationmanagement.repository.OperationRepository;
import br.com.inatel.icc.quotationmanagement.service.StockManagementService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OperationControllerCucumberSteps {

	@Mock
	private OperationRepository operationRepository;
	@Mock
	private StockManagementService stockManagementService;

	private OperationController operationController;

	private ResponseEntity<?> getResponseEntity;
	private ResponseEntity<?> postResponseEntity;

	private Operation postOperation;

	@Given("the list of valid stockId on StockManager")
	public void the_list_of_valid_stock_id_on_stock_manager() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(stockManagementService.getStock("petr4")).thenReturn(new Stock("petr4", "Test petr4"));
		Mockito.when(stockManagementService.getStock("vale5")).thenReturn(new Stock("vale5", "Test vale5"));
	}

	@Given("previous operations for those stockIds")
	public void previous_operations_for_those_stock_ids() {
		Mockito.when(operationRepository.findByStockId("petr4")).thenReturn(generateOperations(3, "petr4"));
		Mockito.when(operationRepository.findByStockId("vale5")).thenReturn(generateOperations(4, "vale5"));
		Mockito.when(operationRepository.findAll()).thenReturn(generateOperations(7, ""));
				
		operationController = new OperationController(operationRepository, stockManagementService);
	}

	@When("trying to list operations of stockId {string}")
	public void trying_to_list_operations_of_stock_id(String stockId) {
		if (stockId.isEmpty()) {
			getResponseEntity = operationController.getStocks(null);
		} else {
			getResponseEntity = operationController.getStocks(stockId);
		}
	}

	@Then("the  HTTP response status for the get request should be {int}")
	public void the_http_response_status_for_the_get_request_should_be(int statusCode) {
		Assert.assertEquals(getResponseEntity.getStatusCode().value(), statusCode);
	}

	@SuppressWarnings("unchecked")
	@Then("the number of operations return should be {int}")
	public void the_number_of_operations_return_should_be(int nOfOperations) {
		if (nOfOperations != 0) {
			List<OperationDTO> operationsDTO = (List<OperationDTO>) getResponseEntity.getBody();
			Assert.assertEquals(operationsDTO.size(), nOfOperations);
		}
	}

	@Given("an operation with stockId equal to {string}")
	public void an_operation_with_stock_id_equal_to(String stockId) {
		postOperation = generateOperations(1, stockId).get(0);
	}

	@Given("with a list of quotes of size {int}")
	public void with_a_list_of_quotes_of_size(Integer nOfQuotes) {
		postOperation.setQuotes(generateQuotes(nOfQuotes, postOperation));
		Mockito.when(operationRepository.save(Mockito.any())).thenReturn(postOperation);
	}

	@When("trying to register the operation on to QuoteManager")
	public void trying_to_register_the_operation_on_to_quote_manager() {
		OperationForm form = new OperationForm();
		form.setStockId(postOperation.getStockId());
		form.setQuotes(quotesListToMap(postOperation.getQuotes()));

		postResponseEntity = operationController.postOperation(form);
	}

	@Then("the  HTTP response status for the post request should be {int}")
	public void the_http_response_status_for_the_post_request_should_be(int statusCode) {
		Assert.assertEquals(postResponseEntity.getStatusCode().value(), statusCode);
	}

	private List<Operation> generateOperations(int nOfOperations, String stockId) {
		List<Operation> operations = new ArrayList<Operation>();

		for (int i = 0; i < nOfOperations; i++) {
			Operation operation = new Operation(stockId);
			operation.setUuid(UUID.randomUUID());;
			operation.setQuotes(generateQuotes(1, operation));
			operations.add(operation);
		}

		return operations;
	}

	private List<Quote> generateQuotes(int nOfQuotes, Operation operation) {
		List<Quote> quotes = new ArrayList<Quote>();

		for (int i = 1; i <= nOfQuotes; i++) {
			quotes.add(new Quote(operation, LocalDate.now().plusDays(i), BigDecimal.valueOf(i)));
		}

		return quotes;
	}

	private Map<LocalDate, BigDecimal> quotesListToMap(List<Quote> quotes) {
		Map<LocalDate, BigDecimal> quotesMap = new HashMap<>();
		quotes.forEach(q -> quotesMap.put(q.getDate(), q.getPrice()));
		return quotesMap;
	}
}
