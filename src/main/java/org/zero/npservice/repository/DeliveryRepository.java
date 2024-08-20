package org.zero.npservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zero.npservice.entity.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
  @Query("select d from Delivery d where d.status not in ?1 and d.document != null")
  List<Delivery> findAllByStatusIsNotIn(List<String> statuses);

  Optional<Delivery> findFirstByOrderId(String orderId);
}