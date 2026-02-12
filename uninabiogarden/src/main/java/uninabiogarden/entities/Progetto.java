package uninabiogarden.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Progetto {

  public enum Stato {
    PIANIFICATO, ATTIVO, CONCLUSO
  }

  private Long id;
  private String nomeProgetto;
  private String descrizione;
  private Stato stato; // "In preparazione", "In corso", "Completato"
  private LocalDateTime dataCreazione;
  private LocalDateTime dataInizio;
  private LocalDateTime dataFine;

  private Proprietario proprietario;
  private Lotto lotto;
  private List<Coltivazione> coltivazioni = new ArrayList<>();
  private List<Coltivatore> coltivatori = new ArrayList<>();

  public Progetto() {
  }

  public Progetto(String nomeProgetto, String descrizione, Proprietario proprietario, Lotto lotto) {
    this.nomeProgetto = nomeProgetto;
    this.descrizione = descrizione;
    this.proprietario = proprietario;
    this.lotto = lotto;
  }

  public String validate() {
    if (this.nomeProgetto == null || this.nomeProgetto.isEmpty()) {
      return "Nome progetto mancante";
    }
    if (this.lotto == null) {
      return "Lotto per il progetto non selezionato";
    }

    if (this.coltivazioni == null || this.coltivazioni.isEmpty()) {
      return "Almeno una coltivazione deve essere aggiunta al progetto";
    }

    if (this.coltivatori == null || this.coltivatori.isEmpty()) {
      return "Almeno un coltivatore deve essere assegnato al progetto";
    }

    return null; // Dati validi
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeProgetto() {
    return nomeProgetto;
  }

  public void setNomeProgetto(String nomeProgetto) {
    this.nomeProgetto = nomeProgetto;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Stato getStato() {
    return stato;
  }

  public void setStato(Stato stato) {
    this.stato = stato;
  }

  public LocalDateTime getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(LocalDateTime dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public LocalDateTime getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(LocalDateTime dataInizio) {
    this.dataInizio = dataInizio;
  }

  public LocalDateTime getDataFine() {
    return dataFine;
  }

  public void setDataFine(LocalDateTime dataFine) {
    this.dataFine = dataFine;
  }

  public Proprietario getProprietario() {
    return proprietario;
  }

  public void setProprietario(Proprietario proprietario) {
    this.proprietario = proprietario;
  }

  public Lotto getLotto() {
    return lotto;
  }

  public void setLotto(Lotto lotto) {
    this.lotto = lotto;
  }

  public List<Coltivazione> getColtivazioni() {
    return coltivazioni;
  }

  public void setColtivazioni(List<Coltivazione> coltivazioni) {
    this.coltivazioni = coltivazioni;
  }

  public List<Coltivatore> getColtivatori() {
    return coltivatori;
  }

  public void setColtivatori(List<Coltivatore> coltivatori) {
    this.coltivatori = coltivatori;
  }

}
