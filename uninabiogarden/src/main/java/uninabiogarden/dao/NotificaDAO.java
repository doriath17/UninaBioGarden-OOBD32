package uninabiogarden.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import uninabiogarden.entities.*;
import uninabiogarden.MainController;
import java.sql.Statement;

public class NotificaDAO {

    private Database database = Database.getInstance();

    private static NotificaDAO instance;

    private NotificaDAO() {}

    public static NotificaDAO getInstance() {
        if (instance == null) {
            instance = new NotificaDAO();
        }
        return instance;
    }

public void saveNotifica(Notifica notifica) {
    
    String sqlNotifica = "INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, giorni_mancanti, id_progetto, id_attivita) " +
                         "VALUES (?, ?::urgenza_notifica, ?, ?::tipo_notifica, ?, ?, ?)";

    String sqlRiceve = "INSERT INTO riceve (id_notifica, id_coltivatore, is_letta) VALUES (?, ?, false)";

    try (var conn = database.getConnection()) {

        conn.setAutoCommit(false);

        try (var stmtN = conn.prepareStatement(sqlNotifica, Statement.RETURN_GENERATED_KEYS);
             var stmtR = conn.prepareStatement(sqlRiceve)) {

            // Notifica
            stmtN.setString(1, notifica.getNome());
            stmtN.setString(2, notifica.getUrgenza().toString());
            stmtN.setString(3, notifica.getDescrizione());
            stmtN.setString(4, notifica.getTipo().toString());
            
            if (notifica.getGiorniMancanti() != null) {
                stmtN.setInt(5, notifica.getGiorniMancanti());
            } else { 
                stmtN.setNull(5, java.sql.Types.INTEGER);
            }

            stmtN.setLong(6, notifica.getProgetto().getId());

            if (notifica.getAttivita() != null) {
                stmtN.setLong(7, notifica.getAttivita().getId());
            } else {
                stmtN.setNull(7, java.sql.Types.INTEGER);
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
                // for (Coltivatore c : notifica.getDestinatari()) {
                //     stmtR.setInt(1, generatedId);
                //     stmtR.setLong(2, c.getId());
                //     stmtR.addBatch();
                // }
                // stmtR.executeBatch();
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
            SELECT n.*, p.nome as nome_progetto, a.nome as nome_attivita, u.nome as nome_proprietario, u.id as id_proprietario
            FROM notifica n
            LEFT JOIN progetto p ON n.id_progetto = p.id
            LEFT JOIN attivita a ON n.id_attivita = a.id
            LEFT JOIN utente u ON p.id_proprietario = u.id
            WHERE p.id = ?
            ORDER BY n.data_invio DESC
            """;

        try (var conn = database.getConnection(); 
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, utente.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Notifica n = new Notifica();
                n.setId(rs.getLong("id"));
                n.setNome(rs.getString("nome_evento"));
                n.setDescrizione(rs.getString("descrizione"));
                n.setUrgenza(Notifica.Urgenza.valueOf(rs.getString("urgenza")));
                n.setTipo(Notifica.Tipo.valueOf(rs.getString("tipo")));
                n.setGiorniMancanti(rs.getInt("giorni_mancanti"));
                n.setMittente((Proprietario) MainController.getInstance().getUtenteLoggato());
                n.setDataInvio(rs.getDate("data_invio").toLocalDate());
                
                // Proprietario Proxy
                Proprietario pr = new Proprietario();
                pr.setId(rs.getLong("id_proprietario"));
                pr.setNome(rs.getString("nome_proprietario"));
                n.setMittente(pr);

                // Progetto Proxy
                Progetto p = new Progetto();
                p.setId(rs.getLong("id_progetto"));
                p.setNomeProgetto(rs.getString("nome_progetto")); 
                n.setProgetto(p);

                // Attivita Proxy
                long idAtt = rs.getLong("id_attivita");
                if (!rs.wasNull()) {
                    // Attività anonima 
                    Attivita a = new Attivita() {}; 
                    a.setId(idAtt);
                    a.setNome(rs.getString("nome_attivita"));
                    n.setAttivita(a);
                }

                list.add(n);
            }
        } catch (Exception e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
        }
        return list;
    }   
    
}
