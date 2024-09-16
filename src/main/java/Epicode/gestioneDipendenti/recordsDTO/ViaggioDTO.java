package Epicode.gestioneDipendenti.recordsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotEmpty(message = "la destinazione è obbligatoria!")
        @Size(min = 3, max = 20, message = "la destinazione deve avere un minimo di 2 ad un massimo di 20 caratteri")
        String destinazione,
        @NotNull(message = "manca data dell'autore")
        LocalDate data,
        @NotNull(message = "manca lo stato del viaggio in caso di un aggiornamento di esso.Altrimenti nella creazione sarà settato di default come IN_PROGRAMMA")
        String statoType
) {
}
