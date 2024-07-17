package Atb.Banque.MS_Credit.controller;

import Atb.Banque.MS_Credit.entity.Payment;
import Atb.Banque.MS_Credit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam  Long creditId,
                                                 @RequestParam  BigDecimal amount,
                                                 @RequestParam  String cardNumber,
                                                 @RequestParam  String cardPassword) {
        Payment createdPayment = paymentService.createPayment(creditId, amount, cardNumber, cardPassword);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,String>> getUsername(@PathVariable String id){
        return ResponseEntity.ok(Map.of("username", this.paymentService.getUserNameDromId(id)));
    }
}
