package Atb.Banque.MS_Credit.dto;

import java.math.BigDecimal;

public record RechargeBody(String numero, BigDecimal montant) {
}
