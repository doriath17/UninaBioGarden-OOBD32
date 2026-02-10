bdd-docs: 
	cd docs/uninabiogarden/BDD && pandoc "Documentazione-BDD.md" \
    -o Documentazione-BDD.pdf \
    --pdf-engine=xelatex \
    --toc \
    -N \
    -V geometry:margin=2cm \
    -V colorlinks=true \
    -V lang=it-IT
 

dizionario-vincoli:
	cd docs/uninabiogarden/BDD && pandoc "dizionario-vincoli-tmp.md" \
    -o dizionario-vincoli-tmp.pdf \
    --pdf-engine=xelatex \
    --toc \
    -N \
    -V geometry:margin=2cm \
    -V colorlinks=true \
    -V lang=it-IT

build:
	cd uninabiogarden && mvn clean package

run:
	cd uninabiogarden && mvn javafx:run

psql-run:
	psql -U ubg_user -d uninabiogarden
