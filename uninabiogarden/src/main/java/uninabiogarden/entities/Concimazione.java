package uninabiogarden.entities;

import java.time.LocalDate;

public class Concimazione extends Attivita {

  public enum TipoConcime {
    ORGANICO,
    MINERALE,
    COMPOST
  }

  private TipoConcime tipoConcime;
  private double quantitaKg;
  private String metodoApplicazione;

  public Concimazione() {
    super();
  }

  public Concimazione(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public TipoConcime getTipoConcime() {
    return tipoConcime;
  }

  public void setTipoConcime(TipoConcime tipoConcime) {
    this.tipoConcime = tipoConcime;
  }

  public double getQuantitaKg() {
    return quantitaKg;
  }

  public void setQuantitaKg(double quantitaKg) {
    this.quantitaKg = quantitaKg;
  }

  public String getMetodoApplicazione() {
    return metodoApplicazione;
  }

  public void setMetodoApplicazione(String metodoApplicazione) {
    this.metodoApplicazione = metodoApplicazione;
  }

}
