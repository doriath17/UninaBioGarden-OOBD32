package uninabiogarden.entities;

public class Concimazione extends Attivita {
    
    public enum TipoConcime { ORGANICO, MINERALE, COMPOST }
    
    private TipoConcime tipoConcime;
    private double quantitaKg;
    private String metodoApplicazione;

    //TODO: Getters and Setters
}