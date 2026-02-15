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
