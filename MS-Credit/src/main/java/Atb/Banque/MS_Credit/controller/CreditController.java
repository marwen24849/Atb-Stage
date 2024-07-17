package Atb.Banque.MS_Credit.controller;

import Atb.Banque.MS_Credit.entity.Credit;
import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.service.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
@AllArgsConstructor
public class CreditController {

    private CreditService creditService;

    @GetMapping
    public ResponseEntity<List<Credit>> getAllCredits() {
        List<Credit> credits = creditService.getAllCredits();
        return ResponseEntity.ok(credits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Long id) {
        Credit credit = creditService.getCreditById(id);
        return ResponseEntity.ok(credit);
    }

    @PostMapping
    public ResponseEntity<Credit> createCredit(@RequestBody CreditRequest credit) {
        Credit createdCredit = creditService.createCreditFromRequest(credit);
        return ResponseEntity.ok(createdCredit);
    }

    @GetMapping("/credit-by-request/{requestId}")
    public ResponseEntity<Credit> getCreditByCreditRequest(@PathVariable Long requestId) {
        Credit credit = creditService.getCreditByCreditRequest(requestId);
        return ResponseEntity.ok(credit);
    }


}
