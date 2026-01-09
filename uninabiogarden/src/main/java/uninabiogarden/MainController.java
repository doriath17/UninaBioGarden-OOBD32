package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {

    private static MainController instance;
    
    // Sub-controllers
    private ControllerLogIn clogin;
    private ControllerSignUp csignin;
    // ...

    private MainController() {
        // ...
    }

    protected void creaSubControllers() {
        clogin = new ControllerLogIn();
        csignin = new ControllerSignUp();
        // ...
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void cambiaFinestra(String newWindowFxmlName) {
        
        // TODO: handle exceptions properly
        try {
            App.setRoot(newWindowFxmlName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Cambia Finestra!" + newWindowFxmlName);
    }

    public void newUser() {
        //TODO
        System.out.println("Nuovo Utente Registrato!");
    }

    public void logIn(){
        //TODO: actually check credentials with a new method
        System.out.println("Tentativo di Login!");
        cambiaFinestra("ProprietarioHomeView");
    }
}
