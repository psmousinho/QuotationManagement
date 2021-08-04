package br.com.inatel.icc.quotationmanagent.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import br.com.inatel.icc.quotationmanagent.model.Operation;
import br.com.inatel.icc.quotationmanagent.model.Quote;

public class OperationForm {

	private String stockId;
	private Map<LocalDate, BigDecimal> quotes;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, BigDecimal> quotes) {
		this.quotes = quotes;
	}

	public Operation convert() {
		Operation op = new Operation(stockId);
		quotes.forEach((date, price) -> op.addQuote(new Quote(op, date, price)));
		return op;
	}

}
