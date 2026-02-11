package uninabiogarden.dto;

import java.time.LocalDate;

public class UtenteDto {
  public String username;
  public String password;
  public String email;
  public String codiceFiscale;
  public String nome;
  public String cognome;
  public LocalDate bDay;
  public String gender;
  public String bio;
  public String tipo; // "COLTIVATORE" o "PROPRIETARIO"
}
