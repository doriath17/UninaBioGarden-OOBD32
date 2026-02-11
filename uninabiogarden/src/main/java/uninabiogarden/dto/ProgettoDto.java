package uninabiogarden.dto;

import java.util.List;

public class ProgettoDto {
  public String nome;
  public String descrizione;
  public String stato;
  public String dataCreazione;
  public String dataInizio;
  public String dataFine;

  public Long lottoId;
  public List<Long> coltivatori;
}
