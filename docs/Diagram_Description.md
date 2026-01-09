# UninaBioGarden - Detailed Diagram Description

## Overview

This document provides a comprehensive textual description of all diagrams contained in the `Diagrams.drawio` file for the UninaBioGarden project. The diagrams represent different views of a garden management system designed for tracking plots, seasonal projects, crops, and activities.

---

## Diagram 1: Entity-Relationship Diagram (ER Diagram) - Original Version

### Main Entities

#### 1. **Utente (User)**

- **Attributes:**

  - `username` (underlined - primary key)
  - `nome` (name)
  - `cognome` (surname)
  - `b_day` (birthday)
  - `password`
  - `codice_fiscale` (fiscal code)
  - `gender`

- **Specialization:**
  - The User entity has a disjoint and total specialization into two subtypes:
    - **Proprietario (Owner)**: Users who own plots and create seasonal projects
    - **Coltivatore (Farmer)**: Users who perform activities on crops

#### 2. **Lotto (Plot)**

- **Attributes:**

  - `codice_lotto` (plot code)
  - `indirizzo` (address)
  - `estensione` (size/extension)
  - `orto` (garden name)
  - `username_prop` (foreign key to owner)

- **Relationships:**
  - **Possiede** (Owns): One Proprietario owns zero or many Lotti (1:0..\*)
  - **Occupa** (Occupies): One Lotto is occupied by zero or many Progetto Stagionale (1:0..\*)

#### 3. **Progetto Stagionale (Seasonal Project)**

- **Attributes:**

  - `nome` (name)
  - `data_inizio` (start date)
  - `data_fine` (end date)
  - `data_scadenza` (deadline date)
  - `descrizione` (description - optional)

- **Relationships:**
  - **pianifica** (plans): One Proprietario plans zero or many Seasonal Projects (1:0..\*)
  - **Occupa** (Occupies): One Project occupies exactly one Lotto (1:1)
  - **prevede** (foresees): One Project foresees one or many Activities (1:1..\*)
  - **assegnato a** (assigned to): One Project is assigned to one or many Coltivatori (1:1..\*)
  - **coltiva** (cultivates): One Project cultivates zero or many Colture through Coltivazione relationship (1:0..\*)

#### 4. **Attivita (Activity)**

- **Attributes:**

  - `nome` (name)
  - `data_inizio` (start date)
  - `data_fine` (end date - optional)
  - `descrizione` (description - optional)
  - `stato` (status)

- **Specialization:**

  - The Activity entity has a partial and disjoint specialization:
    - **Semina (Sowing)**: Has attribute `qty` (quantity)
    - **Irrigazione (Irrigation)**: No additional attributes
    - **Raccolta (Harvest)**: Has attributes `qty_prevista` (expected quantity) and `qty_effettiva` (actual quantity)

- **Relationships:**
  - **Svolge** (Performs): Zero or many Activities are performed by one or many Coltivatori (0.._:1.._)
  - **prevede** (foresees): One or many Activities are foreseen by one Project (1..\*:1)
  - **associata_a** (associated with): Zero or many Activities are associated with zero or one Coltura (0..\*:0..1)

#### 5. **Coltura (Crop)**

- **Attributes:**

  - `nome` (name - underlined as key)
  - `descrizione` (description - optional)
  - `tempo_maturazione` (maturation time)

- **Specialization (in a later version):**

  - The Crop entity has a total and disjoint specialization:
    - **Ortofrutticoli (Fruit and Vegetables)**
    - **Erbe Aromatiche (Aromatic Herbs)**
    - **Leguminose (Legumes)**
    - **Perenni (Perennials)**

- **Relationships:**
  - Through the **Coltivazione** (Cultivation) relationship, connects Projects to Crops
  - **associata_a**: Activities can be associated with Crops

#### 6. **Coltivazione (Cultivation) - Relationship**

This is a many-to-many relationship between Progetto Stagionale and Coltura with attributes:

- `fase_sviluppo` (development phase)
- `previsione raccolta` (harvest forecast)
- `descrizione` (description - optional)

### Design Notes and Constraints

**Elimination of Orto Entity:**
The design notes explain that the "Orto" (Garden) entity was eliminated because the system focuses on individual plots rather than gardens. A garden is simply represented as a geographical area containing multiple plots, which can be represented as an attribute (location/address) of the plot.

**Key Constraints:**

