# LaTeX Template per Documentazione BDD

Questo template LaTeX è stato creato per la documentazione del progetto UninaBioGarden.

## Struttura dei File

```
latex-template/
├── main.tex          # File principale LaTeX
├── README.md         # Questo file
└── compile.sh        # Script per compilare il documento (opzionale)
```

## Come Usare con TeXstudio

### 1. Aprire il Progetto

1. Apri TeXstudio
2. File → Apri → Seleziona `main.tex`
3. TeXstudio rileverà automaticamente tutte le dipendenze

### 2. Configurazione (Prima volta)

- Vai su: Opzioni → Configura TeXstudio
- Verifica che il compilatore predefinito sia `pdflatex` o `xelatex`
- Per questo template, `pdflatex` è sufficiente

### 3. Compilare il Documento

- Premi **F5** (Compila e Visualizza)
- Oppure usa il pulsante verde "Compila e Visualizza" nella toolbar
- Il PDF verrà generato nella stessa directory

### 4. Compilazione da Terminale (Alternativa)

```bash
cd docs/latex-template
pdflatex main.tex
pdflatex main.tex  # Esegui due volte per aggiornare i riferimenti
```

## Pacchetti Richiesti

Il template usa i seguenti pacchetti LaTeX (normalmente inclusi in una distribuzione TeX completa):

- **babel**: Supporto lingua italiana
- **geometry**: Margini e layout pagina
- **graphicx**: Inclusione immagini
- **hyperref**: Collegamenti ipertestuali
- **listings**: Codice sorgente formattato (SQL)
- **booktabs**: Tabelle professionali
- **longtable**: Tabelle su più pagine
- **fancyhdr**: Intestazioni e piè di pagina personalizzati

## Personalizzazione

### Modificare le Informazioni del Documento

Nel file `main.tex`, cerca la sezione `TITLE PAGE DATA` (circa riga 96) e modifica:

- Titolo
- Autori e matricole
- Anno accademico

### Aggiungere Immagini

Le immagini devono essere nella cartella `docs/uninabiogarden/asset/` (già configurata).

Per includere un'immagine:

```latex
\begin{figure}[H]
    \centering
    \includegraphics[width=0.9\textwidth]{nome-file.svg}
    \caption{Descrizione dell'immagine}
    \label{fig:etichetta}
\end{figure}
```

### Aggiungere Codice SQL

```latex
\begin{lstlisting}[style=sqlstyle]
CREATE TABLE NomeTabella (
    id SERIAL PRIMARY KEY,
    campo VARCHAR(100) NOT NULL
);
\end{lstlisting}
```

### Aggiungere Tabelle

```latex
\begin{table}[H]
\centering
\caption{Titolo Tabella}
\begin{tabular}{|l|l|p{6cm}|}
\hline
\textbf{Colonna 1} & \textbf{Colonna 2} & \textbf{Descrizione} \\
\hline
Valore 1 & Valore 2 & Descrizione lunga... \\
\hline
\end{tabular}
\end{table}
```

## Struttura del Documento

Il template è organizzato in sezioni:

1. **Introduzione**: Panoramica del sistema
2. **Analisi del Dominio**: Requisiti e glossario
3. **Schema Concettuale**: Diagrammi ER e UML
4. **Schema Ristrutturato**: Modifiche al modello
5. **Schema Logico**: Tabelle SQL
6. **Dizionario dei Dati**: Descrizione attributi
7. **Vincoli di Integrità**: Vincoli del database
8. **Query di Esempio**: Query SQL utili
9. **Implementazione**: Dettagli implementativi
10. **Conclusioni**: Riepilogo e sviluppi futuri

## Tips per LaTeX

### Caratteri Speciali

- `_` → `\_` (underscore)
- `%` → `\%` (percentuale)
- `#` → `\#` (cancelletto)
- `&` → `\&` (e commerciale)

### Riferimenti Incrociati

```latex
\label{fig:mia-figura}    % Etichetta
\ref{fig:mia-figura}       % Riferimento
```

### Note a Piè di Pagina

```latex
Testo\footnote{Nota a piè di pagina}
```

### Elenchi

```latex
% Elenco puntato
\begin{itemize}
    \item Primo punto
    \item Secondo punto
\end{itemize}

% Elenco numerato
\begin{enumerate}
    \item Primo punto
    \item Secondo punto
\end{enumerate}
```

## Troubleshooting

### Problema: Immagini non visualizzate

- Verifica che le immagini siano in `docs/uninabiogarden/asset/`
- Controlla che i nomi dei file siano corretti (case-sensitive su Linux)

### Problema: Errori di compilazione

- Leggi il log degli errori in TeXstudio (pannello in basso)
- Compila due volte per risolvere problemi di riferimenti
- Verifica che tutti i pacchetti siano installati

### Problema: Caratteri accentati non funzionano

- Assicurati che il file sia salvato in UTF-8
- Il template usa `\usepackage[utf8]{inputenc}` che dovrebbe gestirlo

## Risorse Utili

- [LaTeX Wikibook](https://en.wikibooks.org/wiki/LaTeX)
- [Overleaf Documentation](https://www.overleaf.com/learn)
- [CTAN - Pacchetti LaTeX](https://www.ctan.org/)
- [Detexify - Cerca simboli LaTeX](http://detexify.kirelabs.org/classify.html)

## Conversione da Markdown

Se hai già contenuti in Markdown, puoi convertirli con Pandoc:

```bash
pandoc input.md -o output.tex
```

Poi copia il contenuto generato nel template.
