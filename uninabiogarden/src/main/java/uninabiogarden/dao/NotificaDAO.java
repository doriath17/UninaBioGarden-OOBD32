package uninabiogarden.dao;

import uninabiogarden.entities.Notifica;

public class NotificaDAO {

    private Database database = Database.getInstance();

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
    
}
