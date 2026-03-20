package uninabiogarden.entities;

import java.util.ArrayList;
import java.util.List;

public class Coltivatore extends Utente {

  private List<Notifica> notificheRicevute = new ArrayList<>();
  private List<Progetto> progetti = new ArrayList<>(); // i progetti di cui è coltivatore

  public Coltivatore() {
    super();
  }

  public Coltivatore(String username, String password, String email, String codiceFiscale, String nome,
      String cognome, String bDay, String gender, String bio) {
    super(username, password, email, codiceFiscale, nome, cognome, bDay, gender, bio);
  }

  public List<Progetto> getProgetti() {
    return progetti;
  }

  public void setProgetti(List<Progetto> progetti) {
    this.progetti = progetti;
  }

  public List<Notifica> getNotificheRicevute() {
    return notificheRicevute;
  }

  public void setNotificheRicevute(List<Notifica> notificheRicevute) {
    this.notificheRicevute = notificheRicevute;
  }

}
