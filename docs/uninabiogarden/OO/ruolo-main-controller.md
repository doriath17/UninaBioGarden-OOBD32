Il MainController è la classi di controllo centrale, il cui ruolo è fare da tramite tra le classi dell'interfaccia grafica con il database sottostante. Per poter svolgere questo compito esso mantiene uno stato interno che riflette la sessione utente attualmente attiva. In particolare:

- ACCESSO AL DATABASE:
  Per accedere al database, questo controller utilizza un istanza di DatabaseController il quale, a sua volta, mantiene un'istanza delle classi DAO ed esponendo dei getters che ne permenttono l'utilizzo. Quindi, essenzialmente, per eseguire le operazioni di lettura e scrittura sul database, il MainController utilizza i metodi esposti dalle classi DAO attraverso l'istanza di DatabaseController.
- CARICAMENTO DATI:
  Appena l'utente esegue correttamente il login, il MainController carica tutti i dati associati a quell'utente più eventualmente altri dati necessari per il funzionamento dell'applicazione.
  Ad esempio, al login del proprietario, vengono prima caricati in memoria i dati generali per permettere il funzionamento come il catalogo delle colture e gli orti registrati. Successivamente sono caricati i dati specifici del proprietario.

- STATO INTERNO:
  Il MainController mantiene uno stato interno che riflette il database sottostante e che permette di poter gestire le operazione di lettura in modo efficiente senza dover accedere direttamente al database.

- SCRITTURA DATI:
  Quando si fanno operazioni di scrittura sul database come inserimenti, update o cancellazioni, il MainController adotta questa strategia:
  1. Se necessario, soprattuto per operazioni di update e inserimento, i nuovi dati vengono prima validati.
  2. Se la validazione va a buon fine, il controller cerca di eseguire l'operazione di scrittura sul database.
  3. Se l'operazione di scrittura va a buon fine, allora viene aggiornato lo stato interno del MainController per riflettere i nuovi dati. Eventualmente il risultato è restituito all'interfaccia grafica (ad esempio facendo il return dell'istanza appena modificata o creata) o semplicemente si assume che se non vengono sollevate eccezioni allora l'operazione è andata a buon fine e quindi l'interfaccia grafica si può aggiornare di conseguenza.

- LOGOUT:
  Quando la sessione dell'utente termina in seguito ad un logout, il MainController si occupa di pulire il suo stato interno per prepararsi ad una nuova sessione utente.

- UTILIZZO DA PARTE DELL'INTERFACCIA GRAFICA:
  Il MainController utilizza il pattern singleton: nel sistema è presente una solo istanza e questa è accessibile a chiunque. In questo modo, tutti controller dell'interfaccia grafica possono accedere al MainController.
