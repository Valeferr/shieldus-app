# 🛡️ ShieldUs – Piattaforma per l’Empowerment di Genere

> *Un’applicazione Android per l’educazione, il supporto legale e l’accesso ai servizi in tema di uguaglianza di genere, salute sessuale e autotutela.*

**ShieldUs** è un progetto di Interazione Uomo-Macchina (IHM) sviluppato nell’ambito del corso dell’Università degli Studi di Salerno (A.A. 2024/2025). L’obiettivo è creare un’app mobile accessibile, intuitiva e riservata che supporti gli utenti nel comprendere i propri diritti, riconoscere dinamiche malsane e accedere a servizi concreti in ambito legale, psicologico e sanitario.

---

## 🎯 Contesto e Motivazione

Nonostante i progressi nell’uguaglianza di genere, molte persone – soprattutto giovani e donne – incontrano difficoltà nell’accedere a informazioni chiare, affidabili e non giudicanti su temi sensibili come:
- Il consenso nelle relazioni
- La discriminazione sul lavoro
- La salute sessuale
- I diritti legali in caso di abuso o violenza

ShieldUs nasce per colmare questo divario, offrendo uno **spazio sicuro, anonimo e progettato con un approccio centrato sull’utente**, in cui teoria e pratica si uniscono per favorire l’**empowerment personale**.

---

## 📱 Tecnologie Utilizzate

- **Piattaforma**: Android
- **IDE**: Android Studio
- **Linguaggio**: Java
- **Design UI**: Figma (prototipo low-fi e high-fi)
- **API integrate**:
  - **Google Maps SDK** – per la visualizzazione interattiva della mappa dei servizi
  - **Fused Location Provider API** – per la geolocalizzazione automatica dell’utente
  - **Google Places API** – per la ricerca di centri antiviolenza, cliniche e sportelli legali
- **Gestione dati**: SharedPreferences (dati locali)

---

## 🧩 Funzionalità Principali

### 1. **Taso di uscita rapida dall'applicazione che reindirizza a Google**
https://github.com/user-attachments/assets/80cea20c-2ec5-4d01-9fd0-cd58f070a4c7

### 2. **Moduli Educativi Interattivi**
- Percorsi educativi su consenso, parità di genere e salute sessuale
- Quiz con feedback personalizzato
- Gamification per un apprendimento coinvolgente
- Cronologia dei quiz con barra di avanzamento

https://github.com/user-attachments/assets/b45aa76c-6353-4196-8d87-5cdad1879015


### 3. **Chatbot Legale**
- Assistente conversazionale 24/7
- Risposte basate su casi reali e validate da esperti
- Guida alle procedure legali e ai contatti utili
- Esempi di domande iniziali per guidare l’interazione

  https://github.com/user-attachments/assets/a2fb13c1-2b87-46b9-bbd2-a3b5db5d2fd3

### 4. **Mappa Collaborativa dei Servizi**
- Trova centri antiviolenza, cliniche specializzate, sportelli legali
- Geolocalizzazione e filtri avanzati
- Possibilità di contribuire in modo anonimo
- Filtraggio dinamico per luogo


https://github.com/user-attachments/assets/79deae94-8fd5-4fd3-9914-b286fa29cc97

https://github.com/user-attachments/assets/653c1eea-16c5-4f4a-9a10-dad3de56ae89

### 5. **Supporto Psicologico Urgente**
- Accesso immediato a numeri verdi e linee di ascolto
- Opzione di prenotazione consulto telefonico

---

## 👥 Profili Utente (Personas)

Il progetto è stato sviluppato intorno a tre *personas* rappresentative:

| Persona | Età | Scopo |
|--------|-----|-------|
| **Alessia** | 19 anni | Studentessa che cerca informazioni sul consenso in modo anonimo |
| **Laura** | 35 anni | Donna lavoratrice vittima di discriminazione, cerca supporto legale rapido |
| **Marco** | 22 anni | Giovane adulto con una MST, cerca informazioni riservate e servizi medici |

---

## 🧪 Metodologia di Progettazione

Il design e lo sviluppo del sistema si basano su un approccio **user-centered**, con le seguenti fasi:

1. **Analisi qualitativa** (interviste, osservazione)
2. **Definizione di task e casi d’uso**
3. **Analisi comparativa** con sistemi esistenti (es. Bright Sky, Feminismo.org)
4. **Design iterativo** con prototipi low-fi
5. **Validazione tramite tecnica del Mago di Oz** e valutazione euristica (Nielsen)
6. **Implementazione in Android Studio (Java)**
7. **Test utente e iterazioni**

