package uninabiogarden;

public enum FxmlView {
  MAIN_VIEW("/uninabiogarden/MainView.fxml"),
  LOGIN_VIEW("/uninabiogarden/LoginView.fxml"),
  SIGNUP_VIEW("/uninabiogarden/SignUpView.fxml");

  private final String fxmlPath;

  FxmlView(String fxmlPath) {
    this.fxmlPath = fxmlPath;
  }

  public String getFxmlPath() {
    return fxmlPath;
  }
}