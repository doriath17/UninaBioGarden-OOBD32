package uninabiogarden;

public enum FxmlView {
  MAIN_VIEW("/uninabiogarden/MainView.fxml"),
  LOGIN_VIEW("/uninabiogarden/LoginView.fxml"),
  SIGNUP_VIEW("/uninabiogarden/SignUpView.fxml"),
  HOME_VIEW("/uninabiogarden/Home.fxml"),
  DASHBOARD_VIEW("/uninabiogarden/Dashboard.fxml"),
  PROFILO_VIEW("/uninabiogarden/Profilo.fxml"),
  PROGETTI_VIEW("/uninabiogarden/Progetti.fxml"),
  ORTI_VIEW("/uninabiogarden/Orti.fxml"),
  CREA_ORTO_VIEW("/uninabiogarden/CreaOrto.fxml"),
  LOTTI_VIEW("/uninabiogarden/Lotti.fxml"),
  CREA_LOTTO_VIEW("/uninabiogarden/CreaLotto.fxml"),

  CREA_PROGETTO_STEP_1("/uninabiogarden/CreaProgettoStep1.fxml"),
  CREA_PROGETTO_STEP_2("/uninabiogarden/CreaProgettoStep2.fxml"),
  CREA_PROGETTO_STEP_3("/uninabiogarden/CreaProgettoStep3.fxml");

  private final String fxmlPath;

  FxmlView(String fxmlPath) {
    this.fxmlPath = fxmlPath;
  }

  public String getFxmlPath() {
    return fxmlPath;
  }
}