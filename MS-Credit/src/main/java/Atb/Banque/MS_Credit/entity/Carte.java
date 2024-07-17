package Atb.Banque.MS_Credit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Carte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @Column(nullable = false)
    private String motDePasse;
    private String userId;
    private BigDecimal solde;

    @ElementCollection
    private List<Operation> historique = new ArrayList<>();

    public void ajouterOperation(String description) {
        this.historique.add(new Operation(description, LocalDateTime.now()));
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Operation {
        private String description;
        private LocalDateTime dateOperation;
    }
}