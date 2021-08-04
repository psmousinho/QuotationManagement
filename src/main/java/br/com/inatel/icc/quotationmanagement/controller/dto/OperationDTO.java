package br.com.inatel.icc.quotationmanagement.controller.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.com.inatel.icc.quotationmanagement.model.Operation;

public class OperationDTO {

	private String opId;
	private String stockId;
	private Map<LocalDate, String> quotes;

	public OperationDTO(Operation operation) {
		opId = operation.getUuid().toString();
		stockId = operation.getStockId();
		quotes = new HashMap<LocalDate, String>();
		operation.getQuotes().forEach(q -> quotes.put(q.getDate(), q.getPrice().toString()));
	}

	public String getOpId() {
		return opId;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<LocalDate, String> getQuotes() {
		return quotes;
	}

}
