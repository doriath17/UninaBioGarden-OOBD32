#

La piattaforma UninaBioGarden è un sistema software progettato per la gestione di orti urbani condivisi. Il sistema è utilizzato principalmente da due tipologie di utenti: i proprietari dei lotti su cui si svolgono le coltivazioni e i coltivatori che svolgono le diverse attività necessarie a tali coltivazioni. Lo scopo principale della piattaforma è fornire un ambiente di gestione sia per i proprietari che per i coltivatori.

# Il ruolo del proprietario

Il proprietario può registrare un orto urbano condiviso ed eventualmente andare a registrare dei lotti, da egli posseduti, al suo interno. La presenza dei lotti è fondamentale alla pianificazione di un progetto in quanto questo prende luogo su un lotto specifico. Il proprietario infatti deve essere in grado di pianificare un progetto specificando su quale lotto questo si svolge. Inoltre il proprietario deve essere in grado di notificare i coltivatori del progetto di eventi che riguardano il progetto, come ad esempio un anomalia o una carestia, o che riguardano attività imminenti, ad esempio una irrigazione programmata per il giorno dopo.

# L'orto

Il sistema prevede la gestione di orti urbani condivisi. Un proprietario è l'utente che può registrare un orto nel sistema ma non ne detiene la proprietà: una volta registrato anche altri proprietari potranno registrare i propri lotti nell'orto. Un orto è caratterizzato da un nome, un indirizzo e dalla data in cui esso è registrato.

# Il lotto

