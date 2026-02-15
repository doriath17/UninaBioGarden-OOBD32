package uninabiogarden.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.MainController;
import uninabiogarden.entities.*;

public class NotificaDAO {

    private Database database = Database.getInstance();

    public void saveNotifica(Notifica notifica) {
        
        String sql = "INSERT INTO Notificha (nome_evento, urgenza, descrizione, tipo, giorni_mancanti, id_proprietario, id_progetto) VALUES (?, ?, ?, ?, ?, ?, ?)";

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


    public List<Notifica> getAllNotifiche() {

        List<Notifica> list = new ArrayList<>();
        // SQL join to get the project name and activity name if they exist
        String sql = "SELECT * FROM notifica WHERE id_proprietario = ? ORDER BY data_invio DESC"; 

        

        try (var conn = database.getConnection(); var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, MainController.getInstance().getUtenteLoggato().getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                Progetto p = new Progetto();
                p.setId(rs.getLong("id_progetto"));

                Attivita a = null;
                if (rs.getObject("id_attività") != null) {
                a = new Attivita();
                a.setId(rs.getLong("id_attività"));
                }

                Notifica n = new Notifica();
                n.setId(rs.getLong("id"));
                n.setDataInvio(rs.getTimestamp("data_invio")); //The method setDataInvio(java.security.Timestamp) in the type Notifica is not applicable for the arguments (java.sql.Timestamp)Java(67108979)
                n.setNome(rs.getString("nome_evento"));
                n.setDescrizione(rs.getString("descrizione"));
                n.setUrgenza(Notifica.Urgenza.valueOf(rs.getString("urgenza")));
                n.setTipo(Notifica.Tipo.valueOf(rs.getString("tipo")));
                n.setGiorniMancanti(rs.getInt("giorni_mancanti"));
                n.setMittente((Proprietario) MainController.getInstance().getUtenteLoggato()); //The method setMittente(Proprietario) in the type Notifica is not applicable for the arguments (Utente)Java(67108979)

                n.setProgetto(p);
                n.setAttivita(a);

                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
