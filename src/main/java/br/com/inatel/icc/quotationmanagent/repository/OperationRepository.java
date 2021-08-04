package br.com.inatel.icc.quotationmanagent.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.icc.quotationmanagent.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID>{

	List<Operation> findByStockId(String stockId);
	
}
