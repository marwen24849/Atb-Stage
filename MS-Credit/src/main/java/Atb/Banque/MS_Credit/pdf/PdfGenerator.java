package Atb.Banque.MS_Credit.pdf;

import Atb.Banque.MS_Credit.client.MailClient;
import Atb.Banque.MS_Credit.dto.UserDto;
import Atb.Banque.MS_Credit.entity.CreditRequest;
import Atb.Banque.MS_Credit.service.MultipartFileUtil;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Data
public class PdfGenerator {

    @Autowired
    private MailClient mailClient;

    public void generateCreditRequestPdf(CreditRequest creditRequest, UserDto user) throws IOException {
        // Utiliser le chemin absolu pour le répertoire dans le conteneur Docker
        String directory = "/app/pdf-requests";
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String dest = directory + "/credit_request_" + creditRequest.getId() + ".pdf";

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        // Font and styles
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Style titleStyle = new Style().setFont(font).setFontSize(18).setBold().setFontColor(ColorConstants.BLUE).setTextAlignment(TextAlignment.CENTER);
        Style labelStyle = new Style().setFont(font).setFontSize(12).setBold();
        Style valueStyle = new Style().setFont(font).setFontSize(12);

        // Title
        document.add(new Paragraph("Demande de Crédit").addStyle(titleStyle));

        // Table
        float[] columnWidths = {2, 5};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Add cells to the table
        table.addCell(new Cell().add(new Paragraph("ID de la Demande:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getId().toString()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Date de Demande:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getRequestDate().toString()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Montant Demandé:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getRequestedAmount().toString()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Taux d'Intérêt:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getInterestRate().toString()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Durée:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getTerm() + " mois").addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("ID du Client:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getCustomer_Id()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Nom du Client:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(user.getFirstName() + " " + user.getLastName()).addStyle(valueStyle)));
        table.addCell(new Cell().add(new Paragraph("Statut de la Demande:").addStyle(labelStyle)));
        table.addCell(new Cell().add(new Paragraph(creditRequest.getStatus().toString()).addStyle(valueStyle)));

        document.add(table);

        document.close();
    }

    public void envoyerDemandeDeCreditPdf(CreditRequest savedRequest, UserDto user) throws IOException {
        String directoryPath = "/app/pdf-requests";  // Chemin absolu dans le conteneur Docker
        File directory = new File(directoryPath);

        // Vérifiez si le répertoire existe, sinon créez-le
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + "/credit_request_" + savedRequest.getId() + ".pdf";
        File file = new File(filePath);

        if (file.exists() && !file.isDirectory()) {
            MultipartFile pdfFile = MultipartFileUtil.fileToMultipartFile(file);

            // Préparer les détails de l'e-mail
            String to = user.getEmail();
            String[] cc = {};
            String subject = "Détails de votre demande de crédit";
            String body = "Cher " + user.getFirstName() + ",\n\nVeuillez trouver ci-joint les détails de votre demande de crédit.\n\nCordialement,\nBanque";

            // Envoyer l'e-mail avec la pièce jointe PDF
            mailClient.sendMail(file, to, cc, subject, body);
        } else {
            // Gérer le cas où le fichier n'existe pas
            System.out.println("Fichier non trouvé : " + filePath);
        }
    }
}
