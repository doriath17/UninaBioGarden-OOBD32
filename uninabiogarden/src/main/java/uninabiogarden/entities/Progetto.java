package uninabiogarden.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Progetto {

  public enum Stato {
    ATTIVO, CONCLUSO
  }

  private Long id;
  private String nomeProgetto;
  private String descrizione;
  private Stato stato;
  private LocalDate dataInizio;
  private LocalDate dataFine;

  private Proprietario proprietario;
  private Lotto lotto;
  private List<Coltivazione> coltivazioni = new ArrayList<>();
  private List<Coltivatore> coltivatori = new ArrayList<>();

  private List<Notifica> notifiche = new ArrayList<>();

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

  @Override
  public String toString() {
    long coltivazioniCount = coltivazioni != null ? coltivazioni.size() : 0;
    long coltivatoriCount = coltivatori != null ? coltivatori.size() : 0;
    return "Progetto{" +
        "id=" + id +
        ", nomeProgetto='" + nomeProgetto + '\'' +
        ", descrizione='" + descrizione + '\'' +
        ", stato=" + stato +
        ", dataInizio=" + dataInizio +
        ", dataFine=" + dataFine +
        ", proprietario=" + proprietario.getUsername() +
        ", lotto=" + lotto +
        ", count coltivazioni=" + coltivazioniCount +
        ", count coltivatori=" + coltivatoriCount +
        '}';
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
