Google Maps Test Automation with Appium — Djamo Technical Assignment

* Contexte

Ce projet correspond à un test technique demandé par l’équipe Djamo.

Il consiste à écrire un scénario automatisé sur l’application Google Maps.

L’objectif est de proposer une implémentation réaliste, claire et maintenable, tout en respectant plusieurs exigences techniques :
	•	Gérer les délais de chargement de l’application
	•	Structurer le projet de manière réutilisable et évolutive
	•	Vérifier le bon comportement à l’aide d’assertions robustes
	•	Documenter comment l’intégrer dans une pipeline d’intégration continue (CI)

Ce projet se concentre sur un seul scénario fonctionnel, mais conçu comme s’il faisait partie d’une suite de tests plus large.

⸻

Objectifs fonctionnels

Le test doit effectuer les actions suivantes sur l’application Google Maps :
	1.	Ouvrir l’application Google Maps sur Android.
	2.	Rechercher la ville “Abidjan”.
	3.	Lancer un itinéraire vers Abidjan.
	4.	Modifier le point de départ en “Dakar”.
	5.	Vérifier que la carte se charge correctement avec les nouveaux paramètres.

Un screenshot final est capturé pour valider visuellement le résultat.

⸻

 * Stack technique utilisée
	•	Java (JDK 21) – langage principal du projet
	•	Appium 2.x avec le driver UIAutomator2 – pour l’automatisation des actions sur l’application mobile Android
	•	Appium Server (exécuté en local) – pour orchestrer la communication entre les tests et le device
	•	TestNG – framework de test pour la structuration, la gestion de suites et les DataProviders
	•	Maven – gestionnaire de projet et de dépendances
	•	Android Studio + AVD (émulateur Android) – pour simuler un smartphone Android
	•	Appium Doctor – pour vérifier la bonne configuration de l’environnement Appium/Android
	•	Eclipse IDE – environnement de développement utilisé pour écrire, lancer et déboguer les tests
	•	Appium Inspector – Outil graphique pour explorer et identifier les éléments UI de l’application mobile

⸻
Prérequis système (variables d’environnement)

Pour garantir le bon fonctionnement de l’exécution locale et du lancement automatique d’Appium, vous devez configurer plusieurs variables d’environnement selon votre système d’exploitation.
⸻
JAVA_HOME
Indispensable pour que Maven, Appium et Android puissent fonctionner.
macOS / Linux :
export JAVA_HOME=$(/usr/libexec/java_home)
Windows :Définir dans :
Panneau de configuration → Système → Variables d’environnement
Nom : JAVA_HOME
Valeur : C:\Program Files\Java\jdk-21
⸻
ANDROID_HOME
Permet aux outils comme adb et Appium d’accéder au SDK Android.
macOS :
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH
Linux :
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH
Windows :
Ajoutez dans les variables utilisateur :
	•	ANDROID_HOME → C:\Users\VotreNom\AppData\Local\Android\Sdk
	•	Ajoutez aussi %ANDROID_HOME%\platform-tools dans la variable PATH.
⸻
APPIUM_MAIN_JS
Permet de lancer Appium automatiquement depuis Java sans ouvrir manuellement le serveur.
Étape 1 – Trouver le chemin :
macOS / Linux :
find $(npm root -g) -name main.js | grep appium/build/lib
Exemple de résultat :
/usr/local/lib/node_modules/appium/build/lib/main.js
Étape 2 – Déclarer la variable
macOS / Linux :
export APPIUM_MAIN_JS=/usr/local/lib/node_modules/appium/build/lib/main.js
Ajoutez cette ligne dans ~/.zshrc, ~/.bashrc ou ~/.bash_profile.

Windows :

$env:APPIUM_MAIN_JS = "C:\Users\VotreNom\AppData\Roaming\npm\node_modules\appium\build\lib\main.js"
Ou ajoutez-la comme variable système.
⸻
Vérification
macOS / Linux :
echo $JAVA_HOME
echo $ANDROID_HOME
echo $APPIUM_MAIN_JS

Sous Windows PowerShell :

