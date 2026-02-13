package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.ArrayList;

import uninabiogarden.dto.UtenteDto;

public class Coltivatore extends Utente {

  private ArrayList<Riceve> notificheRicevute = new ArrayList<>();

  public Coltivatore() {
    super();
  }

  public Coltivatore(String username, String password, String email, String codiceFiscale, String nome,
      String cognome, String bDay, String gender, String bio) {
    super(username, password, email, codiceFiscale, nome, cognome, bDay, gender, bio);
  }

  public Coltivatore(UtenteDto utenteDto) {
    super(utenteDto.username, utenteDto.password, utenteDto.email, utenteDto.codiceFiscale,
        utenteDto.nome, utenteDto.cognome, utenteDto.bDay, utenteDto.gender, utenteDto.bio);
  }

}
