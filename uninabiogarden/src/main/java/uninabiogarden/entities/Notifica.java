package uninabiogarden.entities;

import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Notifica {

    public enum Urgenza {
        BASSA, MEDIA, ALTA, CRITICA;
    }

    public enum Tipo {
        NOTIFICA_PROGETTO, NOTIFICA_ATTIVITA_IMMINENTE;
    }
    
    private Long id;

    private LocalDate dataInvio;

    private String nome;
    private String descrizione;
    private Urgenza urgenza;
    private Tipo tipo;
    
    private Integer giorniMancanti;

    private Proprietario mittente;
    private Progetto progetto;
    private Attivita attivita;

    private ArrayList<Coltivatore> destinatari = new ArrayList<>();

    public Notifica(Long id, LocalDate dataInvio, String nome, String descrizione, Urgenza urgenza, Tipo tipo, Integer giorniMancanti, Proprietario mittente, Progetto progetto, Attivita attivita) {
        this.id = id;
        this.dataInvio = dataInvio;
        this.nome = nome;
        this.descrizione = descrizione;
        this.urgenza = urgenza;
        this.tipo = tipo;
        this.giorniMancanti = giorniMancanti;
        this.mittente = mittente;
        this.progetto = progetto;
        this.attivita = attivita;
        
    }

    public Notifica() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(LocalDate dataInvio) {
        this.dataInvio = dataInvio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Urgenza getUrgenza() {
        return urgenza;
    }

    public void setUrgenza(Urgenza urgenza) {
        this.urgenza = urgenza;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getGiorniMancanti() {
        return giorniMancanti;
    }

    public void setGiorniMancanti(Integer giorniMancanti) {
        this.giorniMancanti = giorniMancanti;
    }

    public Proprietario getMittente() {
        return mittente;
    }

    public void setMittente(Proprietario mittente) {
        this.mittente = mittente;
    }

    public Progetto getProgetto() {
        return progetto;
    }

    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    public Attivita getAttivita() {
        return attivita;
    }

    public void setAttivita(Attivita attivita) {
        this.attivita = attivita;
    }
    

}

