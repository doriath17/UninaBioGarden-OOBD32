package uninabiogarden.entities;

import java.time.LocalDate;

import uninabiogarden.dto.UtenteDto;

public class Proprietario extends Utente {

  public Proprietario(String username, String password, String email, String codiceFiscale, String nome,
      String cognome, LocalDate bDay, String gender, String bio) {
    super(username, password, email, codiceFiscale, nome, cognome, bDay, gender, bio);
  }

  public Proprietario(UtenteDto utenteDto) {
    super(utenteDto.username, utenteDto.password, utenteDto.email, utenteDto.codiceFiscale,
        utenteDto.nome, utenteDto.cognome, utenteDto.bDay, utenteDto.gender, utenteDto.bio);
  }
}