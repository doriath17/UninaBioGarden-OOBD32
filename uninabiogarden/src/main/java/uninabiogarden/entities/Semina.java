package uninabiogarden.entities;

import java.time.LocalDate;

public class Semina extends Attivita {

  private Integer quantitaSementi;
  private Double profonditaSeminaCm;

  public Semina() {
    super();
  }

  public Semina(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public Semina(Semina source) {
    super(source);
    this.quantitaSementi = source.quantitaSementi;
    this.profonditaSeminaCm = source.profonditaSeminaCm;
  }

  @Override
  public void copyFrom(Attivita source) {
    super.copyFrom(source);
    if (source instanceof Semina semina) {
      this.quantitaSementi = semina.quantitaSementi;
      this.profonditaSeminaCm = semina.profonditaSeminaCm;
    }
  }

  @Override
  public String validate() {
    String validationError = super.validate();
    if (validationError != null) {
      return validationError;
    }
    if (quantitaSementi == null || quantitaSementi <= 0) {
      return "La quantità di sementi deve essere positiva ed è obbligatoria.";
    }
    if (profonditaSeminaCm == null || profonditaSeminaCm <= 0 || profonditaSeminaCm > 40) {
      return "La profondità di semina deve essere compresa tra 0 e 40 cm ed è obbligatoria.";
    }
    return null;
  }

  public Integer getQuantitaSementi() {
    return quantitaSementi;
  }

  public void setQuantitaSementi(Integer quantitaSementi) {
    this.quantitaSementi = quantitaSementi;
  }

  public Double getProfonditaSeminaCm() {
    return profonditaSeminaCm;
  }

  public void setProfonditaSeminaCm(Double profonditaSeminaCm) {
    this.profonditaSeminaCm = profonditaSeminaCm;
  }

}
