package uninabiogarden.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.*;
import uninabiogarden.MainController;
import java.sql.Statement;

public class NotificaDAO {

  private Database database = Database.getInstance();

  private static NotificaDAO instance;

  private NotificaDAO() {
  }

  public static NotificaDAO getInstance() {
    if (instance == null) {
      instance = new NotificaDAO();
    }
    return instance;
  }

    public void saveNotifica(Notifica notifica) {
        
        String sqlNotifica = "INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, id_progetto, id_attivita) " +
                             "VALUES (?, ?::urgenza_notifica, ?, ?::tipo_notifica, ?, ?)";
    
        String sqlRiceve = "INSERT INTO riceve (id_notifica, id_coltivatore, is_letta) VALUES (?, ?, false)";
    
        try (var conn = database.getConnection()) {
        
            conn.setAutoCommit(false);
        
            try (var stmtN = conn.prepareStatement(sqlNotifica, Statement.RETURN_GENERATED_KEYS);
                 var stmtR = conn.prepareStatement(sqlRiceve)) {
                
                String tipoValue;
                if (notifica instanceof NotificaAttivita) {
                    tipoValue = "NOTIFICA_ATTIVITA_IMMINENTE";
                } else {
                    tipoValue = "NOTIFICA_PROGETTO";
                }
                
                stmtN.setString(1, notifica.getNome());
                stmtN.setString(2, notifica.getUrgenza().toString());
                stmtN.setString(3, notifica.getDescrizione());
                stmtN.setString(4, tipoValue);
    
                stmtN.setLong(5, notifica.getProgetto().getId());
    
                // Controlla se si deve caricare anche id_attivita o mettere null
                if (notifica instanceof NotificaAttivita) {

                    NotificaAttivita notificaAttivita = (NotificaAttivita) notifica;

                    if (notificaAttivita.getAttivita() != null) {
                        stmtN.setLong(6, notificaAttivita.getAttivita().getId());
                    } else {
                        stmtN.setNull(6, java.sql.Types.INTEGER);
                    }

                } else {
                    stmtN.setNull(6, java.sql.Types.INTEGER);
                }
            
                stmtN.executeUpdate();
            
                // recupero id notifica
                int generatedId = -1;
                try (var rs = stmtN.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            
                // riceve
                if (generatedId != -1 && notifica.getDestinatari() != null) {
                    for (Coltivatore c : notifica.getDestinatari()) {
                        stmtR.setInt(1, generatedId);
                        stmtR.setLong(2, c.getId());
                        stmtR.executeUpdate();
                    }
                }
            
                // salva tutto
                conn.commit();
                System.out.println("Notifica salvata con successo! ID: " + generatedId);
            
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
            
        } catch (Exception e) {
            System.err.println("Errore fatale: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Notifica> getAllNotificheOfUtente(Utente utente) {

        ArrayList<Notifica> list = new ArrayList<>();
        
        String sql = """
                SELECT DISTINCT n.*, p.nome as nome_progetto, a.nome as nome_attivita, u.nome as nome_proprietario, u.id as id_proprietario, a.data_scadenza as data_scadenza_attivita
                FROM notifica n
                LEFT JOIN progetto p ON n.id_progetto = p.id
                LEFT JOIN attivita a ON n.id_attivita = a.id
                LEFT JOIN utente u ON p.id_proprietario = u.id
                LEFT JOIN riceve r ON n.id = r.id_notifica
                WHERE p.id_proprietario = ? OR r.id_coltivatore = ?
                ORDER BY n.data_invio DESC
                """;

        try (var conn = database.getConnection(); 
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, utente.getId());
            stmt.setLong(2, utente.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                // per capire che classe instanziare
                String tipo = rs.getString("tipo");
                Notifica n;

                if ("NOTIFICA_ATTIVITA_IMMINENTE".equals(tipo)) {
                    n = new NotificaAttivita();
                } else {
                    n = new Notifica();
                }

                n.setId(rs.getLong("id"));
                n.setNome(rs.getString("nome_evento"));
                n.setDescrizione(rs.getString("descrizione"));
                n.setUrgenza(Notifica.Urgenza.valueOf(rs.getString("urgenza")));
                // n.setGiorniMancanti(rs.getInt("giorni_mancanti"));
                n.setDataInvio(rs.getDate("data_invio").toLocalDate());
                
                // proprietario proxy
                Proprietario pr = new Proprietario();
                pr.setId(rs.getLong("id_proprietario"));
                pr.setNome(rs.getString("nome_proprietario"));
                n.setMittente(pr);

                // progetto proxy
                Progetto p = new Progetto();
                p.setId(rs.getLong("id_progetto"));
                p.setNomeProgetto(rs.getString("nome_progetto")); 
                n.setProgetto(p);

                // solo per NotificaAttivita
                if (n instanceof NotificaAttivita) {
                    
                    // attivita proxy
                    long idAtt = rs.getLong("id_attivita");
                    if (!rs.wasNull()) {
                        // attività anonima 
                        Attivita a = new Attivita() {}; 
                        a.setId(idAtt);
                        a.setNome(rs.getString("nome_attivita"));
                        ((NotificaAttivita) n).setAttivita(a);

                        java.sql.Date dataSca = rs.getDate("data_scadenza_attivita");
                        if (dataSca != null) {
                            a.setDataScadenza(dataSca.toLocalDate());
                        }

                        // calcola giorni mancanti
                        ((NotificaAttivita) n).setAttivita(a);

                    }

                    // giorni mancanti
                    int giorniMancanti = rs.getInt("giorni_mancanti");
                    if (!rs.wasNull()) {
                        ((NotificaAttivita) n).setGiorniMancanti(giorniMancanti);
                    }
                    
                }

                list.add(n);
            }
        } catch (Exception e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
        }
        return list;
    }
}

