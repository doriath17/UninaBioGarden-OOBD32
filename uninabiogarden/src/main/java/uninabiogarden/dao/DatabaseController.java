package uninabiogarden.dao;

public class DatabaseController {

  private static DatabaseController instance;

  public static DatabaseController getInstance() {
    if (instance == null) {
      instance = new DatabaseController();
    }
    return instance;
  }

  private DatabaseController() {
  }

  private Database database = Database.getInstance();

  private UtenteDao utenteDao = new UtenteDao();

  public UtenteDao getUtenteDao() {
    return utenteDao;
  }

}
