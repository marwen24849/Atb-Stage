package Atb.Banque.MS_Credit.service;

import Atb.Banque.MS_Credit.client.UserClient;
import Atb.Banque.MS_Credit.dto.UserDto;
import Atb.Banque.MS_Credit.entity.Carte;
import Atb.Banque.MS_Credit.entity.Credit;
import Atb.Banque.MS_Credit.entity.Payment;
import Atb.Banque.MS_Credit.enums.PaymentStatus;
import Atb.Banque.MS_Credit.enums.Status;
import Atb.Banque.MS_Credit.repositoory.CarteRepository;
import Atb.Banque.MS_Credit.repositoory.CreditRepository;
import Atb.Banque.MS_Credit.repositoory.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CarteService carteService;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private UserClient userClient;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment createPayment(Long creditId, BigDecimal amount, String cardNumber, String cardPassword) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));

        if (!credit.getStatus().toString().equals("Active")) {
            throw new RuntimeException("Credit is not active");
        }

        Carte carte = carteRepository.findByNumero(cardNumber);
        if (carte == null)
            throw new RuntimeException("Numero Carte Faux");

        if (!this.carteService.verifierMotDePasse(carte, cardPassword)) {
            throw new RuntimeException("Invalid card password");
        }

        if (carte.getSolde().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance on card");
        }
        carte.setSolde(carte.getSolde().subtract(amount));
        carte.ajouterOperation("Payment of " + amount + " to credit " + creditId);
        carteRepository.save(carte);

        Payment payment = new Payment();
        payment.setCredit(credit);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(amount);
        payment.setPaymentStatus(PaymentStatus.Paid);

        // Update the credit balance
        updateCreditBalance(creditId, amount);

        return paymentRepository.save(payment);
    }

    private void updateCreditBalance(Long creditId, BigDecimal amount) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found"));
        credit.setRemainingBalance(credit.getRemainingBalance().subtract(amount));
        creditRepository.save(credit);
    }

    public String getUserNameDromId(String id){
        UserDto user = this.userClient.findUser(id);
        if (user == null)
            throw new RuntimeException("User Not Found");
        return user.getUsername();

    }

}