2 Analisi del Dominio
2.1 Descrizione del Contesto
La piattaforma UninaBioGarden è un sistema software progettato per la gestione di orti
urbani condivisi. Il sistema è utilizzato principalmente da due tipologie di utenti: i
proprietari dei lotti su cui si svolgono le coltivazioni e i coltivatori che svolgono le
diverse attività necessarie a tali coltivazioni. Lo scopo principale della piattaforma è
fornire un ambiente di gestione sia per i proprietari che per i coltivatori, facilitando la
collaborazione e il tracciamento delle attività agricole.
2.2 Attori del Sistema
2.2.1 Il Ruolo del Proprietario
Il proprietario può registrare un orto urbano condiviso ed eventualmente andare a regi-
strare dei lotti, da egli posseduti, al suo interno. La presenza dei lotti è fondamentale
alla pianificazione di un progetto in quanto questo prende luogo su un lotto specifico. Il
proprietario infatti deve essere in grado di pianificare un progetto specificando su quale
lotto questo si svolge.
Inoltre il proprietario deve essere in grado di notificare i coltivatori del progetto di eventi
che riguardano il progetto, come ad esempio un’anomalia o una carestia, o che riguardano
attività imminenti, ad esempio un’irrigazione programmata per il giorno dopo.
2.2.2 Il Ruolo del Coltivatore
Nel sistema il coltivatore è un’entità abbastanza passiva, in quanto non può pianificare un
progetto, una coltivazione, un’attività e non può inviare notifiche. Il coltivatore tuttavia
è fondamentale per lo svolgimento generale dei progetti perché è colui che è responsabile
delle attività che vi sono assegnate e lui ne può gestire il ciclo di vita.
2.3 Entità del Dominio
2.3.1 L’Orto
Il sistema prevede la gestione di orti urbani condivisi. Un proprietario è l’utente che
può registrare un orto nel sistema ma non ne detiene la proprietà esclusiva: una volta
registrato anche altri proprietari potranno registrare i propri lotti nell’orto. Un orto è
caratterizzato da un nome, un indirizzo e dalla data in cui esso è registrato.
2.3.2 Il Lotto
Un lotto è un’area fisica all’interno di un orto urbano condiviso su cui si svolgono le
coltivazioni di un certo progetto. Un lotto è caratterizzato da un’estensione (in metri
quadrati) e da un codice identificativo del lotto, internamente all’orto urbano in cui si
trova (ad esempio il lotto 017A all’interno di un certo orto).
Un lotto è associato ad un proprietario, che è colui che lo ha registrato. Vincolo impor-
tante: Un lotto può ospitare più progetti del proprietario che lo ha registrato ma soltanto
un progetto attivo per volta.
7 di 63
2 ANALISI DEL DOMINIO UninaBioGarden
2.3.3 Il Progetto
Il progetto è l’entità centrale del sistema. Esso, come anticipato precedentemente, occupa
uno dei lotti del proprietario che lo ha pianificato. Un progetto è caratterizzato da un
nome, una descrizione, una data di inizio e una data di fine e può trovarsi in due stati
differenti a seconda del suo ciclo vita:
Attivo Il progetto è stato pianificato ma non è ancora concluso. In questa fase è pos-
sibile programmare e completare tutte le attività che caratterizzano le coltivazioni
associate al progetto.
Concluso Non è più possibile programmare o completare nessuna attività associata al
progetto.
Un progetto infatti non può essere pianificato se non è associato ad un lotto, e questo è
necessario per definire lo spazio fisico in cui si svolgeranno le coltivazioni del progetto.
Diversi coltivatori possono essere associati ad un progetto e questo definisce l’insieme di
persone che potranno essere selezionate per lo svolgimento delle attività.
Come si vedrà in dettaglio in seguito, ad un progetto si possono associare una o più
coltivazioni di colture specifiche e ognuna di queste è a sua volta caratterizzata dall’insieme
di attività che ne definiscono il ciclo vita.
2.3.4 Il Catalogo delle Colture
Il sistema fornisce di default un catalogo delle colture. Ogni coltura è caratterizzata da un nome, una descrizione, un tempo di maturazione
previsto (in giorni). Il catalogo delle colture è fondamentale per la pianificazione di un
progetto in quanto quando si pianifica un progetto è necessario specificare quali colture
si vogliono coltivare e quindi quali coltivazioni si vogliono creare.
2.3.5 La Coltivazione
Un progetto può prevedere una o più coltivazioni, ognuna caratterizzata da una specifica
coltura presente nel catalogo delle colture. Una coltivazione è caratterizzata da una data
di inizio e di fine, uno stato di salute (ottimo, stabile, sofferente, critico e compromesso)
e uno stato che ne descrive il ciclo vita.
Le coltivazioni infatti, come il progetto, hanno un loro ciclo vita che ruota intorno alle
attività che la caratterizzano e in particolare allo svolgimento dell’attività di raccolta che
ne sancisce la fine:

