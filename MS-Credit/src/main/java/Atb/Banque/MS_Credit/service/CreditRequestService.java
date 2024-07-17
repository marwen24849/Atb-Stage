package Atb.Banque.MS_Credit.service;
import Atb.Banque.MS_Credit.client.MailClient;
import Atb.Banque.MS_Credit.client.UserClient;
import Atb.Banque.MS_Credit.dto.UserDto;
import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.enums.CeditRequestStatus;
import Atb.Banque.MS_Credit.pdf.PdfGenerator;
import Atb.Banque.MS_Credit.repositoory.CreditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CreditRequestService {

    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Autowired
    private CreditService creditService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private MailClient mailClient;

    public List<CreditRequest> getAllCreditRequests() {
        return creditRequestRepository.findAll();
    }

    public List<CreditRequest> getAllCreditRequestsByStatus(String status) {
        List<CreditRequest> creditRequests = this.creditRequestRepository.findAll().stream()
                .filter(request -> request.getStatus().toString().equals(status))
                .collect(Collectors.toList());
        return creditRequests;
    }


    public CreditRequest getCreditRequestById(Long id) {
       return creditRequestRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Credit Non Trouver"));

    }

    public CreditRequest createCreditRequest(CreditRequest creditRequest) {
        UserDto user = this.userClient.findUser(creditRequest.getCustomer_Id());
        creditRequest.setStatus(CeditRequestStatus.Submitted);
        creditRequest.setRequestDate(LocalDate.now());
        creditRequest.setInterestRate(BigDecimal.valueOf(5));
        CreditRequest savedRequest = creditRequestRepository.save(creditRequest);
        try {
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generateCreditRequestPdf(savedRequest, user);
            this.envoyerDemandeDeCreditPdf(savedRequest, user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedRequest;
    }

    public CreditRequest approveCreditRequest(Long id) {
        CreditRequest creditRequest = creditRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditRequest not found"));
        creditRequest.setStatus(CeditRequestStatus.Approved);
        creditService.createCreditFromRequest(creditRequest);
        UserDto user = this.getUserById(creditRequest.getCustomer_Id());
        String to = user.getEmail();
        String subject = "Votre demande de crédit a été approuvée";
        String body = "Cher "+user.getFirstName()+",\n\nVotre demande de crédit a été approuvée.\n\nCordialement,\nVotre Banque";
        mailClient.sendMail(to, null, subject, body);
        return creditRequestRepository.save(creditRequest);
    }

    public CreditRequest rejectCreditRequest(Long id, String adminComment) {
        CreditRequest creditRequest = creditRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditRequest not found"));
        creditRequest.setStatus(CeditRequestStatus.Rejected);
        creditRequest.setAdminComment(adminComment);
        UserDto user = this.getUserById(creditRequest.getCustomer_Id());
        String to = user.getEmail();
        String subject = "Votre demande de crédit a été rejetée";
        String body = "Cher " + user.getFirstName() + ",\n\nVotre demande de crédit a été rejetée." +
                "\nCommentaire Admine: "+adminComment+
                "\n\nCordialement,\nVotre Banque";
        mailClient.sendMail(to, null, subject, body);
        return creditRequestRepository.save(creditRequest);
    }

    public BigDecimal calculateAnnualInterestRate(BigDecimal totalAmountToRepay, BigDecimal principal, int termInMonths) {
        BigDecimal monthlyInterestRate = BigDecimal.ZERO;
        BigDecimal onePlusMonthlyInterestRate = BigDecimal.ONE;
        BigDecimal totalAmountToRepayCalculated;
        for (int i = 0; i < 1000; i++) {
            monthlyInterestRate = monthlyInterestRate.add(BigDecimal.valueOf(0.0001)); // Increment by small value
            onePlusMonthlyInterestRate = monthlyInterestRate.add(BigDecimal.ONE);
            totalAmountToRepayCalculated = principal.multiply(onePlusMonthlyInterestRate.pow(termInMonths));
            if (totalAmountToRepayCalculated.compareTo(totalAmountToRepay) >= 0) {
                break;
            }
        }
        return monthlyInterestRate.multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(100));
    }

    public List<CreditRequest> getAllCreditRequestsByCustomerId(String customer_Id) {

        return creditRequestRepository.findAll().stream()
                .filter(request -> customer_Id.equals(request.getCustomer_Id()))
                .collect(Collectors.toList());
    }

    public void envoyerDemandeDeCreditPdf(CreditRequest savedRequest, UserDto user) throws IOException {
        String directoryPath = "/app/pdf-requests";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = directoryPath + "/credit_request_" + savedRequest.getId() + ".pdf";
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            MultipartFile pdfFile = MultipartFileUtil.fileToMultipartFile(file);
            String to = user.getEmail();
            String[] cc = {};
            String subject = "Détails de votre demande de crédit";
            String body = "Cher " + user.getFirstName() + ",\n\nVeuillez trouver ci-joint les détails de votre demande de crédit.\n\nCordialement,\nBanque";
            mailClient.sendMail(file, to, cc, subject, body);
        } else {
            System.out.println("Fichier non trouvé : " + filePath);
        }
    }

    public UserDto getUserById(String id){
        return this.userClient.findUser(id);
    }
}