1. Multiplicity [0..*] allows a Proprietario to exist without having any plots or projects registered yet
2. A plot can be associated with multiple projects over time, but their time intervals cannot overlap
3. Crops are pre-populated entities in the system, not specific to a project
4. Activities are uniquely identified by name, start date, and the project that foresees them
5. Different crop types were introduced (even if not explicitly required) to allow future extension

---

## Diagram 2: UML Class Diagram - Initial Version

This diagram represents the same entities in UML class notation with the following classes:

### Classes

#### **Utente**

```
+ username: String
+ password: String
+ nome: String
+ cognome: String
+ bday: Date
+ codice_fiscale: String
+ gender: String
```

**Subclasses:**

- **Coltivatore** (empty - pure specialization)
- **Proprietario** (empty - pure specialization)
- Note: {Total, Disjoint} constraint on the specialization

#### **Attività**

```
+ nome: String
+ descrizione: String [0..1]
+ data_inizio: Date
+ data_fine: Date [0..1]
+ stato: StatoAttivita
```

**Subclasses:**

- **Semina**: `+ qty: Integer`
- **Irrigazione**: (no additional attributes)
- **Raccolta**:
  - `+ qty_prevista: Integer`
  - `+ qty_effettiva: Integer`
- Note: {Partial, Disjoint} constraint

#### **Progetto Stagionale**

```
+ nome: String
+ data_inizio: Date
+ data_scadenza: Date
+ data_fine: Date [0..1]
+ descrizione: String [0..1]
```

#### **Coltura**

```
+ nome: String
+ descrizione: String [0..1]
+ tempo_maturazione: String
```

**Subclasses:**

- **Ortofrutticolo**
- **Erba Aromatica**
- **Perenne**
- **Leguminosa**
- Note: {Total, Disjoint} constraint

#### **Lotto**

```
+ indirizzo: String
+ codice_lotto: String
+ estensione: Float
+ orto: String
```

### Relationships

1. **Proprietario → Progetto Stagionale** (pianifica): 1 to 0..\*
2. **Proprietario → Lotto** (possiede): 1 to 0..\*
3. **Lotto → Progetto Stagionale** (occupa): 1 to 0..\*
4. **Progetto Stagionale → Attività** (prevede): 1 to 1..\*
5. **Coltivatore → Attività** (svolge): 1.._ to 0.._
6. **Coltivatore → Progetto Stagionale** (assegnato): 1.._ to 0.._
7. **Attività → Coltura** (associata a): 0..\* to 0..1
8. **Progetto Stagionale → Coltura** (coltiva): 0.._ to 1.._

### Enumerations

#### **TipoUtente**

- Proprietario
- Coltivatore

#### **StatoAttivita**

- Pianificata
- InCorso
- Completato

#### **TipoAttivita**

- Raccolta
- Semina
- Irrigazione

---

## Diagram 3: UML Class Diagram - Restructured Version

This is the final restructured version with surrogate keys and additional attributes.

### Major Changes

#### **Utente**

```
+ username: String
+ password: String
+ nome: String
+ cognome: String
+ bday: Date
+ codice_fiscale: String
+ gender: String
+ type: TipoUtente  // NEW: enum instead of inheritance
```

**Key Change:** No longer uses inheritance; instead uses a `type` attribute with TipoUtente enum.

#### **Progetto Stagionale**

```
+ id_progetto: Integer  // NEW: surrogate key
+ nome: String
+ descrizione: String [0..1]
+ data_inizio: Date
+ data_scadenza: Date
+ data_fine: Date [0..1]
```

#### **Lotto**

```
+ id_lotto: Integer  // NEW: surrogate key
+ indirizzo: String
+ codice_lotto: String
+ estensione: Float
+ orto: String
```

#### **Attività**

```
+ id_attivita: Integer  // NEW: surrogate key
+ nome: String
+ descrizione: String [0..1]
+ data_inizio: Date
+ data_fine: Date [0..1]
+ stato: StatoAttivita
+ tipo: TipoAttivita  // NEW: enum instead of inheritance
+ qty_prevista: Integer  // NEW: combined from subtypes
+ qty_effettiva: Integer  // NEW: combined from subtypes
```

**Key Change:** No longer uses inheritance; all activity types are represented in one class with a `tipo` attribute.

#### **Coltura**

```
+ id_coltura: Integer  // NEW: surrogate key
+ nome: String
+ data_semina: Date  // NEW attribute
+ tempo_maturazione: String
+ descrizione: String [0..1]
+ tipo: TipoColtura  // NEW: enum instead of inheritance
```

#### **Coltivazione (NEW Association Class)**

```
+ prev_raccolta: Date
+ fase_sviluppo: FaseSviluppo
+ descrizione: String [0..1]
```

