package uninabiogarden.dao;

import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Orto;

public class LottoDao {

  private Database database = Database.getInstance();

  public Long saveLotto(Lotto lotto) {
    var sql = "INSERT INTO lotto (codice_lotto, estensione_mq, tipologia_terreno, id_proprietario, id_orto) VALUES (?, ?, ?::tipologia_terreno, ?, ?)";

    Long id = null;
    try (var conn = database.getConnection();
        var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, lotto.getCodiceLotto());
      stmt.setDouble(2, lotto.getEstensioneMq());
      stmt.setString(3, lotto.getTipologiaTerreno().name());
      stmt.setLong(4, lotto.getProprietario().getId());
      stmt.setLong(5, lotto.getOrto().getId());

      var rows = stmt.executeUpdate();
      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        id = result.getLong("id");
      } else {
        throw new RuntimeException("Creazione lotto fallita, nessuna riga inserita");
      }

    } catch (Exception e) {
      System.err.println("Errore durante la creazione del lotto: " + e.getMessage());
      throw new RuntimeException(e);
    }

    return id;
  }

  public List<Lotto> findAll(Long proprietarioId) {
    var sql = """
            SELECT *
            FROM lotto
            WHERE id_proprietario = {{proprietarioId}}
        """;
    sql = sql.replace("{{proprietarioId}}", String.valueOf(proprietarioId));

    List<Lotto> lotti = new ArrayList<>();

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      while (result.next()) { // lotto trovato
        Lotto lotto = new Lotto();
        lotto.setId(result.getLong("id"));
        lotto.setCodiceLotto(result.getString("codice_lotto"));
        lotto.setEstensioneMq(result.getDouble("estensione_mq"));
        lotto.setTipologiaTerreno(Lotto.TipologiaTerreno.valueOf(result.getString("tipologia_terreno")));

        // proxy per l'orto che dovrebbe gia essere caricato in memoria
        Orto ortoProxy = new Orto();
        ortoProxy.setId(result.getLong("id_orto"));
        lotto.setOrto(ortoProxy);

        lotti.add(lotto);
      }
      return lotti;

    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei lotti: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<Lotto> findAvailableLotti(Long id) {
    var sql = """
          SELECT *
          FROM vista_lotti_disponibili
          WHERE id_proprietario = {{id}}
        """;
    sql = sql.replace("{{id}}", String.valueOf(id));

    List<Lotto> lotti = new ArrayList<>();

    try (var conn = database.getConnection(); var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      while (result.next()) { // lotto trovato
        Lotto lotto = new Lotto();
        lotto.setId(result.getLong("id"));
        lotto.setCodiceLotto(result.getString("codice_lotto"));
        lotto.setEstensioneMq(result.getDouble("estensione_mq"));
        lotto.setTipologiaTerreno(Lotto.TipologiaTerreno.valueOf(result.getString("tipologia_terreno")));

        // proxy per l'orto che dovrebbe gia essere caricato in memoria
        Orto ortoProxy = new Orto();
        ortoProxy.setId(result.getLong("id_orto"));
        lotto.setOrto(ortoProxy);

        lotti.add(lotto);
      }
      return lotti;

    } catch (Exception e) {
      System.err.println("Errore durante il recupero dei lotti disponibili: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

}
