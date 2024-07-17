package Atb.Banque.MS_Credit.controller;

import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.service.CreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-requests")
public class CreditRequestController {

    @Autowired
    private CreditRequestService creditRequestService;

    @GetMapping
    public ResponseEntity<List<CreditRequest>> getAllCreditRequests() {
        List<CreditRequest> creditRequests = creditRequestService.getAllCreditRequests();
        return ResponseEntity.ok(creditRequests);
    }

    @GetMapping("/Status/{status}")
    public ResponseEntity<List<CreditRequest>> getAllCreditRequestsByStatus(@PathVariable String status) {
        List<CreditRequest> creditRequests = creditRequestService.getAllCreditRequestsByStatus(status);
        return ResponseEntity.ok(creditRequests);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<List<CreditRequest>> getAllCreditRequestsByCustomer_Id(@PathVariable String customer_id) {
        List<CreditRequest> creditRequests = creditRequestService.getAllCreditRequestsByCustomerId(customer_id);
        return ResponseEntity.ok(creditRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditRequest> getCreditRequestById(@PathVariable Long id) {
        CreditRequest creditRequest = creditRequestService.getCreditRequestById(id);
        return ResponseEntity.ok(creditRequest);
    }

    @PostMapping
    public ResponseEntity<CreditRequest> createCreditRequest(@RequestBody CreditRequest creditRequest) {
        CreditRequest createdCreditRequest = creditRequestService.createCreditRequest(creditRequest);
        return ResponseEntity.ok(createdCreditRequest);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<CreditRequest> approveCreditRequest(@PathVariable Long id) {
        CreditRequest approvedCreditRequest = creditRequestService.approveCreditRequest(id);
        return ResponseEntity.ok(approvedCreditRequest);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<CreditRequest> rejectCreditRequest(@PathVariable Long id, @RequestParam String adminComment) {
        CreditRequest rejectedCreditRequest = creditRequestService.rejectCreditRequest(id, adminComment);
        return ResponseEntity.ok(rejectedCreditRequest);
    }
}
