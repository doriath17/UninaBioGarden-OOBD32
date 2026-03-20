package uninabiogarden.dao;

public class DatabaseController {

  private UtenteDao utenteDao = new UtenteDao();
  private OrtoDao ortoDao = new OrtoDao();
  private LottoDao lottoDao = new LottoDao();
  private ColturaDao colturaDao = new ColturaDao();
  private ProgettoDao progettoDao = new ProgettoDao();
  private ColtivazioneDao coltivazioneDao = new ColtivazioneDao();
  private AttivitaDao attivitaDao = new AttivitaDao();
  private NotificaDAO notificaDao = NotificaDAO.getInstance();
  private ReportDao reportDao = ReportDao.getInstance();

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

  public ColtivazioneDao getColtivazioneDao() {
    return coltivazioneDao;
  }

  public AttivitaDao getAttivitaDao() {
    return attivitaDao;
  }

  public NotificaDAO getNotificaDao() {
    return notificaDao;
  }

  public ReportDao getReportDao() {
    return reportDao;
  }

}
