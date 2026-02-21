package uninabiogarden.entities;

import java.time.LocalDate;

public class Trattamento extends Attivita {

  private String nomeProdotto;
  private Integer tempoCarenza;

  public Trattamento() {
    super();
  }

  public Trattamento(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
  }

  public Trattamento(Trattamento source) {
    copyFrom(source);
  }

  @Override
  public void copyFrom(Attivita source) {
    super.copyFrom(source);
    if (source instanceof Trattamento tratt) {
      this.nomeProdotto = tratt.nomeProdotto;
      this.tempoCarenza = tratt.tempoCarenza;
    }
  }

  @Override
  public String validate() {
    String validationError = super.validate();
    if (validationError != null) {
      return validationError;
    }
    if (nomeProdotto == null || nomeProdotto.isBlank()) {
      return "Il nome del prodotto è obbligatorio per i trattamenti.";
    }
    return null;
  }

  public String getNomeProdotto() {
    return nomeProdotto;
  }

  public void setNomeProdotto(String nomeProdotto) {
    this.nomeProdotto = nomeProdotto;
  }

  public Integer getTempoCarenza() {
    return tempoCarenza;
  }

  public void setTempoCarenza(Integer tempoCarenza) {
    this.tempoCarenza = tempoCarenza;
  }

}
