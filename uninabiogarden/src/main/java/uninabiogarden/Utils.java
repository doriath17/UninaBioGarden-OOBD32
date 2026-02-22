package uninabiogarden;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

public class Utils {

  public static String extractSQLErrorMessage(SQLException e) {
    if (e == null) {
      return "Errore sconosciuto";
    }

    String message = e.getMessage();
    if (message == null || message.isEmpty()) {
      return "Errore del database";
    }

    // Rimuovi il prefisso "ERROR: "
    if (message.startsWith("ERROR: ")) {
      message = message.substring(7);
    }

    // Trova l'indice di "Where:" o "\n Where:"
    int whereIndex = message.indexOf("\n  Where:");
    if (whereIndex == -1) {
      whereIndex = message.indexOf("\nWhere:");
    }
    if (whereIndex != -1) {
      message = message.substring(0, whereIndex);
    }

    message = message.trim();

    return message;
  }

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
    // default: massimo 8 cifre intere e 2 decimali
    addDoubleFilter(textField, 8, 2);
  }

  public static void addDoubleFilter(TextField textField, int maxIntegerDigits, int maxFractionDigits) {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();

      // Permetti campo vuoto per facilitare la digitazione, altrimenti il filtro
      // bloccherebbe l'inserimento
      if (newText.isEmpty()) {
        return change;
      }

      // Non permettere il segno negativo, né come primo carattere né in qualsiasi
      // altra posizione
      if (newText.startsWith("-") || newText.contains("-")) {
        return null;
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

  public static <T> ObjectProperty<T> setupCheckBoxColumnExclusive(TableColumn<T, Void> column) {
    SimpleObjectProperty<T> selectedItem = new SimpleObjectProperty<>(null);
    column.setCellFactory(col -> new TableCell<T, Void>() {
      private final CheckBox checkBox = new CheckBox();

      {
        checkBox.setOnAction(event -> {
          T value = getTableRow().getItem();
          if (value != null) {
            selectedItem.set(checkBox.isSelected() ? value : null);
            getTableView().refresh();
          }
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setGraphic(null);
        } else {
          checkBox.setSelected(getTableRow().getItem().equals(selectedItem.get()));
          setGraphic(checkBox);
        }
      }
    });
    return selectedItem;
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

  public static <T> void mostraDialogConfermaConAzione(
      String messaggio,
      T data,
      Consumer<T> onConfirm) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Conferma Azione");
    alert.setHeaderText(null);
    alert.setContentText(messaggio);

    ButtonType buttonConferma = new ButtonType("Conferma");
    ButtonType buttonAnnulla = new ButtonType("Annulla");
    alert.getButtonTypes().setAll(buttonConferma, buttonAnnulla);

    // Rendi il dialog non ridimensionabile
    alert.setResizable(false);

    // Mostra il dialog e esegui l'azione se l'utente conferma
    alert.showAndWait().ifPresent(response -> {
      if (response == buttonConferma) {
        onConfirm.accept(data);
      }
    });
  }

  public static <T> void addButtonToColumn(
      TableColumn<T, Void> column,
      String buttonText,
      Consumer<T> rowAction) {
    column.setCellFactory(param -> new TableCell<T, Void>() {
      private final Button btn = new Button(buttonText);

      {
        btn.setOnAction(event -> {
          T rowData = getTableView().getItems().get(getIndex());
          rowAction.accept(rowData);
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setGraphic(null);
        } else {
          setGraphic(btn);
        }
      }
    });
  }

  public static void showError(Label label, String message) {
    if (label != null) {
      label.setText(message);
      label.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
      label.setVisible(true);
    }
  }

  public static void showSuccess(Label label, String message) {
    if (label != null) {
      label.setText(message);
      label.setStyle("-fx-text-fill: #388e3c; -fx-font-weight: bold;");
      label.setVisible(true);
    }
  }

  public static void hideMessage(Label label) {
    if (label != null) {
      label.setVisible(false);
      label.setText("");
    }
  }

}
