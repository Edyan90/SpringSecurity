package Epicode.gestioneDipendenti.services;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.exceptions.NotFoundEx;
import Epicode.gestioneDipendenti.recordsDTO.DipendenteDTO;
import Epicode.gestioneDipendenti.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendenteService {
    @Autowired
    DipendenteRepository dipendenteRepository;
    @Autowired
    Cloudinary cloudinary;

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findByID(UUID dipendenteID) {
        return this.dipendenteRepository.findById(dipendenteID).orElseThrow(() -> new NotFoundEx(dipendenteID));
    }

    public Dipendente save(DipendenteDTO dipendenteDTO) {
        this.dipendenteRepository.findByEmail(dipendenteDTO.email()).ifPresent(dipendente -> {
            throw new BadRequestEx("L'email " + dipendenteDTO.email() + " è già in uso!");
        });
        Dipendente dipendente = new Dipendente(
                dipendenteDTO.username(),
                dipendenteDTO.nome(),
                dipendenteDTO.cognome(),
                dipendenteDTO.email()
        );
        dipendente.setAvatar("https://ui-avatars.com/api/?name=" + dipendenteDTO.nome() + "+" + dipendenteDTO.cognome());
        return this.dipendenteRepository.save(dipendente);
    }

    public Dipendente update(UUID dipendereID, DipendenteDTO dipendenteDTO) {
        Dipendente found = this.findByID(dipendereID);
        this.dipendenteRepository.findByEmail(dipendenteDTO.email()).ifPresent(dipendente -> {
            if (!dipendente.getId().equals(dipendereID)) {
                throw new BadRequestEx("L'email " + dipendenteDTO.email() + " è già in uso!");
            }
        });
        found.setNome(dipendenteDTO.nome());
        found.setCognome(dipendenteDTO.cognome());
        found.setUsername(dipendenteDTO.username());
        found.setEmail(dipendenteDTO.email());
        return this.dipendenteRepository.save(found);
    }

    public void findAndDelete(UUID dipendenteID) {
        Dipendente dipendente = this.findByID(dipendenteID);
        this.dipendenteRepository.delete(dipendente);
    }

    public Dipendente avatarUpload(UUID dipendenteID, MultipartFile file) {
        try {
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            Dipendente dipendente = this.findByID(dipendenteID);
            dipendente.setAvatar(url);
            return this.dipendenteRepository.save(dipendente);
        } catch (IOException e) {
            throw new BadRequestEx("Errore nel caricamento del file. Verifica il formato e le dimensioni!");
        }
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundEx(email));
    }

}
