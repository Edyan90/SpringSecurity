package Epicode.gestioneDipendenti.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Setter
@Getter

@NoArgsConstructor
@ToString
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    private LocalDate dataDiRichiesta;
    private String note;

    public Prenotazione(Viaggio viaggio, Dipendente dipendente, String note) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
    }
}
