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

# Sezione 3: Appunti sulle scelte di modellazione EER

# Orto come composizione di lotti

Si è deciso di modellare l'orto come una composizione di lotti: un lotto non ha senso di esistere nella piattaforma che si sta definendo se non è associato ad un orto. Nel sistema infatti vengono rappresentati degli orti urbani al cui interno un proprietario può avere dei lotti e sui quali può pianificare progetti. Per tanto il legame che vincola un lotto ad un orto è quello della composizione in cui un lotto non può esistere senza orto ma è vero il contrario.

Inoltre questo permette di modellare in modo più elegante l'indirizzo di un lotto: siccome tutti i lotti fanno parte di un orto essi automaticamente ereditano l'indirizzo dell'orto a cui appartengono, e questo è più realistico in quanto un lotto non ha un indirizzo proprio ma è identificato all'interno di un orto.

Siccome i lotti sono caratterizzati da una estensione, un orto può essere caratterizzato da una estensione totale che è data dalla somma delle estensioni dei lotti che lo compongono, per questo si è scelto di aggiungere l'attributo derivato "estensione totale" all'orto.

# Lotto come entita debole

Si è scelto di modellare il lotto come entità debole siccome questo non possiede un identificatore proprio. Un lotto ha un codice lotto che lo identifica all'interno di un orto ma nulla di più. Si è ritenuto pertanto che fosse naturale che un lotto come entità debole avesse il codice lotto come chiave parziale ma fosse identificato completamente tramite la relazione identificante con l'orto di cui fa parte.

# Coltivazione come entità debole

Si è scelto di modellare la coltivazione come entità debole siccome non è caratterizzata da alcun attributo che la identifichi univocamente: una coltivazione, per sua natura, è identificata univocamente dal progetto a cui è associata e dalla coltura che rappresenta. Tra le coltivazioni associate allo stesso progetto è la data di inizio tra queste che permette di distinguerle e quindi funge da chiave parziale: ad esempio un progetto può avere due coltivazioni di pomodori ma che iniziano in due date diverse.

# Attività come entità debole

Si è scelto di modellare l'attività come entità debole siccome questa non è caratterizzata da alcun attributo che la identifichi univocamente: un attività, per sua natura, è identificata univocamente dalla coltivazione a cui è associata e dal nome dell'attività. Tra le attività associate alla stessa coltivazione è il nome dell'attività che permette di distinguerle e quindi funge da chiave parziale: ad esempio una coltivazione può avere due attività di irrigazione ma che hanno nomi diversi (ad esempio "irrigazione 1" e "irrigazione 2").

# Notifica come entità debole

Si è scelto di modellare la notifica come entità debole siccome questa non è caratterizzata da alcun attributo che la identifichi univocamente: una notifica, per sua natura, è identificata univocamente dal progetto a cui è associata e dalla data di invio. Tra le notifiche associate allo stesso progetto la data di invio funge da chiave parziale che permette di distinguerle: ad esempio un progetto può avere due notifiche inviate in due date diverse.

# Gerarchie

# utente gerarchia totale

Si è scelto di rendere la specializzazione di utente totale e disgiunta, non permettendo dunque che vi siano utenti generici nel sistema. Questo deriva dal modo stesso in cui il dominio è stato compreso: si sono soltanto due tipologie di utenti che sono il proprietario e il coltivatore, qualsiasi altro utente non avrebbe senso in tale schema.

# attivita gerarchia totale

Si è scelto di rendere la specializzazione di attività totale e disgiunta, non permettendo dunque che vi siano attività generiche nel sistema. Si è scelto di evitare di permettere delle attività generiche e di restringere le possibilit attività che possono essere pianificate su una coltivazione. Il motivo è puramente una questione di complessità in quanto si è ritenuto non necessario gestire la presenza di attività generiche.

# notifica gerarchia parziale

Per la notifica invece si è scelto di optare per una specializzazione parziale siccome una notifica in se rappresenta un evento sul progetto come previsto dal dominio, ma può specializzarsi in una notifica di un attività imminente.

# decisioni sul ristrutturato

# chiavi surrogate

si è scelto di introdurre delle chiavi surrogate (id) per tutte le entità del sistema, anche per quelle che avevano già una chiave naturale. Questo è stato fatto per semplificare la gestione delle chiavi primarie e delle chiavi esterne, evitando di dover gestire chiavi primarie composte e chiavi esterne che fanno riferimento a più campi. Inoltre, l'introduzione di chiavi surrogate permette di avere un identificatore univoco per ogni entità, indipendentemente dagli attributi che la caratterizzano, e questo è particolarmente utile in caso di modifiche future al modello dei dati.

# ridondanze

- si svolge su (coltivazione e lotto)
  Questa relazione è stata rimossa siccome si è ritenuta ridondante: è possibile risalire al lotto su cui si svolge una coltivazione tramite il progetto a cui la coltivazione è associata.

- si occupa di (attivita e coltura)
  Questa relazione è stata rimossa siccome si è ritenuta ridondante: è possibile risalire alla coltura relativa all'attività tramite la coltivazione a cui l'attività è associata.

- lavora su (coltivatore e lotto)
  Questa relazione è stata rimossa siccome si è ritenuta ridondante: è possibile risalire al lotto su cui lavora un coltivatore tramite la relazione "lavora_per" che un coltivatore ha con progetto.

# eliminazione gerarchie

# gerarchia attivita

Si è optato per la strategia "una tabella per entità". Di seguito i motivi di questa scelta:

- siccome ci sono molte sottoclassi di attività e ognuna di queste ha diversi attributi, si evita di avere una tabella con molti campi nulli, con conseguente utilizzo più efficiente della memoria. Di conseguenza la strategia "una tabella per gerarchia" è stata scartata.
- si è ritenuto che fosse necessario una maniera veloce per poter fare delle query e recuperare tutte le informazioni generali di entità anche senza le informazioni specifiche dei sottotipi. Di conseguenza la strategia "una tabella per sottotipo" è stata scartata.

# gerarchia utente

Al contrario della gerarchia di attività, per la gerarchia di utente si è optato per la strategia "una tabella per gerarchia". Di seguito i motivi di questa scelta:

- in questo caso le sottoclassi di utente sono prive di attributi specifici e quindi è risultato naturale utilizzare questa gerarchia e introdurre un campo "tipo" ad utente per distinguere tra le due tipologie di utenti.

# gerarchia notifica

Come per utente, il sottotipo di notifica aveva soltanto un campo e dunque la soluzione più naturale è stata quella di utilizzare la strategia "una tabella per gerarchia" e introdurre un campo "tipo" a notifica per distinguere tra le due tipologie di notifica.
