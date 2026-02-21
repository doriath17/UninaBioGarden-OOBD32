package uninabiogarden.entities;

import java.time.LocalDate;

public class Irrigazione extends Attivita {

  public enum MetodoIrrigazione {
    PIOGGIA,
    GOCCIA,
    MANUALE,
    SCORRIMENTO,
    NEBULIZZAZIONE
  }

  private MetodoIrrigazione metodo;
  private Double volumeAcquaL;

  public Irrigazione() {
    super();
  }

  public Irrigazione(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public Irrigazione(Irrigazione source) {
    copyFrom(source);
  }

  @Override
  public void copyFrom(Attivita source) {
    super.copyFrom(source);
    if (source instanceof Irrigazione irr) {
      this.metodo = irr.metodo;
      this.volumeAcquaL = irr.volumeAcquaL;
    }
  }

  @Override
  public String validate() {
    String validationError = super.validate();
    if (validationError != null) {
      return validationError;
    }
    if (metodo == null) {
      return "Il metodo di irrigazione è obbligatorio per le irrigazioni.";
    }
    if (volumeAcquaL == null || volumeAcquaL <= 0) {
      return "Il volume di acqua deve essere positivo ed è obbligatorio.";
    }
    return null;
  }

  public MetodoIrrigazione getMetodo() {
    return metodo;
  }

  public void setMetodo(MetodoIrrigazione metodo) {
    this.metodo = metodo;
  }

  public Double getVolumeAcquaL() {
    return volumeAcquaL;
  }

  public void setVolumeAcquaL(Double volumeAcquaL) {
    this.volumeAcquaL = volumeAcquaL;
  }

}
