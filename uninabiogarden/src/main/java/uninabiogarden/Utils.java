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

}
