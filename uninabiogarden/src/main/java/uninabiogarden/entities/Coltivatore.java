package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.dto.UtenteDto;

public class Coltivatore extends Utente {

  private List<Riceve> notificheRicevute = new ArrayList<>();
  private List<Progetto> progetti = new ArrayList<>(); // i progetti di cui è coltivatore

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

  public List<Progetto> getProgetti() {
    return progetti;
  }

  public void setProgetti(List<Progetto> progetti) {
    this.progetti = progetti;
  }

  public List<Riceve> getNotificheRicevute() {
    return notificheRicevute;
  }

  public void setNotificheRicevute(List<Riceve> notificheRicevute) {
    this.notificheRicevute = notificheRicevute;
  }

}
