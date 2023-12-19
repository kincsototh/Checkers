This project has been made for my Programming 3 class. All comments are in Hungarian including the documentation. 

# CHECKERS [HUN]

## Felhasználói kézikönyv, a program használata:
Indulás után a következő műveleteket kínálja fel a program a felhasználó számára.
Klikkelésre a következők történnek.

![image](https://github.com/kincsototh/Checkers/assets/144167052/e71b957c-f44a-4d0d-bfe1-3d64b59c18fc)


### 1. Új játék indítása
Belépve ebbe a pontba a következő pontok jelennek meg, ahhoz, hogy milyen fajta
játékot szeretnénk indítani:
  ● tábla méret módosítása (ez lehet a klasszikus 10x10-es (nemzetközi) vagy
8x8)
  ● játékosok számának beállítása (ez lehet 1 vagy 2, attól függően, a játékos
gép ellen akar-e játszani vagy pedig másik játékos ellen)
  ● játékosok színének változtatása
  ● játékosok nevének beállítása.
Az ablak megnyílásakor alapvetően az egyjátékos mód van beállítva és az ellenfél neve
automatikusan “számítógép”. Ezt lehetőség van módosítani a kétjátékos mód
kiválasztásakor. A játék indításához csak rá kell kattintani az “Indítás” gombra.

![image](https://github.com/kincsototh/Checkers/assets/144167052/06d17484-ff13-49f5-8f3e-a5c005d88eab)


#### 2. Elemzőtábla indítása
Belépve ebbe a pontba a következő pontok jelennek meg, ahhoz, hogy milyen fajta
táblán szeretnénk elemezni:
● tábla méret módosítása (ez lehet a klasszikus 10x10-es (nemzetközi) vagy
8x8)
● játékosok színének változtatása.
Ebben a menüpontban arra van lehetősége a felhasználónak, hogy játékosok nélkül,
úgymond “maga ellen” tudjon lépéseket tenni. Ez arra hasznos, hogy esetlegesen
régebbi partikat elemezzen (“mi lehetett volna…?” alapon), vagy pedig
próbálgathassa a szabályokat, lépéseket. Ez segítséget nyújthat mind a kezdők,
mind a profibbak számára is, akik esetleg nem rendelkeznek saját táblával.
Az “Indítás” gombra kattintva van lehetőségünk az elemzőtáblát elindítani.

![image](https://github.com/kincsototh/Checkers/assets/144167052/c39c668e-5fb6-4e13-b52a-8cba7c57db42)

### 3. Szabályok
Aki esetleg új a játékban, az megismerheti a szabályokat ebben a menüpontban. A
menüpont elmagyarázza (esetleg ábrákon is), hogy mik a játék szabályai (merre
lehet lépni, hogyan kell ütni stb.).

![image](https://github.com/kincsototh/Checkers/assets/144167052/380f2b6f-85a0-43f7-b122-c84dbc8f1754)

#### 4. Kilépés
Kilép a menüből/programból.

![image](https://github.com/kincsototh/Checkers/assets/144167052/e020e637-8d7e-4e90-a216-5b9ea2093e02)

Amennyiben az “Új játék indítása” menüpont alatt lévő adatokat kitöltjük/kiválasztjuk, a
program létrehozza az új partit. Ez egy külön ablakban fog megnyílni. Itt látszódni fog a tábla
maga, rajta a figurák. Amennyiben az éppen lépésen lévő játékos rányom az egyik
figurájára, a program különböző színekkel jelzi, milyen valid lépések léteznek az adott
figurával. Amennyiben szabálytalan lépést téne az egyik játékos, a program egyszerűen
nem lépi meg, és a program nem áll le. Ha a figuránk eléri az (számunkra) utolsó sort,
vezérré/királlyá változik. Ezt a program úgy jelzi, hogy kör helyett egy vezér fog megjelenni a
figuránk helyett. Ez itt alább látható, illetve ezek a színek érhetők el a program személyre
szabásához is.

![image](https://github.com/kincsototh/Checkers/assets/144167052/bdf8404b-9288-4d00-b37a-2b04942f89e9)

Játék közben is lehetőség van elmenteni az adott állást, hogy később lehessen folytatni a
partit.

## Megvalósítás:
A játék implementálásához a fentiek alapján két nagy csoportra bonthatók az osztályok. A
grafikus megjelenítés osztályai a MainMenu, NewWindow, Square, és a Grid osztály. Utóbbi
összeköti a megjelenítést a játéklogikával. A játéklogika osztályai a Play, Move, Piece,
Board, Player. Ezeken kívül pedig a teszteléshez használt teszt osztályok a testBoardClass,
testGridCaptures, testGridClass, testPlayClass (JUnit test case-k).
Fontos megjegyeznem, hogy a kódot angol nyelven írtam (tehát a különböző osztályok,
attribútumaik, függvényeik angol nyelven vannak), a program és a kommentek nyelve
azonban magyar.

