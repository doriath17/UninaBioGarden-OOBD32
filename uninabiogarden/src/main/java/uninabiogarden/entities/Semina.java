package uninabiogarden.entities;

import java.time.LocalDate;

public class Semina extends Attivita {

  private int quantitaSementi;
  private Double profonditaSeminaCm;

  public Semina() {
    super();
  }

  public Semina(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public int getQuantitaSementi() {
    return quantitaSementi;
  }

  public void setQuantitaSementi(int quantitaSementi) {
    this.quantitaSementi = quantitaSementi;
  }

  public Double getProfonditaSeminaCm() {
    return profonditaSeminaCm;
  }

  public void setProfonditaSeminaCm(Double profonditaSeminaCm) {
    this.profonditaSeminaCm = profonditaSeminaCm;
  }

}
