#!/bin/bash

# Script per compilare il documento LaTeX
# Usage: ./compile.sh

echo "======================================"
echo "Compilazione Documentazione OO"
echo "======================================"
echo ""

# Controllo se pdflatex è installato
if ! command -v pdflatex &> /dev/null; then
    echo "ERRORE: pdflatex non trovato!"
    echo "Installa una distribuzione LaTeX (es: texlive-full)"
    exit 1
fi

# Prima compilazione
echo "Prima compilazione..."
pdflatex -interaction=nonstopmode main.tex > /dev/null

# Seconda compilazione (per riferimenti e TOC)
echo "Seconda compilazione (aggiornamento riferimenti)..."
pdflatex -interaction=nonstopmode main.tex > /dev/null

# Pulizia file ausiliari
echo "Pulizia file temporanei..."
rm -f *.aux *.log *.out *.toc *.lof *.lot

echo ""
echo "======================================"
echo "Compilazione completata!"
echo "Output: main.pdf"
echo "======================================"

# Apri il PDF se disponibile
if command -v xdg-open &> /dev/null; then
    echo "Apertura PDF..."
    xdg-open main.pdf &
elif command -v evince &> /dev/null; then
    echo "Apertura PDF..."
    evince main.pdf &
fi
