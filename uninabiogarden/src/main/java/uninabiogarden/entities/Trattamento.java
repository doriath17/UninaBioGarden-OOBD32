package uninabiogarden.entities;

import java.time.LocalDate;

public class Trattamento extends Attivita {

  private String nomeProdotto;
  private Integer tempoCarenza;
  private String diluzioneDose;

  public Trattamento() {
    super();
  }

  public Trattamento(String nome, LocalDate dataInizio, String noteTecniche, Coltivazione coltivazione,
      Coltivatore coltivatore) {
    super(nome, dataInizio, noteTecniche, coltivazione, coltivatore);
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

  public String getDiluzioneDose() {
    return diluzioneDose;
  }

  public void setDiluzioneDose(String diluzioneDose) {
    this.diluzioneDose = diluzioneDose;
  }

}
