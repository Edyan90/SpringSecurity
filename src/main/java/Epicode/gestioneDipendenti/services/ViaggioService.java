package Epicode.gestioneDipendenti.services;


import Epicode.gestioneDipendenti.entities.Viaggio;
import Epicode.gestioneDipendenti.enums.StatoType;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.exceptions.NotFoundEx;
import Epicode.gestioneDipendenti.recordsDTO.ViaggioDTO;
import Epicode.gestioneDipendenti.recordsDTO.ViaggioStatusDTO;
import Epicode.gestioneDipendenti.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ViaggioService {
    @Autowired
    ViaggioRepository viaggioRepository;

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findByID(UUID viaggioID) {
        return this.viaggioRepository.findById(viaggioID).orElseThrow(() -> new NotFoundEx(viaggioID));
    }

    public Viaggio save(ViaggioDTO viaggioDTO) {
        if (viaggioDTO.data().isBefore(LocalDate.now())) {
            throw new BadRequestEx("La data del viaggio non può essere nel passato.");
        }
        Viaggio viaggio = new Viaggio(
                viaggioDTO.destinazione(),
                viaggioDTO.data());
        return this.viaggioRepository.save(viaggio);
    }

    public Viaggio upDate(UUID viaggioID, ViaggioDTO viaggioDTO) {
        Viaggio viaggio = this.findByID(viaggioID);
        viaggio.setDestinazione(viaggioDTO.destinazione());
        viaggio.setData(viaggioDTO.data());
        switch (viaggioDTO.statoType().toLowerCase()) {
            case "in programma":
                viaggio.setStatoType(StatoType.IN_PROGRAMMA);
                break;
            case "completato":
                viaggio.setStatoType(StatoType.COMPLETATO);
                break;
            case "cancellato":
                viaggio.setStatoType(StatoType.CANCELLATO);
                break;
            default:
                throw new BadRequestEx("Stato non valido: " + viaggioDTO.statoType() +
                        ". I valori validi sono: in programma, completato, cancellato.");
        }
        return this.viaggioRepository.save(viaggio);
    }

    public void findByIDAndDelete(UUID viaggioID) {
        Viaggio viaggio = this.findByID(viaggioID);
        this.viaggioRepository.delete(viaggio);
    }

    public Viaggio upDateStatus(UUID viaggioID, ViaggioStatusDTO viaggioStatusDTO) {
        Viaggio viaggio = this.findByID(viaggioID);
        switch (viaggioStatusDTO.statoType().toLowerCase()) {
            case "in programma":
                viaggio.setStatoType(StatoType.IN_PROGRAMMA);
                break;
            case "completato":
                viaggio.setStatoType(StatoType.COMPLETATO);
                break;
            case "cancellato":
                viaggio.setStatoType(StatoType.CANCELLATO);
                break;
            default:
                throw new BadRequestEx("Stato non valido: " + viaggioStatusDTO.statoType() +
                        ". I valori validi sono: in programma, completato, cancellato.");
        }
        return this.viaggioRepository.save(viaggio);
    }
}
