package uninabiogarden;

public class ControllerProprietarioHomeView {

    MainController maincontroller;

    public ControllerProprietarioHomeView() {
        maincontroller = MainController.getInstance();
    }

    public void openProgettiView() {
        // TODO: decidere se così va bene o fare un metodo in maincontroller che passa alla finestra precedente
        maincontroller.cambiaFinestra("CreazioneProgettoView1");
    }

    public void openLottiView() {
        // TODO: decidere se così va bene o fare un metodo in maincontroller che passa alla finestra precedente
        maincontroller.cambiaFinestra("LottiView");
    }
    
}
