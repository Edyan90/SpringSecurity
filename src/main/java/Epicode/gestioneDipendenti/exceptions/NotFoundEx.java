package Epicode.gestioneDipendenti.exceptions;

import java.util.UUID;

public class NotFoundEx extends RuntimeException {
    public NotFoundEx(UUID id) {
        super("L'entità con id " + id + " non è stata trovata!");
    }
}
