package uninabiogarden.controller;

import java.time.LocalDate;

import uninabiogarden.MainController;
import uninabiogarden.dao.DatabaseController;
import uninabiogarden.entities.Attivita;
import uninabiogarden.entities.Coltivazione;
import uninabiogarden.entities.Raccolta;
import uninabiogarden.exceptions.ValidationException;

public class AttivitaController {

  MainController mainController;
  DatabaseController databaseController;

  public AttivitaController(MainController mainController) {
    this.mainController = mainController;
    this.databaseController = mainController.getDatabaseController();
  }

  // ==============================================================================================
  // Validazione
  // ==============================================================================================

  private String validateUpdate(Attivita dto, Attivita original, Coltivazione coltivazione) {
    String validationError = dto.validate();
    if (validationError != null) {
      return validationError;
    }

    // check dei passaggi di stato: devono rispettare la logica PIANIFICATA ->
    // IN_CORSO -> COMPLETATA, non è possibile saltare uno stato o tornare indietro
    if (original.getStato() == Attivita.Stato.PIANIFICATA && dto.getStato() == Attivita.Stato.COMPLETATA) {
      return "Non è possibile passare direttamente da PIANIFICATA a COMPLETATA. Prima devi mettere l'attività IN_CORSO.";
    }
    if (original.getStato() == Attivita.Stato.IN_CORSO && dto.getStato() == Attivita.Stato.PIANIFICATA) {
      return "Non è possibile tornare indietro da IN_CORSO a PIANIFICATA.";
    }

    // se è una raccolta che sta per iniziare (stato IN_CORSO), tutte le altre
    // attivita della coltivazione devono essere in stato COMPLETATA, altrimenti non
    // è possibile iniziare la raccolta
    if (original instanceof Raccolta && original.getStato() == Attivita.Stato.PIANIFICATA
        && dto.getStato() == Attivita.Stato.IN_CORSO) {
      boolean allCompleted = coltivazione.getAttivita().stream()
          .filter(a -> !a.getId().equals(dto.getId())) // escludo l'attività che sto aggiornando
          .allMatch(a -> a.getStato() == Attivita.Stato.COMPLETATA);
      if (!allCompleted) {
        throw new ValidationException(
            "Non è possibile iniziare la raccolta finché tutte le altre attività non sono completate.");
      }
    }

    // se l'attivita ha un inizio programmato ed e in PIANIFICATA, non si puo
    // iniziare prima della data di inizio (non si puo passare a IN_CORSO o
    // COMPLETATA)
    if (original.getDataInizio() != null && original.getStato() == Attivita.Stato.PIANIFICATA
        && (dto.getStato() == Attivita.Stato.IN_CORSO || dto.getStato() == Attivita.Stato.COMPLETATA)) {
      if (dto.getDataInizio().isBefore(original.getDataInizio())) {
        return "Non è possibile iniziare l'attività prima della data di inizio programmata.";
      }
    }

    // questo deve valere anche in questo caso: attivita pianificata, l utente setta
    // la data di inizio e anche lo stato a IN_CORSO: non si puo iniziare (passare a
    // IN_CORSO) prima della data di inizio programmata. Se ad esempio si mette che
    // l attivita inizia il 03/06 allora non puo passare in corso prime del 03/06.
    boolean isTransitioningToInCorso = original.getStato() == Attivita.Stato.PIANIFICATA
        && dto.getStato() == Attivita.Stato.IN_CORSO;
    if (isTransitioningToInCorso && dto.getDataInizio() != null && dto.getDataInizio().isAfter(LocalDate.now())) {
      return "Non è possibile iniziare l'attività prima della data di inizio programmata.";
    }

    return null;

  }

  private String validateCreate(Attivita attivita, Coltivazione coltivazione) {
    String validationError = attivita.validate();
    if (validationError != null) {
      return validationError;
    }

    // se la coltivazione e in raccolta o conclusa, non si possono aggiungere nuove
    // attivita
    if (coltivazione.getStato() == Coltivazione.Stato.IN_RACCOLTA
        || coltivazione.getStato() == Coltivazione.Stato.CONCLUSA) {
      return "Non è possibile aggiungere nuove attività a una coltivazione "
          + coltivazione.getStato().name().toLowerCase().replace("_", " ") + ".";
    }

    return null;
  }

  // ==============================================================================================
  // Sezione: CRUD
  // ==============================================================================================

  public Attivita create(Attivita attivita, Coltivazione coltivazione) {
    String validationError = validateCreate(attivita, coltivazione);
    if (validationError != null) {
      throw new ValidationException(validationError);
    }

    var newAttivita = databaseController.getAttivitaDao().create(attivita, coltivazione.getId());
    coltivazione.getAttivita().add(newAttivita);
    System.out.println("Attività creata: " + newAttivita.getId());

    return newAttivita;
  }

  public void update(Attivita dto, Attivita original, Coltivazione coltivazione) {
    String validationError = validateUpdate(dto, original, coltivazione);
    if (validationError != null) {
      throw new ValidationException(validationError);
    }

    dto = databaseController.getAttivitaDao().update(dto);

    if (dto instanceof Raccolta) {
      // se lo stato della raccolta diventa IN_CORSO, la coltivazione associata va
      // nello stato IN_RACCOLTA (questo viene fatto in automatico dal database
      // tramite trigger)
      // se invece la raccolta termina (stato COMPLETATA), la coltivazione associata
      // va nello stato CONCLUSA (anche questo viene fatto in automatico dal database
      // tramite trigger)
      if (original.getStato() != Attivita.Stato.IN_CORSO && dto.getStato() == Attivita.Stato.IN_CORSO) {
        coltivazione.setStato(Coltivazione.Stato.IN_RACCOLTA);
      } else if (original.getStato() == Attivita.Stato.IN_CORSO && dto.getStato() == Attivita.Stato.COMPLETATA) {
        coltivazione.setStato(Coltivazione.Stato.CONCLUSA);
      }
    }
    original.copyFrom(dto);

    System.out.println("Attività aggiornata: " + dto.getId());

  }

}
