package Atb.Banque.MS_Credit.repositoory;

import Atb.Banque.MS_Credit.entity.Credit;
import Atb.Banque.MS_Credit.entity.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Credit findByCreditRequest(CreditRequest creditRequest);
}