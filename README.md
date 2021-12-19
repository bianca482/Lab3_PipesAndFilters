# Systemarchitekturen Lab 3 (Pipes and Filters)

## Allgemeines
Github Repository verfügbar unter: https://github.com/bianca482/Lab3_PipesAndFilters

Zum Starten der Applikation wird Java Version 11 benötigt.
In der Klasse Main wurde die Größe der Applikation (VIEW_WITH + VIEW_HEIGHT) leicht abgeändert.

Beim Starten aus der IDE muss zum Wechseln zwischen der Push- und Pull-Pipeline muss der Wert von USE_PUSH_PIPELINE abgeändert werden.

Ansonsten wurden auch zwei jar-Files erstellt, welche mittels Kommandozeile wie folgt gestartet werden können:
1. Wechsel in Verzeichnis in dem sich das Jar-File befindet: *pushd <directory>*
2. Programm starten: *java -p  "<java_fx_path>" --add-modules javafx.controls,javafx.fxml -jar pushPipeline.jar|pullPipeline.jar* (wobei <java_fx_path> mit dem JAVA FX-Installationspfad des eigenen PCs angepasst werden muss.)

## Architektur
- Architektur erklären (von Push & Pull Pipeline) ->
    - Funktionsweise, (erledigt)
    - welche Interfaces,
    - Verschachtelung (müssen Nachfolger oder Vorgänger angegeben werden müssen) (erledigt)
- Screenshot beider Pipelines (erledigt)

### Push Pipeline
Bei der Push Pipeline pusht die ModelSource zunächst alle Faces auf die nachfolgende Pipe, welche
wiederrum dem nachfolgenden Filter die Daten weitergibt. Es wurde hierbei gleich eine Liste aller
Faces übergeben, damit später das Depth Sorting leichter umgesetzt werden kann. Nach dem Depth
Sorting wird nur noch ein einzelnes Face (später auch als Pair mit der Farbe) weitergegeben.
Ansonsten ist der Ablauf immer derselbe -> die Filter führen ihre Transformationen und Berechnungen
durch, pushen die Ergebnisse auf die Pipe, welche die Daten wiederrum an den nächsten Filter weiterleitet.
Der ganze Prozess in Gang gesetzt, indem die Source als aktives Element die Faces auf die 
nachfolgende Pipe pushed.

Das schlussendliche Ergebnis sieht folgendermaßen aus:

<img src="resources/pictures/PushPipeline_left.png" alt="Push Pipeline" />

Weitere Bilder zum Vergleich der Push- und Pull Pipeline sind unter Lab3_PipesAndFilters\resources\pictures ersichtlich.

### Pull Pipeline
Im Fall der Pull Pipeline ist die ModelSink das einzige aktive Element. Diese triggert durch
das Pullen der vorgehenenden Pipe den ganzen Prozess. 
Die Filter haben nun alle einen Pipe-Vorgänger und in einer Pipe wird der Filter gespeichert, 
von welcher die Pipe der Input erhält (Filter-Vorgänger). Ein Filter pullt nun die Daten von der vorgehenden
Pipe, welche wiederrum von seinem vorgehenden Filter die Daten pullt und dem aufrufenden Filter zurückgibt. 

Das Resultat des Renderings der Pull Pipeline sieht folgendermaßen aus:
<img src="resources/pictures/PullPipeline_left.png" alt="Pull Pipeline" />
