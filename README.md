# OOP-Project
Progetto per il Corso di Programmazione ad Oggetti per l'appello di luglio 2019

## Descrizione

Il progetto svolto ha lo scopo di effettuare interrogazioni su un dataset CSV.
Tale dataset all'avvio dell'applicazione deve essere scaricato, e il link per scaricarlo è contenuto all'interno di un file di tipo JSON, il cui link è stato fornito dal professore.
Quindi l'applicazione all'avvio dovrà: scaricare il file JSON e farne il parsing in modo tale da trovare il link del file CSV, fatto ciò dovrà scaricare il file CSV e farne il parsing in modo tale da estrarre le informazioni del dataset, e infine dovrà gestire le interrogazioni fatte dall'utente implementate tramite API di tipo REST.

Il Dataset in questione tratta le  diverse voci di bilancio (budget item) di numerose banche europee, in particolare ogni elemento è caratterizzato da:

* **lei_code**: codice alfanumerico univoco di un'attività finanziaria, cioè è il codice per identificare una banca.
* **nsa**: codice che indica la nazione della banca a cui ci si sta riferendo
* **period**: periodo in cui la voce di bilancio a cui ci si riferisce è stata calcolata.
* **item**: codice numerico associato alla voce di bilancio, indica a cosa si riferisce la voce.
* **label**: descrizione in linguaggio naturale di cosa indica la voce di bilancio.
* **amount**: ammontare della voce di bilancio.
* **n quarters**: trimestre a cui ci si sta riferendo.

## Strumenti Utilizzati

* Eclipse 2018-12
* Postman
* Git
* Maven
* Spring Framework

## Diagrammi
Diagrammi utili per la compresione del progetto

### Diagramma dei Casi d'Uso

[Casi D'uso](https://github.com/bolopenguin/OOP-Project/blob/master/Casi%20D'uso.jpg)

### Diagramma delle Classi

