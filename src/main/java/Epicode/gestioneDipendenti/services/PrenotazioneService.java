package Epicode.gestioneDipendenti.services;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.entities.Prenotazione;
import Epicode.gestioneDipendenti.entities.Viaggio;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.exceptions.NotFoundEx;
import Epicode.gestioneDipendenti.recordsDTO.PrenotazioneDTO;
import Epicode.gestioneDipendenti.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    ViaggioService viaggioService;
    @Autowired
    DipendenteService dipendenteService;

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findByID(UUID prenotazioneID) {
        return this.prenotazioneRepository.findById(prenotazioneID).orElseThrow(() -> new NotFoundEx(prenotazioneID));
    }

    public Prenotazione save(PrenotazioneDTO prenotazioneDTO) {
        Viaggio viaggio = viaggioService.findByID(UUID.fromString(prenotazioneDTO.viaggioID()));
        Dipendente dipendente = dipendenteService.findByID(UUID.fromString(prenotazioneDTO.dipendenteID()));
        if (!this.controlloDataPrenotazioni(dipendente.getId(), viaggio.getData())) {
            throw new BadRequestEx("Non puoi prenotare due viaggi lo stesso giorno o lo stesso viaggio due volte!");
        }
        Prenotazione prenotazione = new Prenotazione(viaggio, dipendente, prenotazioneDTO.note());
        try {
            return this.prenotazioneRepository.save(prenotazione);
        } catch (Exception e) {
            throw new BadRequestEx("Errore nel salvataggio della prenotazione. Verifica i dati e riprova.");
        }
    }

    public Prenotazione update(UUID prenotazioneID, PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione = this.findByID(prenotazioneID);
        Viaggio viaggio = viaggioService.findByID(UUID.fromString(prenotazioneDTO.viaggioID()));
        Dipendente dipendente = dipendenteService.findByID(UUID.fromString(prenotazioneDTO.dipendenteID()));
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setNote(prenotazioneDTO.note());
        return this.prenotazioneRepository.save(prenotazione);
    }

    public void findAndDelete(UUID prenotazioneID) {
        Prenotazione prenotazione = this.findByID(prenotazioneID);
        this.prenotazioneRepository.delete(prenotazione);
    }

    public boolean controlloDataPrenotazioni(UUID dipendenteID, LocalDate requestDate) {
        Dipendente dipendente = dipendenteService.findByID(dipendenteID);

        List<LocalDate> datePrenotazioni = prenotazioneRepository.findDatesByDipendenteID(dipendenteID);
        for (LocalDate data : datePrenotazioni) {
            if (data.equals(requestDate)) {
                return false;
            }
        }
        if (this.prenotazioneRepository.existsByDipendenteAndDataDiRichiesta(dipendente, requestDate)) {
            return false;
        }
        return true;
    }
}
