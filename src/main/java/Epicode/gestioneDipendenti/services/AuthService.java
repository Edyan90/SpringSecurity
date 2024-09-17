package Epicode.gestioneDipendenti.services;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.exceptions.UnauthorizedEx;
import Epicode.gestioneDipendenti.recordsDTO.LoginDTO;
import Epicode.gestioneDipendenti.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(LoginDTO loginDTO) {
        Dipendente dipendente = dipendenteService.findByEmail(loginDTO.email());
        if (bcrypt.matches(loginDTO.password(), dipendente.getPassword())) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedEx("Credenziali errate!");
        }
    }
}