---

## 🖼️ Prototipo Interattivo

👉 [**Visualizza il prototipo Figma**](https://www.figma.com/design/Ja34rEHRDMN1xzyzUfehuM/Progetto?node-id=22-108&p=f&t=AqxftDWwoVoL2CcU-0)

Il prototipo include flussi completi per:
- Accesso ai moduli educativi
- Utilizzo del chatbot legale
- Ricerca di servizi tramite mappa
- Contributo alla mappa collaborativa
- Accesso a supporto psicologico urgente

---

## 🧱 Design Pattern Utilizzati

- **Card Layout**: per organizzare contenuti in modo chiaro e scansionabile
- **Wizard**: per guidare l’utente in flussi complessi (es. quiz)
- **Chat Interface**: interfaccia conversazionale per il chatbot legale
- **Search with Filters**: ricerca avanzata nella mappa dei servizi
  
---

## 🔧 Miglioramenti Implementati (Assignment n.4)

Nell’ultima iterazione, sono stati implementati cinque miglioramenti chiave basati su feedback utente:

| Funzionalità | Descrizione |
|-------------|-----------|
| **Filtraggio della mappa per luogo** | L’utente può filtrare i servizi per area geografica, migliorando la precisione della ricerca |
| **Cronologia dei quiz** | Visualizza lo stato di completamento e permette di riprendere i quiz iniziati |
| **Esempio di domande per il chatbot** | Suggerimenti iniziali per guidare l’utente e ridurre la barriera d’ingresso |
| **Promemoria modalità anonima** | Pop-up informativo che ricorda che i progressi non vengono salvati in modalità anonima |
| **Miglioramento del tasto di uscita veloce** | Reindirizza immediatamente a Google per garantire discrezione e sicurezza |

---

## 📂 Struttura del Progetto

La repository contiene:
- `Assignment-1.docx`: Analisi iniziale, personas e requisiti UX
- `Assignment-2.docx`: Casi d’uso e analisi comparativa
- `Assignment-3.docx`: Design, prototipo e valutazione
- `Assignment-4.docx`: Design, prototipo e valutazione
- Cartella `/app/src/main/java/` con codice Java
- Cartella `/app/src/main/res/layout/` con layout XML
- Prototipi Figma (link esterno)
- Documentazione di progetto aggiornata

---

## 🔍 Valutazione dell’Usabilità

Il progetto ha seguito un processo di validazione rigoroso:
- **Tecnica del Mago di Oz**: per testare il chatbot e la mappa in fase di prototipazione
- **Valutazione euristica (Nielsen)**: identificazione di violazioni di usabilità
- **Test utente con osservazione diretta**: raccolta di feedback qualitativi e quantitativi

Le principali modifiche apportate:
- Migliorata la visibilità del chatbot con icona flottante
- Semplificato il linguaggio dell’interfaccia
- Aggiunta funzione "Annulla" nei flussi complessi
- Ottimizzazione per dispositivi mobili (testo più breve, pulsanti più grandi)

---

## 📊 Risultati dei Test di Usabilità

Abbiamo condotto test con **10 utenti** utilizzando:
- **Questionario QUIS** (scala 1-9)
- **Valutazione percepita** (ISE, IKS, IPC, IMOT)

### 🔢 Risultati medi:
| Area | Punteggio |
|------|---------|
| Aspetto visivo e organizzazione | 7.25 |
| Chiarezza dei contenuti | 6.59 |
| Facilità di apprendimento | 6.98 |
| Completezza delle funzionalità | 7.48 |

✅ **Punteggio più alto**: **7.48** – capacità percepita del sistema  
✅ **Impatto educativo**: task "Educazione al consenso" ha ottenuto **4.00/5** in conoscenza acquisita (IKS)

---

## 👥 Team

**Gruppo 5 – Corso di Interazione Uomo Macchina, Università di Salerno**

| Nome | Ruolo | Email |
|------|-------|-------|
| Valentina Ferrentino | Manager del gruppo e della documentazione | v.ferrentino@studenti.unisa.it |
| Gaia Grassia | Manager della valutazione | g.grassia@studenti.unisa.it |
| Valeria Zaccaro | Manager del design | v.zaccaro@studenti.unisa.it |

---

## 📚 Licenza

Questo progetto è sviluppato a scopo didattico.  
