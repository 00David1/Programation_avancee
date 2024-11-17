# Rapport des TP Programmation avancée

Rédiger par : Grondin David

Réalisé le : 15/11/2024

Note : L'intelligence artifitielle a était utiliser comme outils durand la réalisation de ces exercices 

## Introduction 

L'objectif de ces TP était d'illustrer la gestion des ressources et sections critiques souvent présent dans les projets informatique. Ces exercices ont permis d'explorer plusieurs outils de synchronisation tels que les sémaphores et les blocs `synchronized`, en appliquant des concepts de programmation multi-thread pour résoudre des problématiques spécifiques.

---

## Définitions 

- **Ressource critique** : Une ressource partagée entre plusieurs processus et qui ne peut être utilisée simultanément que par un seul d'entre eux. Par exemple une zone mémoire.

- **Section critique** : Une portion de code contenant une ressource critique avec un seul threads pouvant entrer à la fois. 

- **Sémaphore** : Un mécanisme de synchronisation qui permet de contrôler l'accès à une ressource partagée en maintenant un compteur. Un sémaphore peut être binaire (valeur 0 ou 1) ou général (compteur arbitraire).

- **Synchronized** : Un mot-clé en Java permettant de verrouiller une méthode ou un bloc de code. Un seul thread peut exécuter ce code à la fois, garantissant ainsi une exécution séquentielle.

---

## Observation 

Les TPs ont mis en évidence des problématiques communes : 
1. L'identification des sections et ressource critiques.
2. Le choix des outils de synchronisation adaptés.
3. La coordination des threads pour garantir un fonctionnement cohérent et sans conflits.

---

## Description des TPs

### TP1 : Gestion d'un Passage Critique avec des Mobiles 

#### Description Générale
Ce projet simule le déplacement de cubes "mobile" dans une fenêtre graphique divisée fictivement en trois colonnes. Les cubes se déplacent horizontalement de gauche à droite et en direction inverse lorsqu'ils atteignent les bords. 

#### Problématique
La colonne centrale représente une **section critique**, où un seul cube à la fois est autorisé à passer.

#### Solution
- **Section critique** : L'avancement des cubes dans la colonne centrale.
- **Ressource critique** : L'accès à la colonne centrale.
- **Outil utilisé** : Sémaphore.

Les sémaphores gèrent l'accès à la section critique. Lorsqu'un cube entre, il bloque l'accès pour les autres avec (`syncWait()`), et lorsqu'il sort, il libère l'accès en donnant au autre un signale avec (`syncSignal()`).

#### Fonctionnement
1. Les cubes se déplacent par petits pas dans leurs propres threads.
2. Lorsqu'un cube atteint la colonne critique, il demande l'accès via le sémaphore.
3. Après avoir traversé la section critique, il signale sa sortie, permettant à un autre cube de passer.

#### Architecture
1. **Classe `UnMobile` :** 
   - Représente un mobile individuel.
   - Contient la logique de déplacement (avec des threads) et la gestion des sémaphores pour accéder à la section critique.
   - Dessine le mobile dans la fenêtre via Swing (`paintComponent`).

2. **Classe `UneFenetre` :** 
   - Gère l'interface graphique principale.
   - Crée des instances de `UnMobile` et organise leur affichage dans une fenêtre divisée en plusieurs sections.

3. **Classe `Semaphore` :** 
   - Une classe abstraite qui implémente les méthodes de base pour un sémaphore.
   - Permet de synchroniser l'accès aux sections critiques.

4. **Classe `SemaphoreGenerale` :** 
   - Implémente un sémaphore général pour contrôler l'accès à la colonne centrale.
   - Utilise les méthodes `syncWait()` et `syncSignal()` pour gérer l'accès concurrent.

5. **Classe `TpMobile` :** 
   - Point d’entrée principal (`main`).
   - Initialise la fenêtre et démarre les threads des mobiles.
---

#### Schéma UML 

![alt text](image.png)

### TP2 : Synchronisation de l'Affichage de Listes 

#### Description Générale
Ce projet consiste à afficher plusieurs listes de lettres dans la console. L'objectif est de garantir que chaque liste est affichée entièrement avant qu'une autre ne commence, sans interférences.