1. Attiva: Una coltivazione si può definire attiva se non è ancora iniziata la fase di
   raccolta. In questa fase è possibile programmare e completare tutte le attività che
   poi porteranno finalmente alla raccolta.
2. In Raccolta: Quando inizia la raccolta, la coltivazione entra in una fase in cui è
   possibile solo completare l’attività di raccolta.
3. Conclusa: Quando la raccolta è completata, la coltivazione è conclusa e non è più
   possibile programmare o completare nessuna attività.
   8 di 63
   2 ANALISI DEL DOMINIO UninaBioGarden
   2.3.6 Le Attività
   Quando una coltivazione è attiva è possibile programmare e completare tutte le attività
   che la caratterizzano. Ci sono diverse tipologie di attività che è possibile programmare:
   Semina Una semina è caratterizzata dalla quantità di semi disponibili e dalla profondità
   a cui devono essere piantati i semi.
   Irrigazione È caratterizzata dalla quantità di acqua da utilizzare e dal metodo da usa-
   re per irrigare. I metodi di irrigazione possono essere: a goccia, a pioggia, a
   scorrimento, manuale e nebulizzazione.
   Concimazione È caratterizzata dalla tipologia di concime da usare (organico, minerale
   o compost) e dalla quantità di concime da utilizzare.
   Trattamento È caratterizzato dal nome del prodotto da usare e dal tempo di carenza
   che deve essere indicativo per il coltivatore a capire quanto tempo deve passare
   prima di poter svolgere altre attività sulla coltivazione.
   Raccolta È caratterizzata da una quantità prevista (in kg) della raccolta e da una
   quantità effettiva (in kg) della raccolta.
   Un’attività è svolta da uno specifico coltivatore che lavora per il progetto a cui la colti-
   vazione è associata. Anch’essa ha un ciclo vita che si suddivide in tre fasi:
4. Pianificazione
5. Svolgimento
6. Completamento
   In particolare il proprietario può dare una scadenza ad un’attività che, sebbene non sia
   obbligatoria e l’attività può essere completata anche se la scadenza è passata, permette di
   tenere traccia di quando tale attività diventa imminente: il proprietario, usando questo
   meccanismo può eventualmente notificare i coltivatori.
   2.3.7 Le Notifiche
   Una notifica è un messaggio che il proprietario può inviare ai coltivatori associati ad
   un progetto. Le notifiche sono caratterizzate da un nome descrittivo dell’evento, una
   descrizione più o meno dettagliata dell’evento, un’urgenza e una data di invio.
   Le notifiche possono essere di due tipi:
   Notifiche di Eventi Sono notifiche che riguardano eventi che possono accadere durante
   il ciclo vita di un progetto, ad esempio un’anomalia o una carestia, e che quindi
   possono essere inviate in qualsiasi momento. Questo tipo di notifica può essere
   inviata ad uno o più coltivatori del progetto, a seconda di chi il proprietario vuole
   informare dell’evento.
   Notifiche di Attività Imminenti Sono notifiche che riguardano attività imminenti e
   che quindi possono essere inviate solo quando un’attività sta per diventare immi-
   nente. Questa tipologia di notifica è diretta al coltivatore che svolge l’attività ed è
   caratterizzata dai giorni mancanti alla scadenza dell’attività nel momento in cui la
   notifica viene inviata.
   9 di 63
   2 ANALISI DEL DOMINIO UninaBioGarden
   Riguardo all’urgenza della notifica, il proprietario può assegnare alla notifica un certo
   livello di urgenza:
   •Bassa
   •Media
   •Alta
   •Critica