This represents the many-to-many relationship between Progetto Stagionale and Coltura.

### New Enumerations

#### **TipoColtura**

- Ortofrutticolo
- ErbaAromatica
- Perenne
- Leguminosa

#### **FaseSviluppo**

- Germinazione
- Fioritura
- Fruttificazione
- ProntaPerRaccolta

### Updated Relationships

All relationships now use the surrogate keys (id_progetto, id_lotto, id_attivita, id_coltura, username) as foreign keys.

### Design Constraints (New)

**Surrogate Keys:**

- For efficiency, surrogate keys were introduced for all major entities.

**Constraints:**

1. Only a User with type = Proprietario can participate in the 'pianifica' relationship with Progetto
2. Only a User with type = Proprietario can participate in the 'possiede' relationship with Lotto
3. A Lotto can be associated with multiple projects over time, but their time intervals cannot overlap
4. Only a User with type = Coltivatore can participate in the 'svolge' relationship with Attività
5. An Activity with type = Semina must have a value for qty_effettiva and must have qty_prevista set to null
6. An Activity with type = Raccolta must have values for both qty_effettiva and qty_prevista
7. An Activity with type = Irrigazione must have both qty_effettiva and qty_prevista set to null
8. A Coltivatore user cannot be assigned to overlapping activities

---

## Diagram 4: Logical Schema (Relational Tables)

This diagram shows the final relational database schema with table structures.

### Tables

#### **utente**

```
PK: username
Attributes:
- username
- password
- email
- nome
- cognome
- nazionalita
- num_tel
- residenza
- type
```

#### **progetto_stagionale**

```
PK: id_progetto
FK: username_prop (→ utente.username)
Attributes:
- id_progetto
- nome
- data_inizio
- data_scadenza
- data_fine
- descrizione
```

#### **lotto**

```
PK: id_lotto
FK: username_prop (→ utente.username)
Attributes:
- id_lotto
- indirizzo
- codice_lotto
- estensione
- nome_orto
```

#### **coltura**

```
PK: id_coltura
Attributes:
- id_coltura
- nome
- descrizione
- tempo_maturazione
- data_semina
- tipo
```

#### **attivita**

```
PK: id_attivita
FK: id_progetto (→ progetto_stagionale.id_progetto)
FK: id_coltura (→ coltura.id_coltura)
Attributes:
- id_attivita
- nome
- data_inizio
- data_fine
- descrizione
- stato
- tipo
- qty_effettiva
- qty_prevista
```

#### **assegnazione_coltivatore**

```
PK: (username_col, id_progetto)
FK: username_col (→ utente.username)
FK: id_progetto (→ progetto_stagionale.id_progetto)
```

This is a junction table for the many-to-many relationship between Coltivatore and Progetto Stagionale.

#### **coltivazione**

```
PK: (id_progetto, id_coltura)
FK: id_progetto (→ progetto_stagionale.id_progetto)
FK: id_coltura (→ coltura.id_coltura)
Attributes:
- prev_raccolta
- fase_sviluppo
- descrizione
```

This is a junction table with attributes for the many-to-many relationship between Progetto Stagionale and Coltura.

#### **svolge**

```
PK: (username_col, id_attivita)
FK: username_col (→ utente.username)
FK: id_attivita (→ attivita.id_attivita)
```

This is a junction table for the many-to-many relationship between Coltivatore and Attività.

### Key Observations

1. **Foreign key notation:** Underlined attributes with arrows indicate foreign keys pointing to primary keys in other tables
2. **Junction tables:** Used for implementing many-to-many relationships
3. **Attribute expansion:** Some attributes were added (email, nazionalita, num_tel, residenza) for more complete user information
4. **Type fields:** The `type` field in utente and attivita tables uses enum values to distinguish between different user types and activity types respectively

---

## Summary of Evolution

The diagrams show the evolution of the database design from:

1. **ER Diagram (Original):** Initial conceptual model with entities, relationships, and inheritance hierarchies
2. **UML Class Diagram (Initial):** Translation to object-oriented representation maintaining inheritance
3. **UML Class Diagram (Restructured):** Refined design introducing surrogate keys and converting inheritance to type attributes
4. **Logical Schema:** Final relational database schema ready for implementation

The main design improvements include:

- Introduction of surrogate keys for better performance
- Conversion of inheritance to type enumerations for simpler implementation
- Addition of association classes (Coltivazione) to capture relationship attributes
- Explicit junction tables for many-to-many relationships in the logical schema
