package Epicode.gestioneDipendenti.entities;

import Epicode.gestioneDipendenti.enums.StatoType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@Setter
@Getter

@NoArgsConstructor
@ToString
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private StatoType statoType;

    public Viaggio(String destinazione, LocalDate data) {
        this.destinazione = destinazione;
        this.data = data;
        this.statoType = StatoType.IN_PROGRAMMA;
    }
}
