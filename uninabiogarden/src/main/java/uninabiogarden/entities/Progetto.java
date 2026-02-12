package uninabiogarden.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import uninabiogarden.dto.ProgettoDto;

public class Progetto {

  public enum Stato {
    PIANIFICATO, ATTIVO, CONCLUSO
  }

  private Long id;
  private String nomeProgetto;
  private String descrizione;
  private Stato stato; // "In preparazione", "In corso", "Completato"
  private LocalDateTime dataCreazione;
  private LocalDateTime dataInizio;
  private LocalDateTime dataFine;

  private Proprietario proprietario;
  private Lotto lotto;

  public Progetto() {
  }

  public Progetto(String nomeProgetto, String descrizione, Stato stato, LocalDateTime dataCreazione,
      LocalDateTime dataInizio,
      LocalDateTime dataFine) {
    this.nomeProgetto = nomeProgetto;
    this.descrizione = descrizione;
    this.stato = stato;
    this.dataCreazione = dataCreazione;
    this.dataInizio = dataInizio;
    this.dataFine = dataFine;
  }

  public Progetto(ProgettoDto dto) {
    try {
      this.nomeProgetto = dto.nome;
      this.descrizione = dto.descrizione;
      this.stato = Stato.valueOf(dto.stato);

      this.dataCreazione = LocalDateTime.parse(dto.dataCreazione);
      this.dataInizio = LocalDateTime.parse(dto.dataInizio);
      this.dataFine = LocalDateTime.parse(dto.dataFine);

      this.proprietario = new Proprietario();
      if (dto.proprietarioId != null) {
        this.proprietario.setId(dto.proprietarioId);
      }

    } catch (DateTimeParseException e) {
      System.err.println("Errore nella conversione delle date: " + e.getMessage());
      throw new IllegalArgumentException("Formato data non valido");
    } catch (Exception e) {
      System.err.println("Errore nella creazione del progetto: " + e.getMessage());
      throw new RuntimeException("Errore nella creazione del progetto");
    }

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeProgetto() {
    return nomeProgetto;
  }

  public void setNomeProgetto(String nomeProgetto) {
    this.nomeProgetto = nomeProgetto;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Stato getStato() {
    return stato;
  }

  public void setStato(Stato stato) {
    this.stato = stato;
  }

  public LocalDateTime getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(LocalDateTime dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public LocalDateTime getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(LocalDateTime dataInizio) {
    this.dataInizio = dataInizio;
  }

  public LocalDateTime getDataFine() {
    return dataFine;
  }

  public void setDataFine(LocalDateTime dataFine) {
    this.dataFine = dataFine;
  }

  public Proprietario getProprietario() {
    return proprietario;
  }

  public void setProprietario(Proprietario proprietario) {
    this.proprietario = proprietario;
  }

  public Lotto getLotto() {
    return lotto;
  }

  public void setLotto(Lotto lotto) {
    this.lotto = lotto;
  }
}
