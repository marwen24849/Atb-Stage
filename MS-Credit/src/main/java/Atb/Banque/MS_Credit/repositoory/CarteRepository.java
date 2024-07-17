package Atb.Banque.MS_Credit.repositoory;

import Atb.Banque.MS_Credit.entity.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteRepository extends JpaRepository<Carte, Long> {
    Carte findByNumero(String numero);
}
