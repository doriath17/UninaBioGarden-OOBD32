package uninabiogarden.entities;

public class Irrigazione extends Attivita {
    
    public enum MetodoIrrigazione { PIOGGIA, GOCCIA, MANUALE, SCORRIMENTO, NEBULIZZAZIONE }
    
    private MetodoIrrigazione metodo;
    private Double volumeAcqua;

    //TODO: Getters and Setters
}