echo $Env:JAVA_HOME
echo $Env:ANDROID_HOME
echo $Env:APPIUM_MAIN_JS
⸻

* Instructions pour exécuter le test

   Prérequis locaux (environnement de test)

Pour exécuter ce projet en local, vous devez avoir les outils suivants installés et configurés :
	1.	Java JDK 17 ou supérieur
	•	Vérifiez avec : java -version
	•	Téléchargement : https://adoptium.net
	2.	Maven (gestionnaire de projet Java)
	•	Vérifiez avec : mvn -v
	•	Téléchargement : https://maven.apache.org/download.cgi
	3.	Node.js et NPM (nécessaires pour installer Appium)
	•	Vérifiez avec : node -v et npm -v
	•	Téléchargement : https://nodejs.org/
	4.	Appium Server 2.x
	•	Installation via NPM :
	    npm install -g appium
		Vérification : appium -v
	5.	Appareil Android
	•	Option 1 : Un émulateur Android créé via Android Studio (AVD)
	•	Option 2 : Un véritable appareil Android connecté en USB avec le débogage activé
	6.	Android Studio (pour créer des émulateurs et configurer le SDK Android)
	•	Téléchargement : https://developer.android.com/studio
	7.	Appium Doctor (outil de diagnostic Appium)
	•	Installation : npm install -g appium-doctor
	•	Utilisation : appium-doctor pour vérifier que tout est prêt
	8.  Appium Inspector est recommandé si l’utilisateur souhaite  modifier les éléments UI utilisés dans les scripts. 
	    Il n’est pas obligatoire pour exécuter les tests.
	•   Téléchargement depuis la page officielle : https://github.com/appium/appium-inspector/releases
        Installez le fichier .dmg (Mac) ou .exe (Windows), puis lancez-le.
	        

* Structure du projet
mobile-tests/
├── pom.xml                                # Fichier Maven de gestion des dépendances
├── README.md                              # Documentation principale du projet
├── testng.xml                             # Plan d’exécution TestNG
├── src/test/resources/
│   ├── capabilities/
│   │   └── android_google_maps.json       # Capabilities Appium selon le profil (device, app, etc.)
│   └── data/
│       └── search_route.json              # Données d’entrée pour la recherche d’itinéraire
├── reports                                # Contient les rapports HTML générés par ExtentReports                            
├── screenshots                            # Capture d’écran automatique (succès/échec) pour chaque test
└── src/test/java/com/djamo/qa/
    ├── base/
    │   └── DriverFactory.java             # Gestionnaire de driver
    ├── pages/
    │   └── SearchAndNavigatePage.java     # Classe représentant la page Google Maps (Page Object Model)
    ├── tests/
    │   └── SearchAndNavigateTest.java     # Classe de test exécutant le scénario demandé (Recherche + Itinéraire)
    └── utils/
        ├── AppiumServerManager.java       # Classe utilitaire pour démarrer/arrêter Appium Server localement (mode programmatique)
        ├── ExtentReporterNG.java          # Configuration du rapport de tests ExtentReports pour TestNG   
        ├── ExtentTestManager.java         # Gestion centralisée des tests et logs pour les rapports ExtentReports   
        ├── Listeners.java                 # Listeners TestNG pour screenshots, logs, etc.
        └── Retry.java                    # Relance automatique des tests instables (flaky), configurable (ex: 1 retry max)
*Lancement local (via Appium)

Avant de lancer les tests automatisés, suivez les étapes ci-dessous :

1. Cloner le projet depuis GitHub
•Ouvrir un terminal et exécuter : git clone <URL_DU_DEPOT_PRIVE>
                                  cd mobile-tests

•Ouvrir le dossier mobile-tests dans Eclipse ou IntelliJ IDEA.
•Laisser Maven mettre à jour les dépendances automatiquement (pom.xml).
•Si nécessaire : clic droit sur le projet → Maven → Update Project.
2. Créer un appareil Android virtuel (AVD)
•Ouvrir Android Studio.
•Aller dans More Actions → Device Manager.
•Cliquer sur Create Virtual Device.
•Exemple : Pixel 7 / API Level 34 (Android 14).
•Lancer l'appareil virtuel.
•Vérifier qu’il est détecté avec : adb devices
3.Lancer manuellement Appium Server (option 1)
•Ouvrir un terminal séparé et taper : appium
4.Exécuter les tests
•Lancer les tests depuis la racine du projet avec : mvn clean test -DsuiteXmlFile=testng.xml