#### Problématique
Sans synchronisation, plusieurs threads peuvent écrire simultanément sur la console, entraînant un affichage mélangé des listes.

#### Solution
- **Section critique** : L'action d'écrire une liste complète dans la console.
- **Ressource critique** : La console partagée (`System.out`).
- **Outils utilisés** : 
  - Une approche utilisant `synchronized`.
  - Une autre approche utilisant un sémaphore binaire.

#### Fonctionnement
1. Dans la version `synchronized`, chaque thread verrouille la section critique pendant l'affichage de sa liste. De cette manière aucun autre thread ne peut entrer dans cette portion de code pendant l'afichage de ce dérnier. Une fois son affichage terminée, un autre thread peut entrer dans la section critique.
2. Dans la version avec sémaphore binaire, la resource critique est encadrer par un "syncWait()" et un "syncSignal()", de cette maniére un seul thread à la fois peut afficher sa liste dans la console.

#### Architecture
1. **Classe `Affichage` :** 
   - Définit un thread pour afficher une liste de lettres.
   - Utilise une synchronisation explicite via des sémaphores ou des blocs `synchronized` pour éviter les conflits d'écriture.

2. **Classe `Semaphore` :** 
   - Une classe abstraite qui implémente les méthodes de base pour un sémaphore.
   - Permet de synchroniser l'accès aux sections critiques.

3. **Classe `SemaphoreBinaire` :** 
   - Hérite de `Semaphore` et garantit une valeur binaire (0 ou 1).
   - Assure qu’un seul thread peut écrire dans la console à la fois.

4. **Classe `TpAffichage` :**
   - Point d'entrée principal.
   - Crée et lance plusieurs threads d'affichage synchronisés.
---
#### Schéma UML 

![alt text](image-1.png)

### TP3 : Boîte aux Lettres - Problème du Producteur-Consommateur 

#### Description Générale
Ce projet met en œuvre une relation entre une boite au lettre, un producteur et un consommateur. Le producteur dépose une lettre dans la boîte aux lettres et le consommateur la retire.

#### Problématique
Le producteur ne peut produire qu'une lettre si la boîte est vide, et le consommateur ne peut retirer qu'une lettre si la boîte est pleine. Cette synchronisation est essentielle pour éviter des erreurs de logique.

#### Solution
- **Section critique** : Les opérations de dépôt et de retrait dans la boîte aux lettres.
- **Ressource critique** : La boîte aux lettres.
- **Outil utilisé** : `BlockingQueue`.

#### Fonctionnement
1. La boîte aux lettres est représentée par une file bloquante (`BlockingQueue`).
2. Le producteur est bloqué jusqu'à ce que le consommateur retire la lettre déposée.
3. Le consommateur est bloqué jusqu'à ce que le producteur dépose une lettre.
4. Lorsque la lettre "Q" est produite et consommée, le programme se termine.

#### Architecture
1. **Classe `BoiteAuLettre` :** 
   - Représente une boîte aux lettres partagée.
   - Utilise une `BlockingQueue` pour synchroniser les échanges entre le producteur et le consommateur.

2. **Classe `Producteur` :** 
   - Implémente l'interface `Runnable`.
   - Dépose des lettres dans la boîte aux lettres via la méthode `deposer()`.

3. **Classe `Consommateur` :** 
   - Implémente l'interface `Runnable`.
   - Retire des lettres de la boîte aux lettres via la méthode `retirer()`.

4. **Classe `Main` :** 
   - Coordonne les interactions.
   - Crée les threads du producteur et du consommateur, puis les démarre.
---

#### Schéma UML 

![alt text](image-2.png)

## Conclusion 

Voici une version corrigée et améliorée de ta conclusion :

Ces travaux pratiques nous ont permis d'explorer plusieurs aspects essentiels de la programmation multi-threadée. Tout d'abord, nous avons appris à identifier les sections critiques et les ressources critiques. Ensuite, nous avons utilisé des outils comme les sémaphores pour limiter l'accès aux sections ou ressources critiques et synchroniser les threads.
Ces concepts sont cruciaux pour concevoir des applications multi-threadées robustes et performantes. En appliquant ces principes, nous avons appris à gérer efficacement les ressources partagées dans des environnements concurrents.
