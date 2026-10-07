package IndiraHub.repository;

import IndiraHub.model.Order;
import IndiraHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByOrderDateDesc(User user);

    List<Order> findByCustomerEmailIgnoreCaseOrderByOrderDateDesc(String customerEmail);

    List<Order> findAllByOrderByOrderDateDesc();
}
