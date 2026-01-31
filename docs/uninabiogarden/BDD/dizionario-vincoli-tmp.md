
# Dizionario dei Vincoli
## Vincoli Attività

### Stato Iniziale e Finale
- **Stato Iniziale**: un attività appena creata deve essere nello stato PIANIFICATA
- **Stato Finale**: se lo stato diventa COMPLETATA o ANNULLATA ogni attributo non può essere più modificato. 

### Regole di Transizione

| Stato Originale | Stato Destinazione | Condizione                                             |
| :-------------- | :----------------- | :----------------------------------------------------- |
| **-**           | PIANIFICATA        |                                                        |
| PIANIFICATA     | IN_CORSO           | La coltivazione a cui è associata ha `stato == ATTIVA` |
| PIANIFICATA     | ANNULLATA          |                                                        |
| IN_CORSO        | COMPLETATA         |                                                        |
| IN_CORSO        | ANNULLATA          |                                                        |
| COMPLETATA      | **-**              |                                                        |
| ANNULLATA       | **-**              |                                                        |

### Coerenza Temporale delle Date
- `data_pianificazione`: non può essere modificata dopo la creazione dell'attività.
- `data_scadenza >= data_pianificazione`
- `data_inizio`: `NULL` finché l'attività non viene spostata nello stato `IN_CORSO`, dopodiché diventa immutabile. Deve valere che: `data_inizio >= data_pianificazione`.
- `data_fine`: `NULL` finché l'attività non viene spostata nello stato `COMPLETATA`, dopodiché diventa immutabile. Deve valere che: 
	- `data_fine >= data_pianificazione`
	- `data_fine >= data_inizio` se `data_inizio` non è `NULL`.
	
### Altri Vincoli
- tra le attività legate ad una coltivazione, il `titolo` deve essere unico.
- non è possibile modificare a quale coltivazione un'attività è associata

## Vincoli Coltivazione
### Stato Iniziale e Finale
- **Stato Iniziale**: una coltivazione appena creata deve avere `stato_salute = OTTIMO` e `stato_coltivazione = PIANIFICATA`.
- **Stato Finale**:  se `stato = CONCLUSA OR FALLITA OR ANNULLATA`, allora:
	- **Congelamento Attributi**: ogni attributo della coltivazione non può essere più modificato. 
	- **Annullamento Attività Rimanenti**: lo `stato` di tutte le attività associate tali che `attivita.stato == COMPLETATA OR ANNULLATA` deve essere impostato ad `ANNULLATA`.
	- **Fine Attività**: non è possibile pianificare ulteriori attività sulla coltivazione.

**Nota**
- Quando una coltivazione entra in uno dei suoi stati finali **tutte** le sue attività sono ***congelate***: lo stato di tutte le attività associate alla coltivazione, che non sono in uno stato terminale, diventa `ANNULLATO` e, per definizione dello stato finale di una attività, queste diventano immutabili. Inoltre alla coltivazione stessa non sarà più possibile associare nuove attività. 

### Regole di Transizione: `stato_coltivazione` 

| Stato Originale | Stato Destinazione | Condizione                                                              |
| --------------- | ------------------ | ----------------------------------------------------------------------- |
| -               | PIANIFICATA        |                                                                         |
| PIANIFICATA     | ATTIVA             | il progetto associato ha `stato == ATTIVO`                              |
| PIANIFICATA     | ANNULLATA          |                                                                         |
| ATTIVA          | CONCLUSA           | solo se esiste una attività **Raccolta** che è nello stato `COMPLETATA` |
| ATTIVA          | FALLITA            | lo stato di salute della coltivazione è `COMPROMESSO`                   |
| ATTIVA          | ANNULLATA          |                                                                         |
| ANNULLATA       | **-**              |                                                                         |
| CONCLUSA        | **-**              |                                                                         |
| FALLITA         | **-**              |                                                                         |

### Coerenza Temporale
- `data_creazione` deve essere inserita soltanto durante la creazione della coltivazione e il suo valore non sarà ulteriormente modificabile.
- `data_inizio` 
	- deve essere `NULL` se `stato == PIANIFICATA`
	- deve essere inserita soltanto durante la transizione di stato `PIANIFICATA --> ATTIVA` e non può essere successivamente modificata.
- Tutte le attività associate ad una coltivazione devono avere una `data_inizio` tale che: `attivita.data_inizio >= coltivazione.data_inizio`.
- Data una coltivazione Y e un attività X che è associata a Y: `Data Inizio Attività X​ <= Data Fine Attività X <= Data Fine Raccolta Y​`

### Regole di Transizione: `stato_salute` 

| Stato Originale                      | Stato Destinazione                                | Condizione                                                                                         |
| ------------------------------------ | ------------------------------------------------- | -------------------------------------------------------------------------------------------------- |
| -                                    | OTTIMO                                            |                                                                                                    |
| OTTIMO, STABILE, SOFFERENTE, CRITICO | OTTIMO, STABILE, SOFFERENTE, CRITICO, COMPROMESSO | La coltivazione deve trovarsi nello stato `ATTIVA` per poter modificare il proprio stato di salute |
| COMPROMESSO                          | **-**                                             |                                                                                                    |

**Nota**
 Lo stato `COMPROMESSO` è uno stato pozzo per la salute: una volta raggiunto, la transizione verso `FALLITA` è immediata e irreversibile. Si nota inoltre che una coltivazione può *fallire* solo se lo stato della coltivazione è `ATTIVA` e il suo stato di salute diventa `COMPROMESSO`. Una coltivazione può essere *annullata* se non è ancora *attiva*.

