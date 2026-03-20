- FXML VIEW
  Questo enum è fondamentale per tracciare in modo comodo ed efficiente tutte i file fxml che descrivono le diverse schermate. Questo permette di riferirsi a questi file senza dover specificare direttamente il loro percorso. Questo risulta in un codice più pulito e facile da comprendere.

- UI CONTROLLER
  Questo controller ha lo scopo fondamentale di offrire a tutte la classi dell'interfaccia un insieme di metodi di navigazione che permettono di passare da una schermata all'altra.
  - FUNZIONE DI CACHE
    Per evitare di effettuare pesanti operazioni di caricamento dei file fxml, questo controller implementa una cache molto semplice che salva le schermate appena questo sono caricate la prima volta. Successivamente, invece di ricaricarle da zero, queste sono recuperate dalla cache. Si fa notare che le schermate sono caricate in modo lazy ovvero soltanto quando sono effettivamente necessario. Questo permette di ridurre i tempi di caricamento iniziale.
  - UTILIZZO DA PARTE DELL'INTERFACCIA GRAFICA:
    I metodi esposti da questo controller permettono di caricare le diverse schermate nella finestra principale (o anche in sottofinestre) e di sfruttare il vantaggio offerto dalla cache. Questa classe, come il MainController, utilizza il pattern singleton così che tutti questi metodi siano facilmente accessibili a tutte le classi dell'interfaccia grafica.

CLASSI DI CONTROLLO DELL'INTERFACCIA GRAFICA
Ogni classe dell'interfaccia grafica ad eccezione del UIController e dell'enum FXMLView, corrisponde ad un file fxml che ne descrive l'interfaccia. Esse sono appunto i controller di queste schermate e sono responsabili di gestire l'interazione con l'utente e di comunicare con il MainController per eseguire le operazioni necessarie. Per implementare la logica di navigazione tra schermate o aprire sottofinestre utilizzano i metodi esposti dal UIController.
