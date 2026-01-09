package uninabiogarden;

public class ControllerSignUp {
    
    MainController maincontroller;

    public ControllerSignUp() {
        maincontroller = MainController.getInstance();
    }

    public void signUpAction(){
        maincontroller.newUser();
    }

    public void indietro(){
        // TODO: decidere se così va bene o fare un metodo in maincontroller che passa alla finestra precedente
        maincontroller.cambiaFinestra("LoginView");
    }

}
