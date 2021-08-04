package br.com.inatel.icc.quotationmanagent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String stockId;
	@OneToMany(mappedBy = "operation", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private List<Quote> quotes;

	public Operation() {
		super();
	}

	public Operation(String stockId) {
		this.stockId = stockId;
		this.quotes = new ArrayList<>();
	}

	public UUID getOpId() {
		return id;
	}

	public void setOpId(UUID opId) {
		this.id = opId;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public boolean addQuote(Quote quote) {
		this.quotes.add(quote);
		return true;
	}

}
