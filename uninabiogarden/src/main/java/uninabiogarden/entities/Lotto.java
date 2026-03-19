package uninabiogarden.entities;

import uninabiogarden.dto.LottoDto;

public class Lotto {

  public enum TipologiaTerreno {
    ARGILLOSO, SABBIOSO, PIETROSO, MEDIO_IMPASTO
  }

  private Long id;
  private String codiceLotto;
  private TipologiaTerreno tipologiaTerreno;
  private Double estensioneMq;

  // tutti i lotti sono dell'utente loggato
  private Proprietario proprietario;
  private Orto orto;

  public Lotto() {
  }

  public Lotto(String codiceLotto, Double estensioneMq, TipologiaTerreno tipologiaTerreno, Proprietario proprietario,
      Orto orto) {
    this.codiceLotto = codiceLotto;
    this.estensioneMq = estensioneMq;
    this.tipologiaTerreno = tipologiaTerreno;
    this.proprietario = proprietario;
    this.orto = orto;
  }

  public Lotto(LottoDto dto) {
    this.codiceLotto = dto.codiceLotto;
    this.tipologiaTerreno = dto.tipologiaTerreno;
    this.estensioneMq = dto.estensioneMq;
  }

  @Override
  public String toString() {
    return "Lotto [id=" + id + ", codiceLotto=" + codiceLotto + ", tipologiaTerreno=" + tipologiaTerreno
        + ", estensioneMq=" + estensioneMq + ", proprietario=" + proprietario.getUsername() + " "
        + ", orto=" + orto.getNomeOrto() + "]";
  }

  public String getNomeOrto() {
    return orto.getNomeOrto();
  }

  public String getIndirizzo() {
    return orto.getFullAddress();
  }

  public String getFullname() {
    return orto.getNomeOrto() + " - " + this.codiceLotto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodiceLotto() {
    return codiceLotto;
  }

  public void setCodiceLotto(String codiceLotto) {
    this.codiceLotto = codiceLotto;
  }

  public Double getEstensioneMq() {
    return estensioneMq;
  }

  public void setEstensioneMq(Double estensioneMq) {
    this.estensioneMq = estensioneMq;
  }

  public Proprietario getProprietario() {
    return proprietario;
  }

  public void setProprietario(Proprietario proprietario) {
    this.proprietario = proprietario;
  }

  public Orto getOrto() {
    return orto;
  }

  public void setOrto(Orto orto) {
    this.orto = orto;
  }

  public TipologiaTerreno getTipologiaTerreno() {
    return tipologiaTerreno;
  }

  public void setTipologiaTerreno(TipologiaTerreno tipologiaTerreno) {
    this.tipologiaTerreno = tipologiaTerreno;
  }
}
