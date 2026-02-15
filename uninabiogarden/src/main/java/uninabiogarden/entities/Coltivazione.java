package uninabiogarden.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Coltivazione {
  public enum StatoSaluteColtivazione {
    OTTIMO, STABILE, SOFFERENTE, CRITICO, COMPROMESSO
  };

  public enum StatoColtivazione {
    ATTIVA, CONCLUSA
  };

  private Long id;

  private StatoSaluteColtivazione statoSalute;
  private StatoColtivazione stato;

  private LocalDate dataInizio;
  private LocalDate dataFine;

  private String noteTecniche;

  private Coltura coltura;
  private Progetto progetto;

  public Coltivazione() {
    statoSalute = StatoSaluteColtivazione.OTTIMO;
    stato = StatoColtivazione.ATTIVA;
    noteTecniche = "";
  }

  public Coltivazione(Coltura coltura, String noteTecniche) {
    super();
    this.coltura = coltura;
    this.noteTecniche = noteTecniche;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StatoSaluteColtivazione getStatoSalute() {
    return statoSalute;
  }

  public void setStatoSalute(StatoSaluteColtivazione statoSalute) {
    this.statoSalute = statoSalute;
  }

  public StatoColtivazione getStato() {
    return stato;
  }

  public void setStato(StatoColtivazione stato) {
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

  public Progetto getProgetto() {
    return progetto;
  }

  public void setProgetto(Progetto progetto) {
    this.progetto = progetto;
  }

}
