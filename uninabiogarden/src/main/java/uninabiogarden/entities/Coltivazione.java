package uninabiogarden.entities;

import java.time.LocalDateTime;

public class Coltivazione {
  public enum StatoSaluteColtivazione {
    OTTIMO, STABILE, SOFFERENTE, CRITICO, COMPROMESSO
  };

  public enum StatoColtivazione {
    PIANIFICATA, ATTIVA, CONCLUSA
  };

  private Long id;

  private StatoSaluteColtivazione statoSalute;
  private StatoColtivazione stato;

  // le date sono impostate e gestite dal database
  private LocalDateTime dataCreazione;
  private LocalDateTime dataInizio;
  private LocalDateTime dataFine;

  private Integer quantitaPiante;
  private String noteTecniche;

  private Coltura coltura;
  // private Progetto progetto; --- IGNORE ---

  public Coltivazione() {
    statoSalute = StatoSaluteColtivazione.OTTIMO;
    stato = StatoColtivazione.PIANIFICATA;
  }

  public Coltivazione(Coltura coltura, Integer quantitaPiante, String noteTecniche) {
    super();
    this.coltura = coltura;
    this.quantitaPiante = quantitaPiante;
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

  public Integer getQuantitaPiante() {
    return quantitaPiante;
  }

  public void setQuantitaPiante(Integer quantitaPiante) {
    this.quantitaPiante = quantitaPiante;
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

}
