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

  String noteTecniche;

  Coltivatore coltivatore;

  public Attivita() {
    stato = Stato.PIANIFICATA;
  }

  public Attivita(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super();
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.noteTecniche = noteTecniche;
    this.coltivatore = coltivatore;
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

}
