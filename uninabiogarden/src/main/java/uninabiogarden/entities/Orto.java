package uninabiogarden.entities;

import uninabiogarden.dto.OrtoDto;

public class Orto {

  private Long id;
  private String nomeOrto;
  private String citta;
  private String cap;
  private String civico;
  private String via;

  private Proprietario proprietario;

  public Orto() {
  }

  public Orto(String nomeOrto, String citta, String cap, String civico, String via, Proprietario proprietario) {
    this.nomeOrto = nomeOrto;
    this.citta = citta;
    this.cap = cap;
    this.civico = civico;
    this.via = via;
    this.proprietario = proprietario;
  }

  public Orto(OrtoDto ortoDto) {
    this.nomeOrto = ortoDto.nomeOrto;
    this.citta = ortoDto.citta;
    this.cap = ortoDto.cap;
    this.civico = ortoDto.civico;
    this.via = ortoDto.via;
    if (ortoDto.proprietario != null) {
      this.proprietario = new Proprietario(ortoDto.proprietario);
    }
  }

  public String getFullAddress() {
    return via + " " + civico + ", " + cap + " " + citta;
  }

  public String getProprietarioFullName() {
    return proprietario.getNome() + " " + proprietario.getCognome();
  }

  public String toString() {
    return nomeOrto + " - " + getFullAddress();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeOrto() {
    return nomeOrto;
  }

  public void setNomeOrto(String nomeOrto) {
    this.nomeOrto = nomeOrto;
  }

  public String getCitta() {
    return citta;
  }

  public void setCitta(String citta) {
    this.citta = citta;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getCivico() {
    return civico;
  }

  public void setCivico(String civico) {
    this.civico = civico;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public Proprietario getProprietario() {
    return proprietario;
  }

  public void setProprietario(Proprietario proprietario) {
    this.proprietario = proprietario;
  }

}
