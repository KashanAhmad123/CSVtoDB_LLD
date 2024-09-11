package repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import entity.Customer;

public interface CustomerRepo extends CrudRepository<Customer,Integer> {

	
	
}
