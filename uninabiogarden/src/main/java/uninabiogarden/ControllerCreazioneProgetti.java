package uninabiogarden;

public class ControllerCreazioneProgetti {

    MainController maincontroller;

    public ControllerCreazioneProgetti() {
        maincontroller = MainController.getInstance();
    }

    public void vaiAvanti1(){
        maincontroller.cambiaFinestra("CreazioneProgettoView2");
    }
    
    public void vaiIndietro2(){
        maincontroller.cambiaFinestra("CreazioneProgettoView1");
    }

    public void vaiAvanti2(){
        maincontroller.cambiaFinestra("CreazioneProgettoView3");
    }
}
