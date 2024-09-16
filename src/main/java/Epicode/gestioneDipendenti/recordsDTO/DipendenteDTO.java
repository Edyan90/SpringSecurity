package Epicode.gestioneDipendenti.recordsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "l'username è obbligatorio!")
        @Size(min = 3, max = 20, message = "l'username deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String username,
        @NotEmpty(message = "il nome è obbligatorio!")
        @Size(min = 3, max = 20, message = "il nome deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String nome,
        @NotEmpty(message = "il cognome è obbligatorio!")
        @Size(min = 3, max = 20, message = "il cognome deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String cognome,
        @NotEmpty(message = "l'email è obbligatorio!")
        @Size(min = 3, max = 30, message = "l'email deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String email
) {
}
