package Atb.Banque.MS_Credit.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record CarteDto(String numero,
                       String motDePasse,
                       String username,
                       BigDecimal solde) {
}
