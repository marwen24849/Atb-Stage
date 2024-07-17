package Atb.Banque.MS_Credit.repositoory;


import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.enums.CeditRequestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {


}