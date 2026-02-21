package uninabiogarden.entities;

import java.time.LocalDate;

public class Concimazione extends Attivita {

  public enum TipoConcime {
    ORGANICO,
    MINERALE,
    COMPOST
  }

  private TipoConcime tipoConcime;
  private Double quantitaKg;

  public Concimazione() {
    super();
  }

  public Concimazione(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public Concimazione(Concimazione source) {
    super(source);
    this.tipoConcime = source.tipoConcime;
    this.quantitaKg = source.quantitaKg;
  }

  @Override
  public void copyFrom(Attivita source) {
    super.copyFrom(source);
    if (source instanceof Concimazione conc) {
      this.tipoConcime = conc.tipoConcime;
      this.quantitaKg = conc.quantitaKg;
    }
  }

  @Override
  public String validate() {
    String validationError = super.validate();
    if (validationError != null) {
      return validationError;
    }
    if (tipoConcime == null) {
      return "Il tipo di concime è obbligatorio per le concimazioni.";
    }
    if (quantitaKg == null || quantitaKg <= 0) {
      return "La quantità di concime deve essere positiva ed è obbligatoria.";
    }
    return null;
  }

  public TipoConcime getTipoConcime() {
    return tipoConcime;
  }

  public void setTipoConcime(TipoConcime tipoConcime) {
    this.tipoConcime = tipoConcime;
  }

  public Double getQuantitaKg() {
    return quantitaKg;
  }

  public void setQuantitaKg(Double quantitaKg) {
    this.quantitaKg = quantitaKg;
  }

}
