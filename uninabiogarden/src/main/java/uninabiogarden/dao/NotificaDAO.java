package uninabiogarden.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import uninabiogarden.entities.*;
import uninabiogarden.MainController;

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
        
        String sql = "INSERT INTO Notifiche (nome_evento, urgenza, descrizione, tipo, giorni_mancanti, id_proprietario, id_progetto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (var conn = database.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notifica.getNome());
            stmt.setString(2, notifica.getUrgenza().toString());
            stmt.setString(3, notifica.getDescrizione());
            stmt.setString(4, notifica.getTipo().toString());
            stmt.setInt(5, notifica.getGiorniMancanti());
            stmt.setLong(6, notifica.getMittente().getId());
            stmt.setLong(7, notifica.getProgetto().getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Errore durante il salvataggio della notifica: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Notifica> getAllNotifiche() {

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

            stmt.setLong(1, MainController.getInstance().getUtenteLoggato().getId());
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
