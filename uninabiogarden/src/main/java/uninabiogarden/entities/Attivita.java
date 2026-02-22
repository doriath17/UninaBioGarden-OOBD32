package uninabiogarden.entities;

import java.time.LocalDate;

public abstract class Attivita {

  public enum Stato {
    PIANIFICATA,
    IN_CORSO,
    COMPLETATA
  }

  Long id;
  String nome;
  Stato stato;

  LocalDate dataPianificazione;
  LocalDate dataInizio;
  LocalDate dataFine;
  LocalDate dataScadenza;

  String noteTecniche;

  Coltivatore coltivatore;
  Coltivazione coltivazione;

  public Attivita() {
    dataPianificazione = LocalDate.now();
    stato = Stato.PIANIFICATA;
  }

  public Attivita(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super();
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.noteTecniche = noteTecniche;
    this.coltivatore = coltivatore;
    this.coltivazione = coltivazione;
  }

  public Attivita(Attivita source) {
    copyFrom(source);
  }

  public void copyFrom(Attivita source) {
    this.id = source.id;
    this.nome = source.nome;
    this.stato = source.stato;
    this.dataPianificazione = source.dataPianificazione;
    this.dataInizio = source.dataInizio;
    this.dataFine = source.dataFine;
    this.dataScadenza = source.dataScadenza;
    this.noteTecniche = source.noteTecniche;
    this.coltivatore = source.coltivatore;
    this.coltivazione = source.coltivazione;
  }

  // si assume che l'attivita abbia una data di pianificazione siccome questa e
  // settata dal database tramite trigger
  public String validate() {
    if (dataInizio != null && dataPianificazione != null
        && dataInizio.isBefore(dataPianificazione)) {
      return "La data di inizio non può essere precedente alla data di pianificazione.";
    }
    if (dataScadenza != null && dataInizio != null
        && dataScadenza.isBefore(dataInizio)) {
      return "La data di scadenza non può essere precedente alla data di inizio.";
    }
    if (dataScadenza != null && dataPianificazione != null
        && dataScadenza.isBefore(dataPianificazione)) {
      return "La data di scadenza non può essere precedente alla data di pianificazione.";
    }

    // check not null dei campi obbligatori
    if (nome == null || nome.isBlank()) {
      return "Il nome dell'attività è obbligatorio.";
    }
    if (noteTecniche == null || noteTecniche.isBlank()) {
      return "Le note tecniche sono obbligatorie.";
    }
    if (coltivatore == null) {
      return "Il coltivatore è obbligatorio.";
    }
    if (coltivazione == null) {
      return "La coltivazione è obbligatoria.";
    }

    return null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Stato getStato() {
    return stato;
  }

  public void setStato(Stato stato) {
    this.stato = stato;
  }

  public LocalDate getDataPianificazione() {
    return dataPianificazione;
  }

  public void setDataPianificazione(LocalDate dataPianificazione) {
    this.dataPianificazione = dataPianificazione;
  }

  public LocalDate getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(LocalDate dataInizio) {
    this.dataInizio = dataInizio;
  }

  public LocalDate getDataFine() {
    return dataFine;
  }

  public void setDataFine(LocalDate dataFine) {
    this.dataFine = dataFine;
  }

  public String getNoteTecniche() {
    return noteTecniche;
  }

  public void setNoteTecniche(String noteTecniche) {
    this.noteTecniche = noteTecniche;
  }

  public Coltivatore getColtivatore() {
    return coltivatore;
  }

  public void setColtivatore(Coltivatore coltivatore) {
    this.coltivatore = coltivatore;
  }

  public LocalDate getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(LocalDate dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public Coltivazione getColtivazione() {
    return coltivazione;
  }

  public void setColtivazione(Coltivazione coltivazione) {
    this.coltivazione = coltivazione;
  }

}
