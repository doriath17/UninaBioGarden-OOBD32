package uninabiogarden.entities;

public class Coltivazione {
  public enum StatoSalute {
    OTTIMO, STABILE, SOFFERENTE, CRITICO, COMPROMESSO
  };

  public enum StatoColtivazione {
    PIANIFICATA, ATTIVA, CONCLUSA, FALLITA, ANNULLATA
  };

  private Long id;

}
