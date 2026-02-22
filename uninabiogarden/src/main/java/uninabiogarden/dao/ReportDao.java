package uninabiogarden.dao;

import java.util.ArrayList;

import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Report;

public class ReportDao {

    Database database = Database.getInstance();

    public static ReportDao instance = ReportDao.getInstance();

    private ReportDao() {}

    public static ReportDao getInstance() {
        if (instance == null) {
            instance = new ReportDao();
        }
        return instance;
    }


    public ArrayList<Report> getRaccoltaByLotto(Lotto lotto) {
        
        String sql = """
                    SELECT 
	                    c.nome_comune AS nome_coltura,
                        COUNT(r.id) AS numero_totale_raccolte,
                        AVG(r.quantita_effettiva_kg) AS quantita_media,
                        MIN(r.quantita_effettiva_kg) AS quantita_minima,
                        MAX(r.quantita_effettiva_kg) AS quantita_massima,
                        MAX(a.data_fine) AS data_ultima_raccolta
                    FROM lotto l
                    JOIN progetto p ON p.id_lotto = l.id
                    JOIN coltivazione col ON col.id_progetto = p.id
                    JOIN coltura c ON col.id_coltura = c.id
                    JOIN attivita a ON a.id_coltivazione = col.id
                    JOIN raccolta r ON r.id = a.id
                    WHERE l.id = ? AND a.stato = 'COMPLETATA'  
                    GROUP BY c.nome_comune;
                    """;

        ArrayList<Report> reports = new ArrayList<>();

        try (var conn = database.getConnection();
            var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, lotto.getId());
            var rs = stmt.executeQuery();

            while (rs.next()) {
                Report report = new Report(
                    rs.getString("nome_coltura"),
                    rs.getInt("numero_totale_raccolte"),
                    rs.getDate("data_ultima_raccolta").toLocalDate(),
                    rs.getDouble("quantita_media"),
                    rs.getDouble("quantita_minima"),
                    rs.getDouble("quantita_massima")
                );

                reports.add(report);
            }

             return reports;


        } catch (Exception e) {
            System.err.println("Errore fatale: " + e.getMessage());
            throw new RuntimeException(e);
        }
        
    }
    
}
