# zadatak - izdavanje kartica

Ovaj projekt je rezultat zadatka - izdavanje kartica. Projekt reprezentira jednostavnu backend aplikaciju za stvaranje aplikacijske datoteke za izradu kartice. 

# Korištenje aplikacije

* Baza podataka je PostgreSQL. Mora biti namještena lokalno kako bi aplikacija radila.
* Klonirajte projekt.
* Izvršite SQL komande za postavljanje baze podataka koje se nalaze u datoteci **src/main/resources/data_init.sql** .
* Pokrenite aplikaciju. Vrti se na [http://localhost:8080](http://localhost:8080).
* Za korištenje endpointova aplikacije odabrao sam postman.
* Datoteke se spremaju u folder **/src/main/java/com/evaluationtask/cardissuer/applicationfiles**.


# Upotreba

* API objašnjenje
  1. GET **http://localhost:8080/api/persons/{oib}** Pretražujemo postoji li osoba s određenim oib-om u bazi. Ako postoji dobijemo nazad informacije o osobi, a ako ne postoji onda se ispiše greška. 
  2. POST  **http://localhost:8080/api/persons** Spremamo osobu u bazu podataka. Informacije o osobi dajemo kao request body.
  3. DELETE **http://localhost:8080/api/persons/{oib}** Ako osoba s određenim oib-om postoji u bazi, brišemo je. Uz to, aktivnu datoteku koja pripada toj osobi, prebacujemo iz aktivnog statusa u neaktivni, ako takva postoji.
  4. POST **http://localhost:8080/api/persons/{oib}/issueCard** Stvarano datoteku za određenu osobu. Ako je to prva datoteka te osobe, status osobe prelazi iz neaktivnog stanja u aktivno. Ako to nije prva datoteka te osobe, onda zadnju datoteku (koja je ujedno i aktivna) koja pripada toj osobi deaktiviramo i stvaramo novu aktivnu datoteku.

Za logiku iza STATUS polja odabrao sam tako da postoje dva statusa - ACTIVE i INACTIVE (isto kod osobe i kod datoteke). Osoba je u INACTIVE stanju kad nema nijednu datoteku na svoje ime. Čim se stvori prva datoteka, taj status prelazi u ACTIVE. Što se tiče statusa kod datoteka, samo kronološki zadnja ima status ACTIVE, ostale imaju status INACTIVE. Ako se osoba obriše, onda i ta zadnja datoteka poprima status INACTIVE.