Un lotto è un'area fisica all'interno di un orto urbano condiviso su cui si svolgono le coltivazioni di un certo progetto. Un lotto è caratterizzato da un'estensione (in metri quadrati) e da una codice indetificativo del lotto, internamente all'orto urbano in cui si trova (ad esempio il lotto 017A all'interno di un certo orto). Un lotto è associato ad un proprietario, che è colui che lo ha registrato. Un lotto può ospitare più progetti del proprietario che lo ha registrato ma soltanto un progetto attivo per volta.

# Il progetto

Il progetto è l'entità centrale del sistema. Esso, come anticipato precedentemente, occupa uno dei lotti del proprietario che lo ha pianificato. Un progetto è caratterizzato da un nome, una descrizione, una data di inizio e una data di fine e può trovarsi in due stati differenti a seconda del suo ciclo vita: attivo o concluso. Come già anticipato un progetto è pianificato da un proprietario su uno dei suoi lotti, e questo è fondamentale per la pianificazione del progetto stesso. Un progetto infatti non può essere pianificato se non è associato ad un lotto, e questo è necessario per definire lo spazio fisico in cui si svolgeranno le coltivazioni del progetto. Un progetto è attivo quando è stato pianificato ma non è ancora concluso, e in questa fase è possibile programmare e completare tutte le attività che caratterizzano le coltivazioni associate al progetto. Quando invece un progetto è concluso, non è più possibile programmare o completare nessuna attività associata al progetto.
Diversi coltivatori possono essere associati ad un progetto e questo definisce l'insieme di persone che potranno essere selezionate per lo svolgimento delle attività. Come si vedrà in dettaglio in seguito, ad un progetto si possono associare una o più coltivazioni di colture specifiche e ognuna di queste è a sua volta caratterizzata dall'insieme di attività che ne definiscono il ciclo vita.

# La coltivazione

Un progetto può prevedere una o più coltivazioni, ognuna caratterizzata da una specifica coltura presente nel catalogo delle colture. Una coltivazine è caratterizzata da una data di inizio e di fine (si veda in seguito nella sezione sul ciclo di vita della coltivazione), uno stato di salute (ottimo, stabile, sofferente, critico e compromesso) e uno stato che ne descrive il ciclo vita. Le coltivazioni infatti, come il progetto, hanno un loro ciclo vita che ruota intorno alle attività che la caratterizzano e in particolare allo svolgimento dell'attività di raccolta che ne sancisce la fine:

- una coltivazione si può definire attiva se non non è ancora iniziata la fase di raccolta, e qui è possibile programmare e completare tutte le attività che poi porteranno finalemente alla raccolta.
- quando inizia la raccolta, la coltivazione entra in una fase in cui è possibile solo completare l'attività di raccolta
- infine, quando la raccolta è completata, la coltivazione è conclusa e non è più possibile programmare o completare nessuna attività.

# Il catalogo delle colture

Il sistema deve permettere di registare delle colture che possono essere coltivate nei progetti. Ogni coltura è caratterizzata da un nome, una descrizione, un tempo di maturazione previsto (in giorni). Il catalogo delle colture è fondamentale per la pianificazione di un progetto in quanto quando si pianifica un progetto è necessario specificare quali colture si vogliono coltivare e quindi quali coltivazioni si vogliono creare.

# Le attività

Come detto,quando una coltivazione è attiva è possibile programmare e completare tutte le attività che la caratterizzano. Ci sono diverse tipologie di attività che è possibile programmare:

- semina: una semina è caratterizzata dalla quantità di semi disponibili, e dalla profondità a cui devono essere piantati i semi.
- irrigazione: è caratterizzata dalla quantità di acqua da utilizzare e dal metodo da usare per irrigare. I metodi di irrigazione possono essere: a goccia, a pioggia, a scorrimento, manuale e nebulizzazione.
- concimazione: è caratterizzata dalla tipologia di concime da usare (organico, minerale o compost) e dalla quantità di concime da utilizzare.
- trattamento: è caratterizzato dal nome del prodotto da usare, il tempo di carenza che deve essere indicativo per il coltivatore a capire quanto tempo deve passare prima di poter svolgere altre attività sulla coltivazione.
- raccolta: è caratterizzata da una quantità prevista (in kg) della raccolta, e da una quantità effettiva (in kg) della raccolta.

Un attività è svolta da uno specifico coltivatore che lavora per il progetto a cui la coltivazione è associata. Anch'essa ha un ciclo vita che si suddivide in tre fasi:

- pianificazione
- svolgimento
- completamento

In particolare il proprietario può dare una scadenza ad un attività che, sebbene non sia obbligatoria e l'attività può essere completata anche se la scadenza è passata, permette di tenere traccia di quando tale attività diventa imminente: il proprietario, usando questo meccanismo può eventualmente notificare i coltivatori.

# Le notifiche

Una notifica è un messaggio che il proprietario può inviare ai coltivatori associati ad un progetto. Le notifiche sono caratterizzate da un nome descrittivo dell'evento, una descrizione più o meno dettagliata dell'evento, un urgenza (si veda più avanti) e una data di invio.

Le notifiche possono essere di due tipi:

- notifiche di eventi: sono notifiche che riguardano eventi che possono accadere durante il ciclo vita di un progetto, ad esempio un anomalia o una carestia, e che quindi possono essere inviate in qualsiasi momento. Questo tipo di notifica può essere inviata ad uno o più coltivatori del progetto, a seconda di chi il proprietario vuole informare dell'evento.
- notifiche di attività imminenti: sono notifiche che riguardano attività imminenti (si veda la sezione precedente dove si parla della scadenza delle attività) e che quindi possono essere inviate solo quando un attività sta per diventare imminente. Questa tipologia di notifica è diretta al coltivatore che svolge l'attività ed è caratterizzata da i giorni mancanti alla scadenza dell'attività nel momento in cui la notifica viene inviata.

Il sistema deve tenere traccia dello stato di lettura di una notifica da parte del coltivatore, e questo è fondamentale per assicurarsi che i coltivatori siano informati e permette, a livello applicativo, di distinguere tra notifiche lette e non lette, ad esempio per mostrare un badge di notifica non letta nell'interfaccia utente, nonché per fornire un criterio di urgenza alle notifiche da mostrare.
Riguardo appunto all'urgenza della notifica, il proprietario può assegnare alla notifica un certo livello di urgenza:

- bassa
- media
- alta
- critica

# Il ruolo del coltivatore

Nel sistema il coltivatore è un entità abbastanza passiva, in quanto non può pianificare un progetto, una coltivazione, un'attività e non può inviare notifiche. Il coltivatore tuttavia è fondamentale per lo svolgimento generale dei progetti perché è colui che è responsabile delle attività che vi sono assegnate e lui ne può gestire il ciclo di vita.
