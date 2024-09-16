package Epicode.gestioneDipendenti.repositories;

import Epicode.gestioneDipendenti.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
    Optional<Dipendente> findByEmail(String email);

    boolean existsByEmail(String email);
}
