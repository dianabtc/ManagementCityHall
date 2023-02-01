# ManagementCityHall
Proiectul prezinta implementarea unui sistem de management al unei primarii, unde
diversi utilizator pot inainta cereri ce vor fi rezolvate de functionari publici din
biroul specializat, lucrand cu genericitate si Java Collections.

Clasa "Birou" am implementat-o generic cu parametrul <T>, reprezentand un tip
de utilizator. Colectia de cereri am folosit-o ca un PriorityQueue in care adaug
cu ajutorul clasei CustomCerereComparator ce implementeaza interfata Comparator,
ordonand astfel cererile dupa prioritate descrescator si in caz de prioritati egale,
dupa data. Am ales PriorityQueue pentru inserare ordonata dar si pentru ca un functionar
ar rezolva cea mai urgenta cerere, adica cea din varful cozii (peak()/poll()). Am retinut
si un ArrayList de functionari publici pentru a-i putea accesa dupa index.

In clasa utilizator am facut ArrayList-uri pentru cererile in asteptare si cele solutionate,
tot pentru accesare. Le-am sortat cu Collections.sort crescator dupa data, clasa Cerere
implementand interfata Comparable. Angajat, Elev, EntitateJuridica, Pensionar, Persoana
mostenesc clasa abstracta Utilizator.

In ManagementPrimarie am un ArrayList pentru utilizatori si cate un birou specializat
pentru fiecare tip de persoana: Birou<Angajat>, Birou<Elev>, Birou<EntitateJuridica>,
Birou<Pensionar>, Birou<Persoana>. Am implementat comenzi astfel:

=> "adauga_utilizator": in functie de tipul de utilizator il adaug in sistemul
primariei cu metoda add().

=> "cerere_noua": am extras data, tipul si prioritatea cererii, apoi am cautat
in ArrayList utilizatorul care vrea sa faca cererea dupa nume si am apelat metoda
"creeazaCerere" care scrie in fisier exceptia in cazul in care metoda "scrieTextCerere"
o arunca (atunci cand tipul este incompatibil), iar in caz contrar adauga cererea in
biroul corespunzator si in cererile de asteptare ale utilizatorului, in ordinea datei

=> "afiseaza_cereri_in_asteptare": gasesc utilizatorul dupa nume in sistemul primariei
si ii afisez fiecare cerere din ArrayList-ul de cereri in asteptare cu metoda 
"afiseazaCerere"

=> "afiseaza_cereri_finalizate": procedez similar numai ca pentru ArrayList-ul de
cereri solutionate

=> "retrage_cerere": sterg cererea din cererile de asteptare ale utilizatorului
dupa nume si data, si apoi gasesc tipul utilizatorului corespunzator si o sterg 
si din coada biroului cu metoda remove()

=> "afiseaza_cereri": afisez cererile din biroul corespunzator, astfel: scot
fiecare cerere din coada cu poll(), o introduc intr-un ArrayList si apoi refac
coada, adaugand elementele

=> "adauga_functionar": adaug un functionar in biroul corespunzator

=> "rezolva_cerere": scot din coada biroului corespunzator cererea, scriu textul
in fisierul functionarului si apoi o caut dupa nume si data in ArrayList-ul de
cereri in asteptare ale utilizatorului, o sterg si o adaug in cele solutionate
