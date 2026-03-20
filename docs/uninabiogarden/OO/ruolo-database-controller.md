PATTERN DAO
Il pattern DAO (Data Access Object) è un pattern architetturale che si occupa di separare la logica di accesso ai dati dalla logica di business dell'applicazione. In questo modo, si favorisce una maggiore modularità e manutenibilità del codice, poiché le classi DAO si occupano esclusivamente di gestire l'accesso al database, mentre le classi di controllo e di business logic possono concentrarsi sulle funzionalità specifiche dell'applicazione senza doversi preoccupare dei dettagli di accesso ai dati.

DATABASE CONTROLLER
Come già accennato in precedenza durante la descrizione del ruolo e del funzionamento del MainController, questo controller ha il ruolo di offrire accesso alle varie classi DAO che forniscono il punto di accesso al database.

LA CLASSE DATABASE
Questa classe particolare permette di connettersi al database. Essa essenzialmente è un singleton, quindi è facilmente accessibile a tutte le classi DAO al bisogno. In particolare, lo scopo fondamentale di questa classe è di aprire una connessione con il database. Al suo interno possiede tutti i parametri per iniziare la connessione come l'url del database, il nome utente e la password. Espone quindi un unico metodo pubblico, oltre a quello per accedere alla sua istanza, che è getConnetion() e che appunto è il cuore di questa classe e la base di tutte le classi DAO.
