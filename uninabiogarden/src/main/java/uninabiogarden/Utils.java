package uninabiogarden;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import uninabiogarden.entities.Coltivatore;

public class Utils {

  public static void addCharacterLimit(TextInputControl textField, int limit) {
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

  public static <T> void setupCheckBoxColumn(TableColumn<T, Void> column,
      Map<T, SimpleBooleanProperty> selectionMap) {
    column.setCellFactory(col -> new TableCell<T, Void>() {
      private final CheckBox checkBox = new CheckBox();
      private SimpleBooleanProperty currentProperty = null;

      {
        // Initializza il checkbox e aggiungi listener per aggiornare la mappa di
        // selezione quando viene cliccato
        checkBox.setOnAction(event -> {
          T value = getTableRow().getItem();
          if (value != null) {
            // Aggiorna lo stato di selezione nella mappa
            selectionMap.get(value).set(checkBox.isSelected());
          }
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (currentProperty != null) {
          // Se c'è una proprietà attualmente associata al checkbox, rimuovi il binding
          checkBox.selectedProperty().unbindBidirectional(currentProperty);
          currentProperty = null;
        }

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setGraphic(null);
        } else {
          T value = getTableRow().getItem();

          // Crea la SimpleBooleanProperty se non esiste
          if (!selectionMap.containsKey(value)) {
            selectionMap.put(value, new SimpleBooleanProperty(false));
          }

          // Qui le SimpleBooleanProperty vengono "collegate" al checkbox con un binding
          // bidirezionale che significa che se l'utente clicca il checkbox, la proprietà
          // si aggiorna, e se la proprietà viene aggiornata (ad esempio quando si sposta
          // un elemento da una tabella all'altra), il checkbox si aggiorna di
          // conseguenza. In questo modo lo stato di selezione rimane sempre sincronizzato
          // tra la UI e la logica dell'applicazione.
          currentProperty = selectionMap.get(value);
          checkBox.selectedProperty().bindBidirectional(currentProperty);

          setGraphic(checkBox);
        }
      }
    });
  }

  public static <T> void moveSelectionTo(ObservableList<T> sourceList, ObservableList<T> targetList,
      Map<T, SimpleBooleanProperty> sourceSelectionMap,
      Map<T, SimpleBooleanProperty> targetSelectionMap) {
    List<T> selectedFromSource = sourceList
        .stream()
        .filter(item -> sourceSelectionMap.containsKey(item) && sourceSelectionMap.get(item).get())
        .collect(Collectors.toList());

    // Aggiungi gli oggetti selezionati alla lista di destinazione
    targetList.addAll(selectedFromSource);

    // Rimuovi gli oggetti selezionati dalla lista di origine
    sourceList.removeAll(selectedFromSource);

    // Reset delle selezioni dopo il trasferimento
    selectedFromSource.forEach(item -> {
      if (sourceSelectionMap.containsKey(item)) {
        sourceSelectionMap.get(item).set(false);
      }
    });
  }

}
