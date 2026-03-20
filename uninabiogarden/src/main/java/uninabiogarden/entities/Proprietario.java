package uninabiogarden.entities;

import java.util.ArrayList;
import java.util.List;

public class Proprietario extends Utente {

  private List<Lotto> lotti;
  private List<Progetto> progetti;
  private List<Notifica> notificheInviate = new ArrayList<>();

  public Proprietario() {
    super();
  }

  public Proprietario(String username, String password, String email, String codiceFiscale, String nome,
      String cognome, String bDay, String gender, String bio) {
    super(username, password, email, codiceFiscale, nome, cognome, bDay, gender, bio);
  }

  public List<Lotto> getLotti() {
    return lotti;
  }

  public void setLotti(List<Lotto> lotti) {
    this.lotti = lotti;
  }

  public void addLotto(Lotto lotto) {
    if (this.lotti == null) {
      this.lotti = new ArrayList<>();
    }
    this.lotti.add(lotto);
  }

  public void removeLotto(Lotto lotto) {
    if (this.lotti != null) {
      this.lotti.remove(lotto);
    }
  }

  public List<Progetto> getProgetti() {
    return progetti;
  }

  public void setProgetti(List<Progetto> progetti) {
    this.progetti = progetti;
  }

  public void addProgetto(Progetto progetto) {
    if (this.progetti == null) {
      this.progetti = new ArrayList<>();
    }
    this.progetti.add(progetto);
  }

  public void removeProgetto(Progetto progetto) {
    if (this.progetti != null) {
      this.progetti.remove(progetto);
    }
  }

  public List<Notifica> getNotificheInviate() {
    return notificheInviate;
  }

  public void setNotificheInviate(List<Notifica> notifiche) {
    this.notificheInviate = notifiche;
  }

  public void addNotificaInviata(Notifica notifica) {
    this.notificheInviate.add(notifica);
  }

  public void removeNotificaInviata(Notifica notifica) {
    this.notificheInviate.remove(notifica);
  }

}