# 🍔 FoodExpress

FoodExpress est une application desktop en **JavaFX** permettant de simuler une plateforme de commande de nourriture en ligne.

Elle propose un menu interactif, un système de panier, des filtres avancés et un historique de commandes sauvegardé en JSON.

---

## 🚀 Fonctionnalités

### 🧾 Menu
- Affichage des plats disponibles
- Catégories : Burgers, Pizzas, Sushi, Desserts, Boissons

### 🔍 Recherche & filtres
- Recherche par nom
- Filtrage par catégorie
- Filtrage par sous-catégorie (subtypes)

### 🛒 Panier
- Ajout d'articles
- Modification des quantités (+ / -)
- Suppression d'articles
- Calcul automatique du total
- Sauvegarde en JSON

### 📦 Commandes
- Validation de commande via bouton "Commander"
- Vidage automatique du panier
- Sauvegarde dans un historique
- Chargement des commandes au démarrage

---

## 🏗️ Architecture
```
org.example
├── controller
│   ├── MenuController.java
│   └── CartController.java
├── model
│   └── Item.java
└── Main.java
```
---

## 💾 Persistance (Gson)

L'application utilise Gson pour stocker les données en JSON.

### Fichiers :
- `cart.json` → panier
- `orders.json` → historique des commandes

### Exemple :

```json
[
  {
    "items": [
      {
        "item": {
          "name": "Hamburger Classic",
          "price": 8.99
        },
        "quantity": 2
      }
    ],
    "total": 17.98,
    "date": "2026-05-01T15:30:00"
  }
]
```

---

## 🛠️ Technologies

- Java 21
- JavaFX
- Maven
- Gson

---

## ⚙️ Installation

```bash
git clone https://github.com/zarkfire/FoodExpress.git
mvn clean install
mvn javafx:run
```

---

## 📌 Prérequis

- Java 17+
- Maven
- JavaFX SDK

---

## 💡 Améliorations possibles

- Interface historique des commandes
- Détails d'une commande
- Export PDF
- Base de données (SQLite)
- Backend Spring Boot

---

## 👨‍💻 Auteur

F8
