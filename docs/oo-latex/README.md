# LaTeX - Documentazione OO UninaBioGarden

Questo documento LaTeX contiene la documentazione orientata agli oggetti del progetto
UninaBioGarden: scelte architetturali, paradigma EBC, pattern DAO, e struttura dell'interfaccia
grafica JavaFX.

## Struttura dei File

```
oo-latex/
├── main.tex          # File principale LaTeX
├── compile.sh        # Script per compilare il documento
├── .gitignore        # File da ignorare in git
└── README.md         # Questo file
```

Le immagini (logo universitario, diagrammi) sono cercate in `../uninabiogarden/asset/`.

## Come Compilare

### Con Make (consigliato)

Dalla radice del progetto:

```bash
# Compilare il documento
make oo-latex-compile

# Pulire i file ausiliari
make oo-latex-clean
```

### Con lo script

```bash
cd docs/oo-latex
chmod +x compile.sh
./compile.sh
```

### Con pdflatex manualmente

```bash
cd docs/oo-latex
pdflatex main.tex
pdflatex main.tex   # seconda volta per TOC e riferimenti
```

### Con TeXstudio

1. Apri TeXstudio
2. File → Apri → Seleziona `main.tex`
3. Premi **F5** (Compila e Visualizza)

## Inserire i Diagrammi

La documentazione contiene dei placeholder per tre diagrammi UML:

- **Diagramma delle Classi Entity** — sostituire la `\fbox` con:
  ```latex
  \includegraphics[width=0.95\textwidth]{diagramma-classi-entity.pdf}
  ```
- **Diagramma delle Classi DAO** — sostituire la `\fbox` con:
  ```latex
  \includegraphics[width=0.95\textwidth]{diagramma-classi-dao.pdf}
  ```
- **Diagramma delle Classi UI** — sostituire la `\fbox` con:
  ```latex
  \includegraphics[width=0.95\textwidth]{diagramma-classi-ui.pdf}
  ```

Inserire i file PDF dei diagrammi nella cartella `../uninabiogarden/asset/`.

## Adattare la Pagina del Titolo

Aggiornare in `main.tex` le seguenti voci:

- `Prof. [Nome Cognome]` → nome del docente del corso OO
- Screenshot/mockup dell'interfaccia nella sezione 3
