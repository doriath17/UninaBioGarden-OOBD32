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
  private OrtoDao ortoDao = new OrtoDao();
  private LottoDao lottoDao = new LottoDao();
  private ColturaDao colturaDao = new ColturaDao();
  private ProgettoDao progettoDao = new ProgettoDao();
  private ColtivazioneDao coltivazioneDao = new ColtivazioneDao();
  private AttivitaDao attivitaDao = new AttivitaDao();
  private NotificaDAO notificaDao = NotificaDAO.getInstance();

  public UtenteDao getUtenteDao() {
    return utenteDao;
  }

  public OrtoDao getOrtoDao() {
    return ortoDao;
  }

  public LottoDao getLottoDao() {
    return lottoDao;
  }

  public ColturaDao getColturaDao() {
    return colturaDao;
  }

  public ProgettoDao getProgettoDao() {
    return progettoDao;
  }

  public Database getDatabase() {
    return database;
  }

  public ColtivazioneDao getColtivazioneDao() {
    return coltivazioneDao;
  }

  public AttivitaDao getAttivitaDao() {
    return attivitaDao;
  }

  public NotificaDAO getNotificaDao() {
    return notificaDao;
  }
  
}
