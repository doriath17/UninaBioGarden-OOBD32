package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.Date;

public abstract class Utente {

  private Long id;

  private String username;
  private String password;

  private String email;
  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate bDay;
  private String gender;
  private String bio;

  public Utente() {
  }

  public Utente(String username, String password, String email, String codiceFiscale, String nome, String cognome,
      String bDay, String gender, String bio) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.codiceFiscale = codiceFiscale;
    this.nome = nome;
    this.cognome = cognome;

    // Parse bDay string to LocalDate with error handling
    try {
      if (bDay != null && !bDay.isEmpty()) {
        this.bDay = LocalDate.parse(bDay);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Formato data non valido per la data di nascita");
    }

    this.gender = gender;
    this.bio = bio;
  }

  public String validate() {
    if (getUsername() == null || getUsername().isEmpty()) {
      return "Username mancante";
    }
    if (getPassword() == null || getPassword().isEmpty()) {
      return "Password mancante";
    }
    if (getEmail() == null || getEmail().isEmpty()) {
      return "Email mancante";
    }
    if (!getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      return "Email non valida";
    }
    if (getCodiceFiscale() == null || getCodiceFiscale().isEmpty()) {
      return "Codice fiscale mancante";
    }
    if (!getCodiceFiscale().matches("[A-Za-z0-9]+")) {
      return "Codice fiscale non valido";
    }
    if (getNome() == null || getNome().isEmpty()) {
      return "Nome mancante";
    }
    if (getCognome() == null || getCognome().isEmpty()) {
      return "Cognome mancante";
    }
    if (getbDay() == null) {
      return "Data di nascita mancante";
    }
    if (getbDay().isAfter(LocalDate.now().minusYears(18))) {
      return "L'utente deve essere maggiorenne";
    }
    return null;
  }

  public String getFullName() {
    return nome + " " + cognome;
  }

  public Integer getAge() {
    if (bDay == null) {
      return 0;
    }
    LocalDate today = LocalDate.now();
    return today.getYear() - bDay.getYear() - (today.getDayOfYear() < bDay.getDayOfYear() ? 1 : 0);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public LocalDate getbDay() {
    return bDay;
  }

  public void setbDay(LocalDate bDay) {
    this.bDay = bDay;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}