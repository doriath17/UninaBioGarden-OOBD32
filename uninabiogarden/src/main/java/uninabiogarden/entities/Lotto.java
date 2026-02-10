package uninabiogarden.entities;

import uninabiogarden.dto.LottoDto;

public class Lotto {

  private Long id;
  private String codiceLotto;
  private Double estensioneMq;

  private Proprietario proprietario;
  private Orto orto;

  public Lotto() {
  }

  public Lotto(String codiceLotto, Double estensioneMq, Proprietario proprietario, Orto orto) {
    this.codiceLotto = codiceLotto;
    this.estensioneMq = estensioneMq;
    this.proprietario = proprietario;
    this.orto = orto;
  }

  public Lotto(LottoDto dto) {
    this.codiceLotto = dto.codiceLotto;
    this.estensioneMq = dto.estensioneMq;
  }

  public String getNomeOrto() {
    return orto.getNomeOrto();
  }

  public String getIndirizzo() {
    return orto.getFullAddress();
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

}
