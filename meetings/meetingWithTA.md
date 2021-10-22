## Möte 1

Pitcha projektet <br>
Fighter spel <br>
Våra features <br>
Kravspec<br>


Vi ska föra protokoll med varje möte. Vi ska ha två möten varje vecka och sen ett möte med TA (Carl Bergman). Det finns en mall på canvas. Vi ska även lägga upp det på github så att CB kan kolla på det.

Han tycker att det låter som en bra idé men kanske att det är ett ganska stort projekt. Han tror att GUI:t kommer vara det svåraste och mest tidskrävande att göra. Han tycker att vi borde börja med kraven och ta önskemålen sist. Han tycker inte att något av det verkar supersvårt.

Vi måste vara tydliga med vad vi har gjort själva, om vi har snott kod från nätet ska vi säga att vi har snott det och om vi gjort lite ändringar ska vi säga det också.



## Möte 2
#### Kolla på vår domain model, är den nice eller behöver den vara mer detaljerad? <br>
Den behöver inte alls vara detaljerad med metoder eller variabler osv. Han funderar på om den är för specifika, om vi bör ta bort några klasser från domänmodellen. Gör den inte mer detaljerad iaf

#### Kan visa skisser + figma
Han tyckte de såg bra ut med skissarna och figman osv.<br>

####När ska man göra user stories till tasks
försök skriva tasks direkt och sen får man ändra dem allteftersom programmet utvecklas.

####Hur mycket ska det finnas på det körbara programmet
Han ska fråga när man ska visa upp det för han visste inte det än. Det ska va någonting som går att köra och visar vart programmet är på väg.

Han fick access till Trello.


####Är storleken på projektet något som bedöms? 
Man ska inte ha för litet iaf men det ligger mer fokus på OOP delen såsom patterns och principer osv.


Vi kan ställa frågor till CB i första hand lite närsom om det är något som vi undrar om.
Bra att fokusera på designmodellen

## Möte 3
####Design modellen
Tycker att den ser bra ut och vi diskuterade om IFighter skulle finnas eller om vi skulle ta bort den. Egentligen kanske vi inte behöver den då Fighter ska vara immutable och inte ha några subklasser alls. Vi bestämde att den borde finnas då det kan vara “future-proof”.
####Tasks
Vi kikar på våra tasks tillsammans och kollar på om vi gjort rätt med att tasksen ligger under User storiesen som en “checklist”. Carl tycker att det kändes rimligt att ha det så och att det såg bra ut. Vi undrade också om det var för specifika tasks då på en föreläsning hade examinatorn väldigt generella tasks. Det var lite oklart men vi hoppas att det löser sig.
####Är vi i fas?
Vi har en bra grund med designmodellen och har smått börjat programmera. Vi tänker att vi håller på med designmodellen samtidigt som koden och uppdaterar lite allt eftersom när vi behöver det. Vi har inga views eller controllers direkt i designmodellen än och frågan är hur lång tid det kommer ta att designa och implementera.


## Möte 4
####RAD/SDD
Fokuset borde la ligga på proggen och dokumenten kan man skriva vid sidan typ samtidigt. SDD är ju väldigt dependent av proggen/projektet. UML och domän ska ligga i SDD.


####Generell update om vad vi gjort
Vi berättade lite om vad vi gjort denna veckan. Vi har ju diskuterat MVC och haft problem och därmed hade ett möte med Pelle så att vi fick fram typ en liten lösning. Vi har ju gjort tasks och diskuterat dem så att alla är med på notan. Vi har påbörjat smått på RAD genom att sätta upp strukturen i Overleaf.


## Möte 5
####Liten recap över vad vi gjort från förra mötet
Vi visade programmet<br>
Vi har inte alls gjort mycket tester så att fokuset ligger på testerna just nu.<br>
Han tyckte att vi kommit superlångt och undrade om vi uppdaterat designmodellen osv. Det har vi inte riktigt gjort än.<br>
Vi kommer behöva uppdatera den inför RAD och SDD peer review.<br>




## Möte 6
####Vad tycker du om vår RAD än så länge?
Han tyckte den såg okej ut. Inga större problem.

####SDD?
Inte mycket att säga här heller



## Möte 7
Vi visade vad vi gjort än så länge och han tycker att vi är “on track”. Vi visade programmet, RAD och vår SDD. Han berättade att han hade kikat på vem som gjort vilken andel av programmet och i nuläget har Elias och Vincent en del mer än resten men Kevin och Dadi har jobbat mycket med attackerna som inte var med i Carls kik på programmet.

## Möte 8

####om presentationen
Börja med demo av programmet för att visa vad vi gjort<br>
teknisk genomgång sen med övergripande av vad vi skrivit i SDD:n. ska vara en powerpoint med slides<br>
Ska ta ca 15min, max 20. <br>


Det som bedöms är masterbranchen enbart. Ska ha en readme om man behöver göra något extra för att köra programmet.

####Vad innebär teknisk genomgång?
fick svar på denna ^^

####Vi kommer nog inte hinna göra klart main menu osv, ska vi ta bort de skissarna från RAD då?
Nej det behöver vi inte, men bra att säga att vi inte implementerat det.

####SDD, punkt 5.1 använder vi ej, får vi ta bort?
Ja, vi får ta bort den om vi vill men vi kan också skriva ett litet stycke att vi inte har något access control eftersom vi inte har login system eller admin privileges.
