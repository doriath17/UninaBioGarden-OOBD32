la struttura della documentazione:

- intro
  - descrizione del dominio: usa analisi-dominio.md
  - utilizzo del paradigma entity-boundary-control: usa paradigma-entity-boundary-control.md
  - scelta di javafx per l'interfaccia grafica: usa scelta-javafx.md
- modellazione
  - diagramma delle classi entity: inserisci il pdf del diagramma delle classi entity attualmente non presente
    - ruolo del MainController: usa ruolo-main-controller.md
  - diagramma delle dao: usa il diagramma delle classi dao attualmente non presente
    - ruolo del DatabaseController: usa ruolo-database-controller.md (qui si parla anche della classe DATABASE e del pattern DAO)
  - diagramma delle classi UI
    - il ruolo di UIController
    - come questo utilizza il MainController per accedere ai dati e aggiornare l'interfaccia
    - la scelta di non usare le dto e specialmente come viene gestita (validate)
- eventuali mockup dell'interfaccia grafica o screenshot dell'applicazione
