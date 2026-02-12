package uninabiogarden.entities;

public class Coltura {

  private Long id;
  private String nomeComune;
  private Integer tempoMaturazione;
  private String caratteristiche;

  public Coltura() {
  }

  public Coltura(String nomeComune, Integer tempoMaturazione, String caratteristiche) {
    this.nomeComune = nomeComune;
    this.tempoMaturazione = tempoMaturazione;
    this.caratteristiche = caratteristiche;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeComune() {
    return nomeComune;
  }

  public void setNomeComune(String nomeComune) {
    this.nomeComune = nomeComune;
  }

  public Integer getTempoMaturazione() {
    return tempoMaturazione;
  }

  public void setTempoMaturazione(Integer tempoMaturazione) {
    this.tempoMaturazione = tempoMaturazione;
  }

  public String getCaratteristiche() {
    return caratteristiche;
  }

  public void setCaratteristiche(String caratteristiche) {
    this.caratteristiche = caratteristiche;
  }

}
