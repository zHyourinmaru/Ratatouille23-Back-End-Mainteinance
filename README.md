# Ratatouille23-Back-End-Mainteinance
Ratatouille23 is a Spring Boot backend application designed for restaurant management (handling dishes, allergens, orders, tables, and users) . This repository showcases a complete Software Evolution and Re-engineering cycle. 


Il documento "Presentation.pdf" è dedicato all'esposizione delle metriche di misurazione calcolate per il progetto, mettendone a confronto lo stato iniziale (AS-IS) e quello ottimizzato (TO-BE). 

Ecco nel dettaglio tutti i dati e le tabelle riportate all'interno del file:

### 1. Metriche di Misurazione – Function Points
La presentazione sottolinea un concetto fondamentale: **il numero di Function Point (FP) del codice AS-IS e TO-BE è identico**, a dimostrazione del fatto che tra le due versioni del software non c'è stata alcuna modifica alle funzionalità percepite dall'utente.

Viene presentata la seguente tabella di calcolo per le transazioni (EI, EO, EQ):
*   **EI Utenti:** FTR 1, DET 5, Complessità Bassa, Moltiplicatore 3 -> **9 FP**
*   **EI Piatti:** FTR 2, DET 4, Complessità Bassa, Moltiplicatore 3 -> **9 FP**
*   **EI Allergeni:** FTR 1, DET 2, Complessità Bassa, Moltiplicatore 2 -> **6 FP**
*   **EI Tavoli:** FTR 1, DET 3, Complessità Bassa, Moltiplicatore 3 -> **9 FP**
*   **EI Ordini:** FTR 2, DET 6, Complessità Medio, Moltiplicatore 2 -> **8 FP**
*   **EO Calcoli:** FTR 3, DET 8, Complessità Media, Moltiplicatore 1 -> **5 FP**
*   **EQ Utenti:** FTR 1, DET 5, Complessità Bassa, Moltiplicatore 1 -> **3 FP**
*   **EQ Piatti:** FTR 2, DET 5, Complessità Bassa, Moltiplicatore 1 -> **3 FP**
*   **EQ Allergeni:** FTR 1, DET 2, Complessità Bassa, Moltiplicatore 1 -> **3 FP**
*   **EQ Tavoli:** FTR 1, DET 3, Complessità Bassa, Moltiplicatore 1 -> **3 FP**
*   **EQ Ordini:** FTR 3, DET 8, Complessità Media, Moltiplicatore 1 -> **4 FP**

### 2. Metriche Strutturali e di Codice
La seconda parte del documento si concentra sulle misurazioni puramente ingegneristiche e algoritmiche.

**Linee di Codice (LOC):**
*   `OrderService`: 170 LOC
*   `UserService`: 161 LOC
*   `TableService`: 120 LOC
*   `AllergenService`: 119 LOC
*   `DishService`: 119 LOC

Le considerazioni derivate da questi numeri sono due:
1.  **Non è presente alcuna classe «God Object»** (ovvero classi enormi con troppe responsabilità).
2.  Per quanto riguarda la dimensione del progetto, il codice di produzione ha subìto un aumento irrisorio del **+1.43%**, mentre il codice di test è esploso con un aumento del **+36,46%** (dimostrando un enorme lavoro sulla *Quality Assurance*).

**Complessità Ciclomatica di McCabe (MCC):**
Vengono riportati i livelli di complessità a seconda della tipologia di operazione:
*   Metodi CRUD semplici (come `getById`, `delete`): MCC tra **1-2**
*   Metodi di ricerca (`get*ByAttributes`): MCC tra **3-4**
*   Metodi di aggiornamento (`update*`): MCC **fino a 6**

**Volume di Halstead:**
Il documento mostra una tabella comparativa prima e dopo il refactoring, prendendo esplicitamente in considerazione la classe **AllergenService**:
*   **Operatori Totali (N1):** scesi da ~48 (AS-IS) a ~24 (TO-BE)
*   **Operandi Totali (N2):** scesi da ~52 (AS-IS) a ~26 (TO-BE)
*   **Lunghezza Totale (N):** dimezzata da ~100 a ~50
*   **Vocabolario (n):** ridotto da ~35 a ~25
*   **Volume:** crollato drasticamente da **~512** (AS-IS) a **~232** (TO-BE), a dimostrazione di una sintassi notevolmente sfoltita e più manutenibile.
