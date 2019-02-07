# JasperReports-Custom-Data-Source
Eine Java-Implementierung einer Custom-Data-Source für JasperReports um Daten von Projektrons BCS Server in Reports einzubinden

## Das Produkt builden
Um die Custom-Data-Source zu builden, führen Sie den Befehl `gradle shadowJar` aus. Dies erzeugt (unter anderem) einen `build/libs`-Ordner, welcher eine einzige `.jar`-Datei beinhaltet. Diese Datei enthält sowohl die Data-Source, als auch all seine Abhängigkeiten.

## Die Data-Source in JasperSoft Studio einbinden
### Einen Data-Adapter definieren
1. Öffnen Sie den `Data Adapter Wizard` (einen neuen Data-Adapter erstellen)
1. Wählen Sie `Custom implementation of JRDataSource`
1. Geben sie `ProjektronDataSource` im `Factory Class`-Textfeld ein
1. Ins Feld für die statische Methode schreiben Sie `getDataSource`
1. Klicken Sie nun den `Add`-Knopf und wählen Sie die zuvor erstellte `.jar`-Datei aus
1. Klicken Sie auf `Finish` (Sie können vorher auch noch auf `Test` drücken)
### Ein Data-Set definieren
1. Erstellen Sie ein neues Data-Set. Wählen Sie dabei den gerade erstellten Data-Adapter aus der Combobox aus
1. Rechtsklicken sie den Data-Set- oder den Report-Wurzelknoten und fügen Sie die gewünschten Felder hinzu (zum Beispiel `effortRegularTimeCosts` mit `java.lang.String`)
1. Um sich die Daten voranzeigen zu lassen, klicken sie im `Data preview`-Reiter (unten) auf `Refresh Preview Data`

## Das Studio Debuggen
Um die Data-Source zu debuggen können Sie JasperSoft Studio über die Quelldateien selbst bauen und dem entsprechen debuggen. Dazu können Sie folgende Schritte durchführen:
1. Zip Datei “TIB_js-studiocomm_6.6.0_sources.zip” von https://sourceforge.net/projects/jasperstudio/files/DevelopersInfo/bugOracle/ runterladen und extrahieren
1. Den Sourcecode von der JasperSoft-Library https://github.com/TIBCOSoftware/jasperreports runterladen
1. Den Anweisungen der offiziellen Dokumentation von https://community.jaspersoft.com/documentation/tibco-jaspersoft-studio-source-build-guide/v630/tibco-jaspersoft-studio-source-build folgen und jeweils die extrahierten Ordner aus Schritt 1 wählen.
1. Eine Möglichkeit Breakpoints zu setzen:
    1. In Custom Data Source eine bestimmte Exception einfügen
    1. In Eclipse einen Exception Breakpoint auf diese Exception setzen
    1. Beim Debugging im Data Adapter Dialog auf Test klicken
    1. Eclipse sollte die Source Datei des Adapters anzeigen
    1. Von hier aus können ganz normale Zeilenbreakpoints gesetzt werden
    1. Die Zeile aus Schritt i wieder löschen
1. Wann immer Eclipse die Source Dateien nicht findet, einfach auf den gegebenen Knopf klicken und einen der Ordner angeben, in den der Code aus Schritten 1 und 2 heruntergeladen wurden
