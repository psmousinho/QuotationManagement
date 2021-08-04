package br.com.inatel.icc.quotationmanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.icc.quotationmanagement.model.Operation;
import br.com.inatel.icc.quotationmanagement.model.Quote;
import br.com.inatel.icc.quotationmanagement.repository.OperationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
public class OperationRepositoryTest {

	@Autowired
	private OperationRepository operationRepository;

	private Operation testOperation;

	@Before
	public void before() {
		testOperation = new Operation("petr4");
		List<Quote> quotes = genearateQuotes(testOperation, 3);

		testOperation.setQuotes(quotes);
		operationRepository.save(testOperation);
	}

	@Test
	public void shouldFindOperationByStock() {

		List<Operation> operations = operationRepository.findByStockId(testOperation.getStockId());

		Assert.assertNotNull(operations);
		Assert.assertEquals(1, operations.size());
		Assert.assertEquals(testOperation.getStockId(), operations.get(0).getStockId());
		Assert.assertEquals(testOperation.getQuotes().size(), operations.get(0).getQuotes().size());
		Assert.assertEquals(testOperation.getQuotes().get(0).getDate(), operations.get(0).getQuotes().get(0).getDate());

	}

	@Test
	public void shouldNotFindOperationByUnregisteredStock() {

		List<Operation> operations = operationRepository.findByStockId("UnregisteredStock");

		Assert.assertNotNull(operations);
		Assert.assertEquals(0, operations.size());

	}

	private List<Quote> genearateQuotes(Operation operation, int nOfQuotes) {
		List<Quote> quotes = new ArrayList<Quote>();

		for (int i = 1; i <= nOfQuotes; i++) {
			quotes.add(new Quote(operation, LocalDate.now().plusDays(i), BigDecimal.valueOf(i)));
		}

		return quotes;
	}

}
