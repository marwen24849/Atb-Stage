package Atb.Banque.MS_Credit.service;

import Atb.Banque.MS_Credit.client.MailClient;
import Atb.Banque.MS_Credit.client.UserClient;
import Atb.Banque.MS_Credit.dto.CarteDto;
import Atb.Banque.MS_Credit.dto.LoginCarte;
import Atb.Banque.MS_Credit.dto.UserDto;
import Atb.Banque.MS_Credit.entity.Carte;
import Atb.Banque.MS_Credit.repositoory.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserClient userClient;

    @Autowired
    private MailClient mailClient;

    public Carte createCarte(CarteDto model) {
        Carte carte = new Carte();
        carte.setMotDePasse(passwordEncoder.encode(model.motDePasse()));
        carte.setSolde(model.solde());
        carte.setNumero(model.numero());
        carte.setUserId(this.usernameToId(model.username()));
        Carte savedCarte = carteRepository.save(carte);
        UserDto user = this.getUserById(carte.getUserId());
        String to = user.getEmail();
        String subject = "Votre nouvelle carte a été créée";
        String body = "Cher " + user.getFirstName() + ",\n\n" +
                "Nous avons le plaisir de vous informer que votre nouvelle carte a été créée.\n\n" +
                "Voici les informations de votre carte :\n" +
                "Numéro de carte : " + model.numero() + "\n" +
                "Mot de passe : " + model.motDePasse() + "\n\n" +
                "Cordialement,\nVotre Banque";

        // Envoyer l'e-mail au client
        mailClient.sendMail(to, null, subject, body);
        return savedCarte;
    }

    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

    public Carte findCarteByNumero(String numero) {
        return carteRepository.findByNumero(numero);
    }

    public void rechargeCarte(String cardNumber, BigDecimal amount) {
        Carte carte = carteRepository.findByNumero(cardNumber);
        if (carte == null) {
            throw new RuntimeException("Numero Carte Faux");
        }
        carte.setSolde(carte.getSolde().add(amount));
        carte.ajouterOperation("Recharge of " + amount);
        carteRepository.save(carte);
    }


    public boolean verifierMotDePasse(Carte carte, String motDePasse) {
        return passwordEncoder.matches(motDePasse, carte.getMotDePasse());
    }

    public String usernameToId(String username){
        UserDto user = this.userClient.findUserByUserName(username);
        if(user == null)
            throw new RuntimeException("User Not Found");
        return user.getId();
    }

    public Boolean login(LoginCarte loginCarte){
        Carte carte = this.carteRepository.findByNumero(loginCarte.numero());
        if(carte == null)
            throw new RuntimeException("Carte Pas Trouver");
        if(this.verifierMotDePasse(carte, loginCarte.Password()))
            return true;
        return false;
    }

    public Carte getCarteByNumero(String numero){
        Carte carte = this.carteRepository.findByNumero(numero);
        if(carte == null)
            throw new RuntimeException("Carte Pas Trouver");
        return carte;
    }
    public UserDto getUserById(String id){
        return this.userClient.findUser(id);
    }
}
