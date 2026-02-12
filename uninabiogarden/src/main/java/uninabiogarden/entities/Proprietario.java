package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.dto.UtenteDto;

public class Proprietario extends Utente {

  private List<Lotto> lotti;
  private List<Progetto> progetti;

  public Proprietario() {
    super();
  }

  public Proprietario(String username, String password, String email, String codiceFiscale, String nome,
      String cognome, String bDay, String gender, String bio) {
    super(username, password, email, codiceFiscale, nome, cognome, bDay, gender, bio);
  }

  public Proprietario(UtenteDto utenteDto) {
    super(utenteDto.username, utenteDto.password, utenteDto.email, utenteDto.codiceFiscale,
        utenteDto.nome, utenteDto.cognome, utenteDto.bDay, utenteDto.gender, utenteDto.bio);
  }

  public List<Lotto> getLotti() {
    return lotti;
  }

  public void setLotti(List<Lotto> lotti) {
    this.lotti = lotti;
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

}