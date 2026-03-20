package uninabiogarden.entities;

import java.time.LocalDate;
//import java.time.temporal.ChronoUnit.*;

public class NotificaAttivita extends Notifica {

    private Attivita attivita;
    private Integer giorniMancanti;

    public NotificaAttivita(Long id, LocalDate dataInvio, String nome, String descrizione, Urgenza urgenza, Proprietario mittente, Progetto progetto, Attivita attivita) {
        
        super(id, dataInvio, nome, descrizione, urgenza, mittente, progetto);

        this.attivita = attivita;
        calcolaGiorniMancanti();
    }

    public NotificaAttivita() {
        super();
    }

    public Attivita getAttivita() {
        return attivita;
    }

    public Integer getGiorniMancanti() {
        return giorniMancanti;
    }

    public void setGiorniMancanti(Integer giorniMancanti) {
        this.giorniMancanti = giorniMancanti;
    }

    public void setAttivita(Attivita attivita) {
        this.attivita = attivita;
        calcolaGiorniMancanti();
    }

    private void calcolaGiorniMancanti() {
        if (attivita != null && attivita.getDataScadenza() != null) {
            long giorniMancanti = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), attivita.getDataScadenza());
            setGiorniMancanti((int) giorniMancanti);
        }
    }

    @Override
    public String toString() {
        return "NotificaAttivita{" +
                "id=" + getId() +
                ", dataInvio=" + getDataInvio() +
                ", nome='" + getNome() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", urgenza=" + getUrgenza() +
                ", giorniMancanti=" + giorniMancanti +
                ", mittente=" + getMittente().getNome() +
                ", progetto=" + getProgetto().getNomeProgetto() +
                ", attivita=" + attivita.getNome() +
                ", destinatari=" + getDestinatari().stream().map(Coltivatore::getNome).toList() +
                '}';
    }
}