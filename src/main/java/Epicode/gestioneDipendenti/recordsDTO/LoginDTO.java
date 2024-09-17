package Epicode.gestioneDipendenti.recordsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotEmpty(message = "manca username")
        @Size(min = 3, max = 10, message = "l'username devono contenere un minimo di 2 ad un massimo di 10 caratteri")
        String username,
        @NotEmpty(message = "manca email")
        @Size(min = 10, max = 20, message = "l'email devono contenere un minimo di 10 ad un massimo di 20 caratteri")
        String email,
        @NotEmpty(message = "manca la password")
        @Size(min = 10, max = 20, message = "la password deve contenere un minimo di 10 ad un massimo di 20 caratteri alfanumerici")
        String password
) {
}