5.Lancer Appium automatiquement (option 2)
Vous pouvez éviter de lancer Appium manuellement en utilisant :
mvn clean test -DsuiteXmlFile=testng.xml -Dautostartappium=true
N_B: L’émulateur doit être déjà lancé avant d’utiliser cette commande.

⸻
* Résultats
	•	ExtentReports HTML est généré automatiquement à la fin de chaque exécution, avec les détails des tests et les captures d’écran intégrées pour les échecs.
	•	Le fichier HTML se trouve dans le dossier reports/
	•	Captures d’écran :
	•	En cas de succès, elles sont enregistrées dans screenshots/SearchAndNavigateTest/
	•	En cas d’échec, elles sont stockées dans screenshots/
	•	Les logs TestNG sont visibles en temps réel dans la console Maven (mvn clean test ...)
	•	Chaque événement de test (succès/échec) est enrichi dans ExtentReports
⸻

* Architecture et approche

- Principes de conception
	•	Page Object Model (POM)
→ La classe SearchAndNavigatePage regroupe toutes les actions liées à l’interface Google Maps (recherche, clics, validation), permettant une séparation claire entre la logique métier et les tests.
	•	Driver Factory dédiée
→ DriverFactory.java instancie les drivers Appium en fonction du profil défini (Android), ce qui facilite l’évolution vers d’autres plateformes (iOS, par exemple).
	•	Paramétrage via TestNG/XML
→ testng.xml permet de spécifier le profil de test (android_google_maps) et d’exécuter plusieurs tests en parallèle si nécessaire.
	•	Rapports d’exécution avec ExtentReports
→ Les classes ExtentReporterNG et ExtentTestManager génèrent automatiquement un rapport HTML clair avec logs, statuts, et captures d’écran intégrées.
	•	Listeners TestNG personnalisés
→ La classe Listeners capte chaque étape de test pour :
	•	enregistrer les captures d’écran en cas de succès (dans screenshots/search_and_navigate/) ou d’échec (dans screenshots/)
	•	ajouter les logs dans le rapport HTML
	•	Démarrage automatique d’Appium
→ AppiumServerManager permet de lancer ou arrêter Appium Server automatiquement depuis Java, sans avoir à le démarrer manuellement.

- Robustesse et reproductibilité
	•	Test stable malgré les délais de chargement
→ Le test utilise des attentes explicites (WebDriverWait) pour attendre l’apparition des éléments critiques (carte, boutons, etc.) et éviter les faux négatifs.
	•	Utilisation de données externes
→ Les termes de recherche (Abidjan, Dakar) sont stockés dans search_route.json, permettant de modifier facilement les tests sans toucher au code.
	•	Code modulaire et réutilisable
→ La logique est découpée proprement entre base, pages, tests et utils pour permettre l’extension à d’autres scénarios (ex. : ajout de favoris, navigation en voiture, etc.).


⸻

Excellent point Sylvia 🙌🏼 ! Voici une version revue, structurée et complète du bloc CI/CD dans ton README, avec deux sections distinctes :

⸻

* Intégration Continue (CI/CD)

                                        ⸻     ⸻    ⸻
Partie 1 – Pipeline local (émulateur Android + Appium local + Jenkins)

Objectif
     Permettre aux équipes QA ou dev de :
	•	Lancer automatiquement les tests Appium en local (via Jenkins)
	•	Générer des rapports d’exécution lisibles
	•	Automatiser les captures d’écran, sans configuration complexe

Outils utilisés
Outil	                             Rôle
Jenkins	                             Orchestration du pipeline local : déclenche tests, archive les rapports
Appium Server 2.x	                 Serveur d’automatisation pour tests Android
Android Studio + AVD	             Émulateur local pour exécuter les tests
Java 21 + Maven	                     Langage + gestion de dépendances & exécution de tests
TestNG	                             Framework de test Java (gestion des suites, DataProviders)
ExtentReports	                     Rapports HTML personnalisés avec logs et captures
Shell (bash)	                     Démarrage automatique de l’émulateur avant tests

