package uninabiogarden.entities;

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

  public String validate() {
    if (nomeOrto == null || nomeOrto.isEmpty()) {
      return "Nome orto mancante";
    }
    if (citta == null || citta.isEmpty()) {
      return "Città mancante";
    }
    if (cap == null || cap.isEmpty()) {
      return "CAP mancante";
    }
    if (!cap.matches("^[0-9]{5}$")) {
      return "CAP non valido o mancante";
    }
    if (via == null || via.isEmpty()) {
      return "Via mancante";
    }
    return null;
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
