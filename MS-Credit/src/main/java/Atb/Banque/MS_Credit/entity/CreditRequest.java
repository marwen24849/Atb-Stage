package Atb.Banque.MS_Credit.entity;

import Atb.Banque.MS_Credit.enums.CeditRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate requestDate;
    private BigDecimal requestedAmount;
    private BigDecimal interestRate;
    private Integer term;
    private String customer_Id;
    @Enumerated(EnumType.STRING)
    private CeditRequestStatus status;
    private String adminComment;






}
