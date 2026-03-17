package uninabiogarden.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Coltivazione {

  public enum StatoSalute {
    OTTIMO, STABILE, SOFFERENTE, CRITICO, COMPROMESSO
  };

  public enum Stato {
    ATTIVA, IN_RACCOLTA, CONCLUSA
  };

  private Long id;

  private StatoSalute statoSalute;
  private Stato stato;

  private LocalDate dataInizio;
  private LocalDate dataFine;

  private String noteTecniche;

  private Coltura coltura;

  private List<Attivita> attivita = new ArrayList<>();

  public Coltivazione() {
    statoSalute = StatoSalute.OTTIMO;
    stato = Stato.ATTIVA;
    noteTecniche = "";
  }

  public Coltivazione(Coltura coltura, String noteTecniche) {
    super();
    this.coltura = coltura;
    this.noteTecniche = noteTecniche;
  }

  public String validateUpdate(Coltivazione updatedColtivazione) {

    return null;
  }

  public Raccolta getRaccolta() {
    return attivita.stream()
        .filter(a -> a instanceof Raccolta)
        .map(a -> (Raccolta) a)
        .findFirst()
        .orElse(null);
  }

  public LocalDate getDataFinePrevista() {
    return this.getDataInizio()
        .plusDays(this.getColtura().getTempoMaturazione());
  }

  @Override
  public String toString() {
    long attivitaCount = attivita != null ? attivita.size() : 0;
    return "Coltivazione{" +
        "id=" + id +
        ", statoSalute=" + statoSalute +
        ", stato=" + stato +
        ", dataInizio=" + dataInizio +
        ", dataFine=" + dataFine +
        ", noteTecniche='" + noteTecniche + '\'' +
        ", coltura=" + coltura +
        ", count attivita=" + attivitaCount +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StatoSalute getStatoSalute() {
    return statoSalute;
  }

  public void setStatoSalute(StatoSalute statoSalute) {
    this.statoSalute = statoSalute;
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

  public String getNoteTecniche() {
    return noteTecniche;
  }

  public void setNoteTecniche(String noteTecniche) {
    this.noteTecniche = noteTecniche;
  }

  public Coltura getColtura() {
    return coltura;
  }

  public void setColtura(Coltura coltura) {
    this.coltura = coltura;
  }

  public List<Attivita> getAttivita() {
    return attivita;
  }

  public void setAttivita(List<Attivita> attivita) {
    this.attivita = attivita;
  }

}
