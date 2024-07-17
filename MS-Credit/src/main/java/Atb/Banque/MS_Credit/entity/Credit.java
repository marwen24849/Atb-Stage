package Atb.Banque.MS_Credit.entity;

import Atb.Banque.MS_Credit.enums.Status;
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
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credit_request_id")
    private CreditRequest creditRequest;
    private BigDecimal Ammount;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmountToRepay;
    private BigDecimal remainingBalance;
    @Enumerated(EnumType.STRING)
    private Status status;


}
