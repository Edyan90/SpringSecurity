package Epicode.gestioneDipendenti.recordsDTO;

import jakarta.validation.constraints.NotNull;

public record ViaggioStatusDTO(
        @NotNull(message = "manca lo stato del viaggio in caso di un aggiornamento di esso")
        String statoType) {
}
