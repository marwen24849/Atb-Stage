package Atb.Banque.MS_Credit.controller;

import Atb.Banque.MS_Credit.dto.CarteDto;
import Atb.Banque.MS_Credit.dto.LoginCarte;
import Atb.Banque.MS_Credit.dto.RechargeBody;
import Atb.Banque.MS_Credit.entity.Carte;
import Atb.Banque.MS_Credit.service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cartes")
public class CarteController {

    @Autowired
    private CarteService carteService;

    @GetMapping
    public ResponseEntity<List<Carte>> getAllCartes() {
        List<Carte> cartes = carteService.getAllCartes();
        return ResponseEntity.ok(cartes);
    }
    @GetMapping("/{numero}")
    public ResponseEntity<Carte> getAllCartes(@PathVariable String numero) {
        return ResponseEntity.ok(this.carteService.getCarteByNumero(numero));
    }

    @PostMapping
    public ResponseEntity<Carte> createCarte(@RequestBody CarteDto model) {
        Carte createdCarte = carteService.createCarte(model);
        return ResponseEntity.ok(createdCarte);
    }

    @PostMapping("/recharger")
    public ResponseEntity<String> rechargerCarte(@RequestBody RechargeBody body) {
        carteService.rechargeCarte(body.numero(), body.montant());
        return ResponseEntity.ok("carte rechargée avec succès");
    }

    @PostMapping("login")
    public ResponseEntity<Boolean> login(@RequestBody LoginCarte loginCarte){
        return ResponseEntity.ok(this.carteService.login(loginCarte));
    }


}
