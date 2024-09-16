package Epicode.gestioneDipendenti.repositories;

import Epicode.gestioneDipendenti.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