[Classi](https://github.com/bolopenguin/OOP-Project/blob/master/Classi.jpg)

### Diagrammi delle Sequenze

[Avvio](https://github.com/bolopenguin/OOP-Project/blob/master/Avvio.jpg)

[Richiesta di Tutte le Statistiche](https://github.com/bolopenguin/OOP-Project/blob/master/Richieste%20tutte%20statistiche.jpg)

[Richiesta di una Statistica precisa](https://github.com/bolopenguin/OOP-Project/blob/master/Richieste%20Statistiche.jpg)

[Richiesta di un Filtro](https://github.com/bolopenguin/OOP-Project/blob/master/Richieste%20filtro.jpg)

## Struttura del Progetto

### Package
 - **europeBanks.spring**: al cui interno troviamo la classe `Application.java` contenente il main che permette l'avvio del programma.
 - **europeBanks.spring.controller**: al cui interno troviamo la classe `BankController.java` che serve per prendere le richieste fatte dall'utente, svolgerle e restituire il risultato
 - **europeBanks.spring.model**: al cui interno troviamo le classi `Budget.java` che modella il contenuto delle righe del dataset e `Metadata.java` che serve per modellare i metadati.
 - **europeBanks.spring.parser**: al cui interno troviamo le classi `ParserCSV.java` e `ParserJSON.java` che servono per scaricare e parsare i file CSV e JSON.
 - **europeBanks.spring.service**: al cui interno troviamo l'interfaccia `IBudgetService.java` che contiene le dichiarazioni dei metodi necessari per soddisfare le richieste fatte dall'utente e la classe `BudgetService.java` che implementa i metodi.
 - **europeBanks.spring.util**: al cui interno troviamo la classe astratta `FilterTools.java` che contiene metodi utili per effettuare le operazioni di filtraggio del dataset e la classe `Error.java` che ha il compito di restituire i messaggi di errori all'utente nel caso in cui siano state fatte richieste errate
 
 ### File
 - **pom.xml**: ha il compito di definire identità e struttura del progetto ed è utilizzato da Maven per reperire le dipendenze del progetto.
 - **manifest.yml**: utilizzato per definire la porta e il percorso dell'applicazione.
 
 ## Guida all'Utilizzo
 
 Nelle interrogazioni sostituire:
 - {proprietà} con una {proprietà_numerica} o {proprietà_stringa}
 - {proprietà_numerica} con: `period`, `item`, `amount`, `n_quarters`
 - {proprietà_stringa} con: `lei_code`, `nsa`, `label`
 - {valore} con un numero o una stringa a seconda di quale proprietà ci si stia riferendo
 - {operatore_logico} con: `and`, `or`, `not`, `in`, `nin` 
 - {operatore_condizionale} con: `<`, `<=`, `=`, `>`, `>=` 
 
 **Attenzione**: non seguire queste regole farà ottenere come risultato un'eccezione in cui verrà indicato anche il tipo di errore commesso
 
 Le interrogazioni al dataset possono essere di due tipi:
 
 ### - Statistiche
 
 - `http://localhost:8080/data`: Restituisce tutti i dati 
 
 - `http://localhost:8080/metadata`: Restituisce tutti i metadati 
 
 - `http://localhost:8080/stats?property={proprietà}`: Restituisce tutte le statistiche relative a una proprietà 
 
 ex. `http://localhost:8080/stats?property=amount`
 
 - `http://localhost:8080/stats/average?property={proprietà_numerica}`: Restituisce la media relativa a una proprietà
 
 ex. `http://localhost:8080/stats/average?property=n_quarters`
 
 - `http://localhost:8080/stats/sum?property={proprietà_numerica}`: Restituisce la somma relativa a una proprietà
 
 ex. `http://localhost:8080/stats/sum?property=amount`
 
 - `http://localhost:8080/stats/count?property={proprietà_numerica}`: Restituisce il conteggio degli elementi relativo a una proprietà 
 
 ex. `http://localhost:8080/stats/count?property=amount`
 
 - `http://localhost:8080/stats/max?property={proprietà_numerica}`: Restituisce il massimo relativo a una proprietà 
 
 ex. `http://localhost:8080/stats/max?property=amount`
 
 - `http://localhost:8080/stats/min?property={proprietà_numerica}`: Restituisce il minimo relativo a una proprietà 
 
 ex. `http://localhost:8080/stats/min?property=amount`
 
 - `http://localhost:8080/stats/devstd?property={proprietà_numerica}`: Restituisce la deviazione standart relativa a una proprietà 
 
 ex. `http://localhost:8080/stats/devstd?property=amount`
 
 - `http://localhost:8080/stats/unique?property={proprietà_stringa}`: Restituisce il conteggio degli elementi unici relativo a una proprietà 
 
 ex. `http://localhost:8080/stats/unique?property=nsa` 

### - Filtri

 - `http://localhost:8080/logicalFilter?field1={proprietà}&value1={valore}&field2={proprietà}&value2={valore}&operator={operatore_logico}`: Restituisce i dati filtrati secondo i valori inseriti e l'operatore scelto.
 **Attenzione**: alcuni operatori (not, in e nin) per fare il filtraggio si riferiscono a field1 e value1, quindi field2 e value2 saranno ignorati se presenti, gli altri operatori invece (and e or) necessitano anche field2 e value2. 
 Inoltre nell'in e nel nin si possono inserire più valori separati da una virgola. 
 
 ex. `http://localhost:8080/logicalFilter?field1=amount&value1=0&field2=nsa&value2=it&operator=and`
 
 ex. `http://localhost:8080/logicalFilter?field1=amount&value1=0&field2=nsa&value2=it&operator=or`
 
 ex. `http://localhost:8080/logicalFilter?field1=nsa&value1=cy&operator=not`
 
 ex. `http://localhost:8080/logicalFilter?field1=nsa&value1=IT,CY&operator=in`
 
 ex. `http://localhost:8080/logicalFilter?field1=nsa&value1=IT,CY&operator=nin`
 
 - `http://localhost:8080/conditionalFilter?operator={operatore_condizionale}&field={proprietà_numerica}&value={valore}`: Restituisce i dati filtrati secondo i valori inseriti e l'operatore scelto.
 
 ex. `http://localhost:8080/conditionalFilter?operator=<&field=n_quarters&value=3`
 
 ex. `http://localhost:8080/conditionalFilter?operator=<=&field=n_quarters&value=3`
 
 ex. `http://localhost:8080/conditionalFilter?operator==&field=n_quarters&value=3`
 
 ex. `http://localhost:8080/conditionalFilter?operator=>=&field=n_quarters&value=3`
 
 ex. `http://localhost:8080/conditionalFilter?operator=>&field=n_quarters&value=3`
 
 ## Autori
 - Damiano Bolognini: [bolopenguin](https://github.com/bolopenguin)
 - Francesco Tontarelli: [fraton](https://github.com/fraton)