### Vincoli sulle Attività Associate

**Vincolo di Unicità Semina/Raccolta:** Data una coltivazione, non può essere inserita un'attività di semina (raccolta) se già esiste un'attività di semina (raccolta) in uno stato diverso da `ANNULLATA`.

**Vincolo di Sequenza Semina/Raccolta**: Si può iniziare l'attività di **Raccolta** solo se l'attività di **Semina** esiste e ha `stato = COMPLETATA`.

## Vincoli del Progetto
### Stato Iniziale e Finale
- **Stato Iniziale**: un progetto appena creato deve avere `stato = PIANIFICATO`.
- **Stato Finale**: se `stato = CONCLUSO OR FALLITO`, allora:
	- **Fine Coltivazioni**: non è possibile associare nuove coltivazioni al progetto.
	- **Congelamento Attributi**: tutti gli attributi del progetto non sono ulteriormente modificabili.
**Nota**
- Si fa notare che se un progetto è concluso o fallito non c'è bisogno di annullare le coltivazioni associate siccome queste o sono tutte annullate (progetto fallito in preparazione) o sono tutte terminate o fallite (progetto concluso o fallito). 

### Regole di Transizione

| Stato Originale | Stato Destinazione | Condizione                                                                                             |
| --------------- | ------------------ | ------------------------------------------------------------------------------------------------------ |
| **-**           | PIANIFICATO        |                                                                                                        |
| PIANIFICATO     | ATTIVO             |                                                                                                        |
| PIANIFICATO     | FALLITO            | solo se tutte le coltivazioni associate hanno `stato_coltivazione = ANNULLATA`.                        |
| ATTIVO          | CONCLUSO           | solo se tutte le coltivazioni associate hanno `stato_coltivazione = CONCLUSA OR FALLITA OR ANNULLATA`. |
| ATTIVO          | FALLITO            | solo se tutte le coltivazioni associate hanno `stato_coltivazione = CONCLUSA OR FALLITA OR ANNULLATA`. |
| CONCLUSO        | **-**              |                                                                                                        |
| FALLITO         | **-**              |                                                                                                        |

**Nota**
- Il progetto può fallire se il proprietario decide di annullarlo. Questo può avvenire se, per esempio, tutte le coltivazioni falliscono o sono annullate. Questo sistema però non dovrebbe essere automatizzato ma è il proprietario a sancire se il progetto è definitivamente fallito: si fa notare che ad un progetto in preparazione o attivo è possibile associare nuove coltivazioni.

### Coerenza Temporale delle Date
- `data_creazione` non può essere modificata successivamente alla creazione del progetto.
- `data_inizio` deve essere `NULL` se `stato = PIANIFICATO`. Il valore può essere inserito soltanto durante la transizione da `PIANIFICATO` ad `ATTIVO` e non può essere modificato successivamente. Deve valere che `data_inizio >= data_creazione`.
- `data_fine` deve essere `NULL` se `stato <> CONCLUSO OR FALLITO`. Il valore può essere inserito soltanto durante le transizioni terminali. Deve valere che: `data_fine >= data_creazione` e, se `data_inizio <> NULL`, `data_fine >= data_inizio`.

## Vincoli sul Lotto
- Il lotto può ospitare soltanto un progetto in stato non terminale per volta. 
- `id_proprietario` deve essere l'`id` di un utente tale che `utente.tipo == PROPRIETARIO`
- `data_registrazione` non può essere modificato dopo la creazione.
- `id_proprietario` non può essere modificato dopo la creazione.
- `id_lotto` non può essere modificato dopo la creazione.

## Vincoli su Orto
- `data_registrazione` deve essere inserito durante la creazione del lotto e non deve essere ulteriormente modificato.
- `id_proprietario` deve essere inserito durante la creazione dell'orto e non deve essere più modificato.
## Ricezione Notifiche
**Coerenza di Lettura**
Data un'istanza di relazione **riceve**, deve valere che:
    - Se `is_letta` è **FALSE**, allora `data_presa_visione` deve essere **NULL**.
    - Se `is_letta` è **TRUE**, allora `data_presa_visione` deve essere **NOT NULL**.

**Coerenza Temporale**
Data la `data_inizio` della notifica associata, deve valere che: `data_lettura` >= `data_invio`.

**Integrità Lettura**:
Una volta che `is_letta` passa a **TRUE**, non può più tornare a **FALSE**.

## Coltura
- `tempo_maturazione` deve essere un valore maggiore di 0 (considera metterlo nel dizionario degli attributi)
## Vincoli Utente

**Utente -- Proprietario**
- Un utente può possedere un lotto solo se `Utente.tipo == PROPRIETARIO`
- Un utente può gestire un orto solo se `Utente.tipo == PROPRIETARIO`
**Nota**
Un utente proprietario può possedere un lotto su un qualsiasi orto anche se lo stesso proprietario, ad esempio, gestisce altri orti. 

- Un utente può inviare una notifica solo se `Utente.tipo == PROPRIETARIO`
- Un utente può pianificare un progetto solo se `Utente.tipo == PROPRIETARIO`

**Utente -- Coltivatore**
- Un utente può svolgere un attività solo se `Utente.tipo == COLTIVATORE`
- Un utente può ricevere una notifica solo se `Utente.tipo == COLTIVATORE`

**Data Registrazione**
`data_registrazione` dell'utente deve essere inserita durante la creazione dell'utente e non può essere più modificata in seguito.
## Deprecated
- `data_inizio_prevista`: non può essere modificata se la data di inizio è diversa da NULL. Deve valere che `data_inizio_prevista >= data_pianificazione`.