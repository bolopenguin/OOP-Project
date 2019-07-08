# OOP-Project
Progetto per il Corso di Programmazione ad Oggetti per l'appello di luglio 2019

## Descrizione

Il progetto svolto ha lo scopo di effettuare interrogazioni su un dataset CSV.
Tale dataset all'avvio dell'applicazione deve essere scaricato, e il link per scaricarlo è contenuto all'interno di un file di tipo JSON, il cui link è stato fornito dal professore.
Quindi l'applicazione all'avvio dovrà: scaricare il file JSON e farne il parsing in modo tale da trovare il link del file CSV, fatto ciò dovrà scaricare il file CSV e farne il parsing in modo tale da estrarre le informazioni del dataset, e infine dovrà gestire le interrogazioni fatte dall'utente implementate tramite API di tipo REST.

Il Dataset in questione tratta le  diverse voci di bilancio (budget item) di numerose banche europee, in particolare ogni elemento è caratterizzato da:
* lei_code: codice alfanumerico univoco di un'attività finanziaria, cioè è il codice per identificare una banca.
* nsa: codice che indica la nazione della banca a cui ci si sta riferendo
* period: periodo in cui la voce di bilancio a cui ci si riferisce è stata calcolata.
* item: codice numerico associato alla voce di bilancio, indica a cosa si riferisce la voce.
* label: descrizione in linguaggio naturale di cosa indica la voce di bilancio.
* amount: ammontare della voce di bilancio.
* n quarters: trimestre a cui ci si sta riferendo.

## Strumenti Utilizzati

* Eclipse 2018-12
* Postman
* Git
* Maven
* Spring Framework

## Diagrammi
Diagrammi utili per la compresione del progetto

### Diagramma dei casi d'uso

[Casi D'uso](https://github.com/bolopenguin/OOP-Project/blob/master/Casi%20D'uso.jpg)
