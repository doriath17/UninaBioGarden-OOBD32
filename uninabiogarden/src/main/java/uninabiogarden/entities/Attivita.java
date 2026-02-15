package uninabiogarden.entities;
import java.sql.Timestamp;


public class Attivita {
    public enum StatoAttivita { PIANIFICATA, IN_CORSO, COMPLETATA, ANNULLATA }

    private Long id;

    private String titolo;
    private StatoAttivita stato;
    private String noteTecniche;

    private Timestamp dataPianificazione;
    private Timestamp dataScadenza;
    private Timestamp dataInizio;
    private Timestamp dataFine;
    
    private Coltivazione coltivazione;

    public Attivita() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public StatoAttivita getStato() {
        return stato;
    }

    public void setStato(StatoAttivita stato) {
        this.stato = stato;
    }

    public String getNoteTecniche() {
        return noteTecniche;
    }

    public void setNoteTecniche(String noteTecniche) {
        this.noteTecniche = noteTecniche;
    }

    public Timestamp getDataPianificazione() {
        return dataPianificazione;
    }

    public void setDataPianificazione(Timestamp dataPianificazione) {
        this.dataPianificazione = dataPianificazione;
    }

    public Timestamp getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Timestamp dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Timestamp getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Timestamp getDataFine() {
        return dataFine;
    }

    public void setDataFine(Timestamp dataFine) {
        this.dataFine = dataFine;
    }

    public Coltivazione getColtivazione() {
        return coltivazione;
    }

    public void setColtivazione(Coltivazione coltivazione) {
        this.coltivazione = coltivazione;
    }


}
