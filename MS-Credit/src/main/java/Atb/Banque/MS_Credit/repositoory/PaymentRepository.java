package Atb.Banque.MS_Credit.repositoory;

import Atb.Banque.MS_Credit.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}