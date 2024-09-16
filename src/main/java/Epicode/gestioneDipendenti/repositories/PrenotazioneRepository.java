package Epicode.gestioneDipendenti.repositories;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    @Query("SELECT pr.viaggio.data FROM Prenotazione pr WHERE pr.dipendente.id=:dipendenteID ")
    List<LocalDate> findDatesByDipendenteID(UUID dipendenteID);

    boolean existsByDipendenteAndDataDiRichiesta(Dipendente dipendente, LocalDate data);
}
