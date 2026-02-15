package uninabiogarden.exceptions;

public class DatabaseException extends RuntimeException {
  public static String msg = "Connessione al database fallita";

  public DatabaseException() {
    super(DatabaseException.msg);
  }

  public DatabaseException(String msg) {
    super(DatabaseException.msg + " -- " + msg);
  }
}
