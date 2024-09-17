package Epicode.gestioneDipendenti.services;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.exceptions.UnauthorizedEx;
import Epicode.gestioneDipendenti.recordsDTO.LoginDTO;
import Epicode.gestioneDipendenti.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;

    public String checkCredenzialiAndGeneraToken(LoginDTO loginDTO) {
        Dipendente dipendente = dipendenteService.findByEmail(loginDTO.email());
        if (dipendente.getUsername().equals(loginDTO.username())) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedEx("Credenziali errate!");
        }
    }
}
