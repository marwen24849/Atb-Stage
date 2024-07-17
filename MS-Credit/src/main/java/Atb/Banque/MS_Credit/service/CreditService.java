package Atb.Banque.MS_Credit.service;

import Atb.Banque.MS_Credit.client.MailClient;
import Atb.Banque.MS_Credit.entity.Credit;
import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.enums.Status;
import Atb.Banque.MS_Credit.repositoory.CreditRepository;
import Atb.Banque.MS_Credit.repositoory.CreditRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@AllArgsConstructor
public class CreditService {


    private CreditRepository creditRepository;
    private CreditRequestRepository creditRequestRepository;
    public Credit createCreditFromRequest(CreditRequest creditRequest) {
        Credit credit = new Credit();
        credit.setCreditRequest(creditRequest);
        credit.setStartDate(LocalDate.now());
        credit.setEndDate(LocalDate.now().plusMonths(creditRequest.getTerm()));
        BigDecimal totalAmountToRepay = calculateTotalAmountToRepay(creditRequest.getRequestedAmount(), creditRequest.getInterestRate(), creditRequest.getTerm());
        credit.setTotalAmountToRepay(totalAmountToRepay);
        credit.setRemainingBalance(totalAmountToRepay);
        credit.setAmmount(creditRequest.getRequestedAmount());
        credit.setStatus(Status.Active);
        return creditRepository.save(credit);
    }

    public  BigDecimal calculateTotalAmountToRepay(BigDecimal principal, BigDecimal annualInterestRate, int termInMonths) {

        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal onePlusInterest = monthlyInterestRate.add(BigDecimal.ONE);
        BigDecimal totalAmountToRepay = principal.multiply(onePlusInterest.pow(termInMonths));
        totalAmountToRepay = totalAmountToRepay.setScale(2, RoundingMode.HALF_UP);
        return totalAmountToRepay;
    }
    public List<Credit> getAllCredits() {
        return this.creditRepository.findAll();
    }

    public Credit getCreditById(Long id) {
        return this.creditRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Credit Not Found"));
    }

    public Credit getCreditByCreditRequest(Long requestId) {

        CreditRequest creditRequest = creditRequestRepository.findById(requestId)
                .orElseThrow(()-> new RuntimeException("Pas Trouver")); // Supposons que vous avez un service pour gérer les requêtes de crédit
        return this.creditRepository.findByCreditRequest(creditRequest);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // Statistiques des demandes de crédit par statut
        List<CreditRequest> creditRequests = creditRequestRepository.findAll();
        Map<String, Long> creditRequestsStats = new HashMap<>();
        for (CreditRequest creditRequest : creditRequests) {
            creditRequestsStats.merge(creditRequest.getStatus().toString(), 1L, Long::sum);
        }
        statistics.put("creditRequestsStats", creditRequestsStats);

        // Montant total des crédits par mois
        List<Credit> credits = creditRepository.findAll();
        Map<String, BigDecimal> creditsStats = credits.stream()
                .collect(Collectors.groupingBy(credit -> credit.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                        Collectors.reducing(BigDecimal.ZERO, Credit::getAmmount, BigDecimal::add)));
        statistics.put("creditsStats", creditsStats);

        return statistics;
    }


}