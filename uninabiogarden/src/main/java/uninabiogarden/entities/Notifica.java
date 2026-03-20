package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Notifica {

    public enum Urgenza {
        BASSA, MEDIA, ALTA, CRITICA;
    }

    
    private Long id;

    private LocalDate dataInvio;

    private String nome;
    private String descrizione;
    private Urgenza urgenza;

    private Proprietario mittente;
    private Progetto progetto;

    private ArrayList<Coltivatore> destinatari = new ArrayList<>();

    public Notifica(Long id, LocalDate dataInvio, String nome, String descrizione, Urgenza urgenza, Proprietario mittente, Progetto progetto) {
        this.id = id;
        this.dataInvio = LocalDate.now();
        this.nome = nome;
        this.descrizione = descrizione;
        this.urgenza = urgenza;
        this.mittente = mittente;
        this.progetto = progetto;
        
    }

    public Notifica() {
        this.dataInvio = LocalDate.now();
    }

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
    
    public ArrayList<Coltivatore> getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(ArrayList<Coltivatore> destinatari) {
        this.destinatari = destinatari;
    }

    @Override
    public String toString() {
        return "Notifica{" +
                "id=" + id +
                ", dataInvio=" + dataInvio +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", urgenza=" + urgenza +
                ", mittente=" + mittente.getNome() +
                ", progetto=" + progetto.getNomeProgetto() +
                ", destinatari=" + destinatari.stream().map(Coltivatore::getNome).toList() +
                '}';
                
            }

}

