package tw.com.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.ispan.domain.CustomerBean;

public interface CustomerRepository extends JpaRepository<CustomerBean, String> {

}
