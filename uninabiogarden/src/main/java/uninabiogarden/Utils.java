package uninabiogarden;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Utils {

  public static void addCharacterLimit(TextField textField, int limit) {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      // Se l'utente sta inserendo testo (non cancellando)
      if (change.isAdded()) {
        int newLength = change.getControlNewText().length();
        if (newLength > limit) {
          // Ignora il cambiamento se supera il limite
          return null;
        }
      }
      return change;
    };

    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  public static void addDoubleFilter(TextField textField) {
    // Default limits derived from DECIMAL(10,2): 10 total precision, 2 fraction =>
    // 8 integer, 2 fraction
    addDoubleFilter(textField, 8, 2);
  }

  public static void addDoubleFilter(TextField textField, int maxIntegerDigits, int maxFractionDigits) {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();

      // Allow empty string
      if (newText.isEmpty()) {
        return change;
      }

      if (maxIntegerDigits < 1 || maxFractionDigits < 0) {
        return null;
      }

      String regex = "^\\d{1," + maxIntegerDigits + "}(\\.\\d{0," + maxFractionDigits + "})?$";

      if (newText.matches(regex)) {
        return change;
      }

      return null;
    };

    textField.setTextFormatter(new TextFormatter<>(filter));
  }
}
