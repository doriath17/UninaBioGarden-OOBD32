package uninabiogarden.entities;

import java.time.LocalDate;

public class Report {

    private String nomeColtura;
    private int totaleRaccolte;
    private LocalDate ultimaRaccolta;
    private double media;
    private double min;
    private double max;

    public Report(String nomeColtura, int totaleRaccolte, LocalDate ultimaRaccolta, double media, double min, double max) {
        this.nomeColtura = nomeColtura;
        this.totaleRaccolte = totaleRaccolte;
        this.ultimaRaccolta = ultimaRaccolta;
        this.media = media;
        this.min = min;
        this.max = max;
    }

    public Report() {}
    
    public String setNomeColtura(String nomeColtura) {
        this.nomeColtura = nomeColtura;
        return nomeColtura; 
    }

    public String getNomeColtura() {
        return nomeColtura;
    }

    public void setTotaleRaccolte(int totaleRaccolte) {
        this.totaleRaccolte = totaleRaccolte;
    }

    public int getTotaleRaccolte() {
        return totaleRaccolte;
    }

    public void setUltimaRaccolta(LocalDate ultimaRaccolta) {
        this.ultimaRaccolta = ultimaRaccolta;
    }

    public LocalDate getUltimaRaccolta() {
        return ultimaRaccolta;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getMedia() {
        return media;
    }

    public void setMin(double min) {
        this.min = min;
    }
    
    public double getMin() {
        return min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMax() {
        return max;
    }


}
