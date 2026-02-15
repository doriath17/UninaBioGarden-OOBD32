package uninabiogarden.entities;

import java.time.LocalDate;

public class Raccolta extends Attivita {

  private double quantitaPrevistaKg;
  private Double quantitaEffettivaKg;

  public Raccolta() {
    super();
  }

  public Raccolta(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public double getQuantitaPrevistaKg() {
    return quantitaPrevistaKg;
  }

  public void setQuantitaPrevistaKg(double quantitaPrevistaKg) {
    this.quantitaPrevistaKg = quantitaPrevistaKg;
  }

  public Double getQuantitaEffettivaKg() {
    return quantitaEffettivaKg;
  }

  public void setQuantitaEffettivaKg(Double quantitaEffettivaKg) {
    this.quantitaEffettivaKg = quantitaEffettivaKg;
  }

}