Étapes dans Jenkins
	1.	Préparation
	•	Créer un job Jenkins mobile-tests-local
	•	Ajouter ce script dans “Execute shell” :
       #!/bin/bash
       # Démarrage de l'émulateur
        emulator -avd Pixel_7_API_34 &
       # Attendre que l'appareil soit prêt
         adb wait-for-device
        # Lancer Appium local appium &  # Exécuter les tests avec autostart Appium
         mvn clean test -DsuiteXmlFile=testng.xml -Dautostartappium=true
    2.	Post-exécution
	•	Archiver reports/ et screenshots/ dans Jenkins
	•	Optionnel : envoyer un mail ou Slack si test échoue

Fréquence d’exécution

Scénario	    Fréquence
main	       chaque nuit (scheduled build)
feature/*	   à la demande manuelle
release/*	   après merge

Visualisation des résultats
	•	 reports/ExtentReport.html : rapport interactif
	•	 screenshots/ : captures automatiques (succès + erreurs)
	•	 Jenkins : statut général + historique des builds

                                             ⸻     ⸻    ⸻

 Partie 2 – Pipeline cloud avec BrowserStack

Objectif
	•	Exécuter les tests Appium sur vrais appareils Android/iOS dans le cloud
	•	Simuler des usages réels (connexion, lenteurs réseau, versions Android variées)
	•	Exécuter en parallèle sur plusieurs devices = gain de temps

Outils utilisés

Outil	                                  Rôle
GitHub Actions / GitLab CI	              Déclencheur cloud pour automatiser les tests
BrowserStack App Automate	              Exécution sur des smartphones réels Android/iOS
Maven + TestNG	                          Exécution de tests et gestion des suites
ExtentReports/BrowserStack Dashboard	  Rapport visuel : logs, vidéo, screenshots

   Comment utiliser BrowserStack ?
	1.	Créer un compte sur https://browserstack.com
	2.	Générer un fichier src/test/resources/capabilities/android_browserstack.json :

{
  "platformName": "Android",
  "deviceName": "Samsung Galaxy S22",
  "app": "bs://<app-id>",
  "bstack:options": {
    "projectName": "Google Maps Appium Demo",
    "buildName": "v1 - Demo Run",
    "sessionName": "Search & Navigate"
  }
}

	3.	Passer -Dplatform=browserstack au moment du test

Exemple de pipeline GitHub Actions

name: Appium BrowserStack Tests

on:
  push:
    branches: [main, 'feature/**']
  pull_request:
    branches: [release/**]

jobs:
  appium-cloud-tests:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v3

      - name: Setup Java & Maven
        uses: actions/setup-java@v3
        with:
          java-version: '21'

      - name: Run Appium tests on BrowserStack
        env:
          BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
          BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
        run: |
          mvn clean test -DsuiteXmlFile=testng.xml -Dplatform=browserstack

Fréquence recommandée

Branche	        Action
main	        Nightly
feature/*	    À chaque push
release/*	    Après merge + tag

Visualisation des résultats
	•	Dashboard BrowserStack :
	•	Résultat (echec/succès)
	•	Screenshots
	•	Vidéo
	•	Logs
	•	reports/ généré localement avec ExtentReports si besoin

En résumé

Pipeline local	                 Pipeline cloud (BrowserStack)
Jenkins	                          GitHub Actions / GitLab CI
AVD (émulateur local)	          Vrais devices cloud
Appium local	                  Appium via BrowserStack
Exécution simple	             Exécution en parallèle
Rapport local HTML	              Dashboard BrowserStack + rapport HTML optionnel

⸻

Bien sûr Sylvia ! Voici la section 🛠️ Erreurs fréquentes rencontrées et résolutions, à inclure dans ton README.md pour anticiper les blocages des utilisateurs (ou testeurs manuels) et démontrer ta rigueur :

⸻

🛠️ Erreurs fréquentes & résolutions

Problème courant	                                     Résolution / Astuce
zsh: command not found: emulator	                     ➤ Ajoutez Android SDK Tools (emulator) au PATH :  export PATH=$PATH:$ANDROID_HOME/emulator dans ~/.zshrc, puis source ~/.zshrc.
Appareil Android non détecté (adb devices vide)	         ➤ Vérifiez que l’AVD est lancé depuis Android Studio ou via terminal.  ➤ Autorisez le débogage USB si appareil réel.
appium ne démarre pas (command not found)	             ➤ Assurez-vous d’avoir installé Appium en global :  npm install -g appium
Tests échouent sans logs clairs	                         ➤ Vérifiez que le serveur Appium est bien lancé avant d’exécuter les tests.  ➤ Activez les logs détaillés avec -Ddebug=true si prévu.
L’élément UI est introuvable	                         ➤ Vérifiez que l’appareil est bien connecté, l’UI visible, et les délais de chargement sont gérés avec wait.
Le test ne prend pas de capture d’écran	                 ➤ Vérifiez que le dossier screenshots/ existe ou est accessible en écriture.
Problème avec mvn ou pom.xml	                         ➤ Lancez mvn clean install pour résoudre les dépendances. Vérifiez que JAVA_HOME pointe bien vers le JDK 17+.
ExtentReports ne génère pas de fichier	                 ➤ Vérifiez le chemin du fichier dans ExtentReporterNG.java. Le répertoire reports/ doit exister.


⸻


* Limites connues / Pistes futures

 Limites actuelles observées

Ces points n’ont pas été résolus dans le cadre de cet exercice, mais sont importants à mentionner :
	•	Absence de gestion multilingue (anglais/français) de l’UI Google Maps
Certains éléments changent selon la langue du système Android. Cela peut rendre les sélecteurs instables sur certains appareils francophones.
	•	Problème de fiabilité UI sur Android 11
Le composant cartographique de Google Maps n’est pas détecté de manière fiable sur Android 11. Les tests échouent car la carte ne se charge pas totalement, ce qui empêche la validation visuelle. Ce comportement semble lié à la version d’Android.
	•	Pas encore de CI/CD réelle
L’intégration GitHub Actions ou Jenkins est documentée, mais non implémentée par manque de temps ou de contraintes d’environnement.
	•	Non couverture de tous les scénarios de navigation (trafic, vélo, transport, etc.)
Le test couvre uniquement un itinéraire voiture simple depuis une recherche textuelle.

⸻

* Améliorations futures possibles

Si le projet devait être poursuivi dans un contexte réel, voici les solutions ou évolutions à envisager :
	•	CI/CD automatisée avec GitHub Actions et Jenkins
Exécution continue avec branche main, tests sur plusieurs devices en parallèle.
	•	Support multiplateforme (iOS + Android) (Déja fait)
	•	Détection dynamique de langue ou device
Adaptation automatique des sélecteurs selon la langue du système ou version Android détectée.
	•	Gestion avancée des captures
Centraliser les captures selon le statut (passed vs failed) + archiver dans les rapports.
	•	Tests supplémentaires :
	- Scénarios avec “transport en commun” ou “trafic en temps réel”
	- Validation du chargement de la carte sur différents types de réseau
	- Simulation d’erreurs réseaux via outils comme Charles Proxy ou ADB shell
	- Rapport de couverture de test
Générer un rapport pour visualiser les classes/méthodes/test cases effectivement couverts.

⸻

Auteur : Sylvia N’Guessan

Consultante QA | Test automation 

⸻

    Liens utiles
	•	Appium Docs
	•	BrowserStack App Automate
	•	TestNG XML suite config
	•	GitHub Actions

⸻

 Check de complétion
	•	Test e2e Google Maps conforme aux specs
	•	Architecture maintenable (POM, config, logs)
	•	Script local & cloud
	•	README complet, clair, contextualisé Djamo
	•	Livraison GitHub prête avec CI documentation
	
	---

Merci pour l’opportunité et bonne lecture

> "Build for reuse, test for resilience."