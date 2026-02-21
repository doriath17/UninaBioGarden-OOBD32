package uninabiogarden.entities;

import java.time.LocalDate;

public class Raccolta extends Attivita {

  private Double quantitaPrevistaKg;
  private Double quantitaEffettivaKg;

  public Raccolta() {
    super();
  }

  public Raccolta(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public Raccolta(Raccolta source) {
    super(source);
    this.quantitaPrevistaKg = source.quantitaPrevistaKg;
    this.quantitaEffettivaKg = source.quantitaEffettivaKg;
  }

  @Override
  public void copyFrom(Attivita source) {
    super.copyFrom(source);
    if (source instanceof Raccolta racc) {
      this.quantitaPrevistaKg = racc.quantitaPrevistaKg;
      this.quantitaEffettivaKg = racc.quantitaEffettivaKg;
    }
  }

  @Override
  public String validate() {
    String validationError = super.validate();
    if (validationError != null) {
      return validationError;
    }
    if (quantitaPrevistaKg != null && quantitaPrevistaKg <= 0) {
      return "La quantità prevista deve essere positiva se specificata.";
    }
    if (quantitaEffettivaKg != null && quantitaEffettivaKg < 0) {
      return "La quantità effettiva deve essere positiva o zero se specificata.";
    }
    return null;
  }

  public Double getQuantitaPrevistaKg() {
    return quantitaPrevistaKg;
  }

  public void setQuantitaPrevistaKg(Double quantitaPrevistaKg) {
    this.quantitaPrevistaKg = quantitaPrevistaKg;
  }

  public Double getQuantitaEffettivaKg() {
    return quantitaEffettivaKg;
  }

  public void setQuantitaEffettivaKg(Double quantitaEffettivaKg) {
    this.quantitaEffettivaKg = quantitaEffettivaKg;
  }

}
