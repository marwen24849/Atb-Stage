package Atb.Banque.MS_Credit.controller;

import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.entity.Credit;
import Atb.Banque.MS_Credit.service.CreditRequestService;
import Atb.Banque.MS_Credit.service.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin-dashboard")
@AllArgsConstructor
public class DashAdminController {

    @Autowired
    private CreditRequestService creditRequestService;

    @Autowired
    private CreditService creditService;

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        // Récupérer les statistiques de votre service
        Map<String, Object> statistics = creditService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/credit-requests")
    public ResponseEntity<List<CreditRequest>> getAllCreditRequests() {
        List<CreditRequest> creditRequests = creditRequestService.getAllCreditRequests();
        return ResponseEntity.ok(creditRequests);
    }

    @GetMapping("/credits")
    public ResponseEntity<List<Credit>> getAllCredits() {
        List<Credit> credits = creditService.getAllCredits();
        return ResponseEntity.ok(credits);
    }
}
