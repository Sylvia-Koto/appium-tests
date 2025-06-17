Google Maps Test Automation with Appium — Djamo Technical Assignment

* Contexte (Version Française)

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


* Instructions pour exécuter le test

   Prérequis (environnement de test)

Pour exécuter ce projet en local, vous devez avoir les outils suivants installés et configurés : 
(Pour garantir le bon fonctionnement de l’exécution locale et du lancement automatique d’Appium, vous devez configurer plusieurs variables d’environnement selon votre système d’exploitation.)
	1.	Java JDK 17 ou supérieur
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
	•	Vérifiez avec : java -version
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
	5.	Appareil Android : Un émulateur Android créé via Android Studio (AVD)
	6.	Android Studio (pour créer des émulateurs et configurer le SDK Android)
	•	Téléchargement : https://developer.android.com/studio
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
	7.	Appium Doctor (outil de diagnostic Appium)
	•	Installation : npm install -g appium-doctor
	•	Utilisation : appium-doctor pour vérifier que tout est prêt
	8.  Appium Inspector est recommandé si l’utilisateur souhaite  modifier les éléments UI utilisés dans les scripts. 
	    Il n’est pas obligatoire pour exécuter les tests.
	•   Téléchargement depuis la page officielle : https://github.com/appium/appium-inspector/releases
        Installez le fichier .dmg (Mac) ou .exe (Windows), puis lancez-le.
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
     9. Installer Eclipse ou Intellij pour mettre à jour le projet via update ou apporter des modifications.
	    (cette étape est facultative)
	 10. Si votre CLI ne reconnait pas mvn, pensez à mettre	le chemin de maven dand le PATH 
          la variable d’environnement MAVEN_HOME
          Linux /  macOS
	      1.	Ouvrez un terminal
	      2.	Localisez Maven (par ex. /opt/homebrew/Cellar/maven/3.x.x/libexec ou /usr/share/maven)
	      3.	Ajoutez ceci à votre fichier ~/.zshrc, ~/.bashrc ou ~/.profile :

          export MAVEN_HOME="/chemin/vers/votre/maven"
          export PATH="$MAVEN_HOME/bin:$PATH"
          4.	Rechargez la configuration : source ~/.zshrc    # ou ~/.bashrc
          5.	Vérifiez que tout fonctionne :
                echo $MAVEN_HOME
               mvn -version
           Windows
	      1.	Allez dans Panneau de configuration > Système > Paramètres système avancés > Variables d’environnement
	      2.	Dans la section “Variables système” :
	             •	Cliquez sur Nouveau
	             •	Nom : MAVEN_HOME
	             •	Valeur : C:\Program Files\Apache\Maven\apache-maven-3.x.x
	    3.	Éditez la variable Path :
	             •	Cliquez sur Modifier
	             •	Ajoutez une nouvelle ligne : %MAVEN_HOME%\bin
         4.	Ouvrez un nouveau terminal (cmd ou PowerShell) et vérifiez : echo %MAVEN_HOME%   / mvn -version

⸻	        

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

1. Cloner le projet depuis GitHub ou télécharger le zip du projet 
•Ouvrir le dossier mobile-tests dans Eclipse ou IntelliJ IDEA.
•Laisser Maven mettre à jour les dépendances automatiquement (pom.xml).
•Si nécessaire : clic droit sur le projet → Maven → Update Project.
2. Créer un appareil Android virtuel (AVD)
•Ouvrir Android Studio.
•Aller dans More Actions → Device Manager.
•Cliquer sur Create Virtual Device : Pixel 7 / API Level 34 (Android 14).
•Lancer l'appareil virtuel.
•Vérifier qu’il est détecté avec : adb devices
3.Lancer manuellement Appium Server (option 1)
•Ouvrir un terminal séparé et taper : appium
4.Exécuter les tests
•Lancer les tests depuis la racine du projet avec : mvn clean test -DsuiteXmlFile=testng.xml ou mvn clean test ou mmvn test
5.Lancer Appium automatiquement (option 2)
Vous pouvez éviter de lancer Appium manuellement en utilisant : mais il faut avoir paramétré la variable APPIUM_MAIN_JS
mvn clean test -DsuiteXmlFile=testng.xml -DautoStartAppium=true ou mvn clean test -DautoStartAppium=true 
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

* Intégration Continue (CI/CD) (Détail sur le pipeline à mettre en place)

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

N-B: pour exécuter plusieurs tests mobiles en parallèle, il existe deux grandes approches :
          1. Plusieurs Appium Servers (approche classique)
          C’est la méthode la plus fiable en local, surtout si vous voulez faire tourner plusieurs appareils en parallèle (réels ou émulateurs). Appium Server ne peut gérer qu’un seul appareil par instance.
          Donc pour deux devices: Démarrer 2 serveurs Appium sur des ports différents
          
		  2. Appium + Test orchestrator ou grid (Avancé)
             a. Appium et selenium grid
             b. Appium avec Docker :créer plusieurs conteneurs Appium prêts à l’emploi avec un docker-compose.yml

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
Extents Reports HTML	          Dashboard BrowserStack + rapport HTML optionnel

⸻

 Erreurs & résolutions

Problème courant	                                     Résolution / Astuce

Appareil Android non détecté (adb devices vide)	         ➤ Vérifiez que l’AVD est lancé depuis Android Studio ou via terminal.
appium ne démarre pas (command not found)	             ➤ Assurez-vous d’avoir installé Appium en global :  npm install -g appium
Tests échouent sans logs clairs	                         ➤ Vérifiez que le serveur Appium est lancé avant d’exécuter                                                  
                                                         ➤ Activez les logs détaillés avec -Ddebug=true si prévu.
L’élément UI est introuvable	                         ➤ Vérifiez que l’appareil est bien connecté, l’UI visible, et les délais  de chargement sont gérés avec wait.
Le test ne prend pas de capture d’écran	                 ➤ Vérifiez que le dossier screenshots/ existe ou est accessible en écriture.
Problème avec mvn ou pom.xml	                         ➤ Lancez mvn clean install pour résoudre les dépendances. Vérifiez que JAVA_HOME pointe bien vers le JDK 17+.
ExtentReports ne génère pas de fichier	                 ➤ Vérifiez le chemin du fichier dans ExtentReporterNG.java. Le répertoire reports/ doit exister.


⸻


* Limites connues / Pistes futures

 Limites actuelles observées

Ces points n’ont pas été résolus dans le cadre de cet exercice, mais sont importants à mentionner :
	•	Absence de gestion multilingue (anglais/français) de l’UI Google Maps
Certains éléments changent selon la langue du système Android. Cela peut rendre les sélecteurs instables sur certains appareils francophones.
	•	Instabilités sur Android 10 et 11
Les tests échouent sur Android 10 et 11 en raison de variations UI (locators, délais de rendu).
Un temps supplémentaire est nécessaire pour analyser et adapter les sélecteurs.
	•	Pas encore de CI/CD réelle
L’intégration GitHub Actions ou Jenkins est documentée, mais non implémentée par manque de temps ou de contraintes d’environnement.
	•	Non couverture de tous les scénarios de navigation (trafic, vélo, transport, etc.)
Le test couvre uniquement un itinéraire voiture simple depuis une recherche textuelle.

⸻

* Améliorations futures possibles

Si le projet devait être poursuivi dans un contexte réel, voici les solutions ou évolutions à envisager :
	•	CI/CD automatisée avec GitHub Actions et Jenkins
Exécution continue avec branche master, tests sur plusieurs devices en parallèle.
	•	Support multiplateforme (iOS + Android)
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


Google Maps Test Automation with Appium — Djamo Technical Assignment

* Context (English Version)

This project corresponds to a technical test requested by the Djamo team .

It involves writing an automated scenario on the Google Maps application .

The objective is to propose a realistic, clear and maintainable implementation, while respecting several technical requirements:
• Manage application loading times
• Structure the project in a reusable and scalable way
• Verify correct behavior using robust assertions
• Document how to integrate it into a continuous integration (CI) pipeline

This project focuses on a single functional scenario, but designed as if it were part of a larger test suite.

⸻

Functional objectives

The test should perform the following actions on the Google Maps application :
Maps app on Android.
2. Search for the city “Abidjan”.
3. Start a route to Abidjan.
4. Change the starting point to “Dakar”.
5. Verify that the map loads correctly with the new settings.

A final screenshot is captured to visually validate the result.

⸻

 * Technical stack used
• Java (JDK 21) – main language of the project
• Appium 2.x with UIAutomator2 driver – for automating actions on Android mobile application
• Appium Server (running locally) – to orchestrate communication between tests and the device
• TestNG – test framework for structuring, suite management and DataProviders
• Maven – project and dependency manager
• Android Studio + AVD (Android emulator) – to simulate an Android smartphone
• Appium Doctor – to check the correct configuration of the Appium /Android environment
• Eclipse IDE – development environment used to write, run and debug tests
• Appium Inspector – Graphical tool to explore and identify UI elements of the mobile application

⸻

* Instructions for running the test

Prerequisites (test environment)

To run this project locally, you need to have the following tools installed and configured:
(To ensure proper local execution and automatic launch of Appium , you need to configure several environment variables depending on your operating system.)
1. Java JDK 17 or higher
⸻
JAVA_HOME
Required for Maven, Appium and Android to work.
		 macOS / Linux:
export JAVA_HOME=$(/ usr / libexec / java_home )
Windows: Set in:
Control Panel → System → Environment Variables
Name: JAVA_HOME
Value: C:\Program Files\Java\jdk-21
⸻
• Check with: java -version
2. Maven (Java project manager)
• Check with: mvn -v
• Download: https://maven.apache.org/download.cgi
3. Node.js and NPM (required to install Appium )
• Check with: node -v and npm -v
• Download: https://nodejs.org/
4. Appium Server 2.x
• Installation via NPM:
		npm install -g appium
Verification: appium -v

⸻
APPIUM_MAIN_JS
Allows you to launch Appium automatically from Java without manually opening the server.
Step 1 – Find the path:
		 macOS / Linux:
		 find $( npm root -g) - name main.js | grep appium / build / lib
Example result:
/ usr /local/lib/ node_modules / appium / build /lib/main.js
Step 2 – Declare the variable
		 macOS / Linux:
export APPIUM_MAIN_JS=/usr/local/lib/node_modules/appium/build/lib/main.js
Add this line to ~/ .zshrc , ~/ .bashrc or ~/ .bash_profile .
Windows:
$ env:APPIUM_MAIN_JS = "C:\Users\YourName\AppData\Roaming\npm\node_modules\appium\build\lib\main.js"
Or add it as a system variable.
⸻
5. Android Device: An Android emulator created via Android Studio (AVD)
6. Android Studio (to create emulators and configure the Android SDK)
• Download: https://developer.android.com/studio
⸻
ANDROID_HOME
Allows tools like adb and Appium to access the Android SDK.
		 macOS :
export ANDROID_HOME=$HOME/Library/Android/ sdk
export PATH=$ANDROID_HOME/platform- tools :$PATH
Linux:
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$ANDROID_HOME/platform- tools :$PATH
Windows:
Add in user variables:
• ANDROID_HOME → C:\Users\YourName\AppData\Local\Android\Sdk
• Also add %ANDROID_HOME%\platform- tools to the PATH variable.
⸻
7. Appium Doctor ( Appium diagnostic tool )
• Installation: npm install -g appium-doctor
• Use: appium-doctor to check that everything is ready
8. Appium Inspector is recommended if the user wants to modify UI elements used in scripts.
It is not mandatory to run the tests.
• Download from the official page: https://github.com/appium/appium-inspector/releases
.dmg (Mac) or .exe (Windows) file , then launch it.
⸻
Verification
		 macOS / Linux:
		 echo $JAVA_HOME
		 echo $ANDROID_HOME
		 echo $APPIUM_MAIN_JS

In Windows PowerShell:
		 echo $ Env:JAVA_HOME
		 echo $ Env:ANDROID_HOME
		 echo $ Env:APPIUM_MAIN_JS
⸻
9. Install Eclipse or Intellij to update the project or make changes.
(this step is optional)
10. If your CLI does not recognize mvn , consider putting the path to maven and the PATH
the MAVEN_HOME environment variable
Linux / macOS
1. Open a terminal
2. Locate Maven ( e.g. /opt/homebrew/Cellar/maven/3.xx/libexec or / usr / share / maven )
3. Add this to your ~/ .zshrc , ~/ .bashrc or ~/.profile file:

export MAVEN_HOME="/path/to/your/ maven "
export PATH="$MAVEN_HOME/bin:$PATH"
4. Reload the configuration: source ~/. zshrc # or ~/. bashrc
5. Check that everything is working:
				echo $MAVEN_HOME
			   mvn -version
Windows
1. Go to Control Panel > System > Advanced System Settings > Environment Variables
2. In the “System Variables” section:
• Click New
• Name: MAVEN_HOME
• Value: C:\Program Files\Apache\Maven\apache-maven-3.xx
3. Edit the Path variable:
• Click Edit
• Add a new line: %MAVEN_HOME%\bin
4. Open a new terminal (cmd or PowerShell) and check: echo %MAVEN_HOME% / mvn -version

⸻

* Project structure
mobile-tests/
├── pom.xml # Maven dependency management file
├── README.md # Main project documentation
TestNG Execution Plan
├── src/test/ resources /
│ ├── capabilities /
│ │ └── android_google_maps.json # Capabilities Appium according to the profile ( device , app, etc.)
│ └── data/
│ └── search_route.json # Input data for route search
├── reports # Contains the HTML reports generated by ExtentReports                            
├── screenshots # Automatic screenshot (success/failure) for each test
└── src/test/java/com/ djamo / qa /
├── base/
│ └── DriverFactory.java # Driver Manager
├── pages/
│ └── SearchAndNavigatePage.java # Class representing the Google Maps page (Page Object Model)
├── tests/
│ └── SearchAndNavigateTest.java # Test class executing the requested scenario (Search + Route)
└── utils /
├── AppiumServerManager.java # Utility class to start/stop Appium Server locally (programmatic mode)
├── ExtentReporterNG.java # Configuring ExtentReports for TestNG   
ExtentReports reports   
├── Listeners.java # Listeners TestNG for screenshots , logs, etc
└── Retry.java flaky ) tests , configurable (e.g. 1 retry max)

*Local launch (via Appium )

Before running automated tests, follow the steps below:

1. Clone the project from GitHub or download the project zip
•Open the mobile-tests folder in Eclipse or IntelliJ IDEA.
•Let Maven update dependencies automatically (pom.xml).
•If necessary: right-click on the project → Maven → Update Project.
2. Create a Virtual Android Device (AVD)
•Open Android Studio.
•Go to More Actions → Device Manager.
•Click on Create Virtual Device : Pixel 7 / API Level 34 (Android 14).
•Launch the virtual device.
•Check that it is detected with: adb devices
3.Manually launch Appium Server (option 1)
•Open a separate terminal and type: appium
4. Run the tests
•Run the tests from the project root with: mvn clean test -DsuiteXmlFile=testng.xml or mvn clean test or mmvn test
5. Launch Appium automatically (option 2)
You can avoid launching Appium manually by using: but you must have set the APPIUM_MAIN_JS variable
mvn clean test -DsuiteXmlFile=testng.xml - DautoStartAppium = true or mvn clean test - DautoStartAppium = true 
NB: The emulator must already be running before using this command.

⸻
* Results
• ExtentReports HTML is automatically generated at the end of each run, with test details and embedded screenshots for failures.
• The HTML file is located in the reports/ folder
• Screenshots:
• If successful, they are saved in screenshots / SearchAndNavigateTest /
• If unsuccessful, they are stored in screenshots /
TestNG logs are visible in real time in the Maven console ( mvn clean test ...)
• Each test event (success/failure) is enriched in ExtentReports
⸻

* Architecture and approach

- Design principles
• Page Object Model (POM)
SearchAndNavigatePage class groups together all actions related to the Google Maps interface (search, clicks, validation), allowing a clear separation between business logic and tests.
Dedicated Driver Factory
Appium drivers based on the defined profile (Android), which makes it easier to upgrade to other platforms (iOS, for example).
• Configuration via TestNG /XML
→ testng.xml allows you to specify the test profile ( android_google_maps ) and run multiple tests in parallel if necessary.
• Execution reports with ExtentReports
ExtentReporterNG and ExtentTestManager classes automatically generate a clear HTML report with logs, statuses, and embedded screenshots.
• Listeners Custom TestNG
→ The Listeners class captures each test step to:
• save screenshots on success (in screenshots / search_and_navigate /) or failure (in screenshots /)
• add the logs in the HTML report
• Automatic start of Appium
→ AppiumServerManager allows you to start or stop Appium Server automatically from Java, without having to start it manually.

- Robustness and reproducibility
• Stable test despite loading delays
→ The test uses explicit waits ( WebDriverWait ) to wait for critical elements (map, buttons, etc.) to appear and avoid false negatives.
• Use of external data
→ Search terms (Abidjan, Dakar) are stored in search_route.json , allowing tests to be easily modified without touching the code.
• Modular and reusable code
→ The logic is cleanly split between base, pages, tests and utilities to allow extension to other scenarios (e.g.: adding favorites, car navigation, etc.).

⸻

* Continuous Integration (CI/CD) (Details on the pipeline to be implemented)

⸻ ⸻ ⸻
Part 1 – Local Pipeline (Android Emulator + Local Appium + Jenkins)

Objective
Allow QA or dev teams to:
Appium tests locally (via Jenkins)
• Generate readable execution reports
• Automate screenshots, without complex configuration

Tools used
Role Tool
Jenkins Local Pipeline Orchestration: Triggers Tests, Archives Reports
Appium Server 2.x Automation Server for Android Testing
Android Studio + AVD Local Emulator to run the tests
Java 21 + Maven Language + Dependency Management & Test Execution
TestNG Java test framework (suite management, DataProviders )
ExtentReports Custom HTML reports with logs and captures
Shell ( bash ) Automatic start of the emulator before testing

Steps in Jenkins
1. Preparation
• Create a Jenkins mobile-tests-local job
• Add this script in “ Execute shell ”:
#!/bin/bash
# Starting the emulator
		emulator - avd Pixel_7_API_34 &
# Wait for the device to be ready
		 adb wait for device
# Start Appium local appium & # Run tests with autostart Appium
		 mvn clean test -DsuiteXmlFile=testng.xml - Dautostartappium = true
2. Post-execution
• Archive reports/ and screenshots / in Jenkins
• Optional: send an email or Slack if test fails

Frequency of execution

Scenario Frequency
hand every night ( scheduled build )
feature /* on manual request
release/* after merge

Visualizing the results
• reports/ExtentReport.html: interactive report
• screenshots /: automatic captures (successes + errors)
• Jenkins: general status + build history

NB: To run multiple mobile tests in parallel, there are two main approaches:
1. Multiple Appium Servers (Classic Approach)
This is the most reliable method locally, especially if you want to run multiple devices in parallel (real or emulated). Appium Server can only manage one device per instance.
So, for two devices: Start two Appium servers on different ports.

2. Appium + Test Orchestrator or Grid (Advanced)
a. Appium and Selenium Grid
b. Appium with Docker: Create multiple ready-to-use Appium containers with a docker-compose.yml file.

⸻ ⸻ ⸻

Part 2 – Cloud Pipeline with BrowserStack

Objective
Appium tests on real Android/iOS devices in the cloud
• Simulate real-life uses (connection, network slowness, various Android versions)
• Run in parallel on multiple devices = save time

Tools used

Role Tool
GitHub Actions / GitLab CI Cloud trigger to automate tests
BrowserStack App Automate Running on Real Android/iOS Smartphones
Maven + TestNG Test execution and suite management
ExtentReports / BrowserStack Dashboard Visual report: logs, video, screenshots

How to use BrowserStack ?
1. Create an account at https://browserstack.com
2. Generate a src/test/ resources / capabilities / android_browserstack.json file :

{
" platformName ": "Android",
" deviceName ": "Samsung Galaxy S22",
"app": "bs:// < app-id > ",
" bstack:options ": {
" projectName ": "Google Maps Appium Demo ",
" buildName ": "v1 - Demo Run",
" sessionName ": " Search & Navigate "
}
}

3. Pass - Dplatform = browserstack at test time

GitHub Actions Pipeline Example

name : Appium BrowserStack Tests

on:
push:
branches: [main, ' feature /**']
  pull_request :
branches: [ release/** ]

jobs:
  appium -cloud-tests:
runs-on: ubuntu-latest

	steps :
- name : Checkout
uses: actions/checkout@v3

- name : Setup Java & Maven
uses: actions/setup-java@v3
		with :
java-version: '21'

- name : Run Appium tests on BrowserStack
		approx :
BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
run: |
		  mvn clean test -DsuiteXmlFile=testng.xml - Dplatform = browserstack

Recommended frequency

Action Branch
main Nightly
feature /* On each push
release/* After merge + tag

Visualizing the results
BrowserStack Dashboard :
• Result ( failure /success)
• Screenshots
• Video
• Logs
• reports/ generated locally with ExtentReports if needed

In summary

Local pipeline Cloud               pipeline ( BrowserStack )
Jenkins                            GitHub Actions / GitLab CI
AVD (local emulator)               Real cloud devices
Local Appium                       Appium via BrowserStack
Simple execution                   Parallel execution
Extents Reports HTML               BrowserStack Dashboard + Extents Reports HTML 

⸻

Errors & Resolutions

Common Problem Resolution / Tip

Android device not detected ( adb empty devices ) ➤ Check that the AVD is launched from Android Studio or via terminal.
appium does not start (command not found ) ➤ Make sure you have installed Appium globally: npm install -g appium
Tests fail without clear logs ➤ Check that the Appium server is running before running
➤ Enable verbose logging with -Ddebug = true if expected.
The UI element is not found ➤ Check that the device is connected, the UI is visible, and loading times are handled with wait .
Test does not take screenshot ➤ Check that the screenshots / folder exists or is writable.
Problem with mvn or pom.xml ➤ Run mvn clean install to resolve dependencies. Check that JAVA_HOME points to JDK 17+.
ExtentReports does not generate a file ➤ Check the file path in ExtentReporterNG.java. The reports/ directory must exist.

⸻

* Known Limits / Future Paths

Current limits observed

These points were not resolved in this exercise, but are important to mention:
• Lack of multilingual management (English/French) of the Google Maps UI
Some elements change depending on the Android system language. This can make the selectors unstable on some French-speaking devices.
• Instabilities on Android 10 and 11
Tests fail on Android 10 and 11 due to UI variations (locators, rendering times).
Additional time is required to analyze and adapt selectors.
• No real CI/CD yet
GitHub Actions or Jenkins integration is documented, but not implemented due to lack of time or environment constraints.
• Not covering all navigation scenarios (traffic, cycling, transport, etc.)
The test only covers a simple car route from a text search.

⸻

* Possible future improvements

If the project were to be continued in a real context, here are the solutions or developments to consider:
• Automated CI/CD with GitHub Actions and Jenkins
Continuous execution with main branch, testing on multiple devices in parallel.
• Cross-platform support (iOS + Android)
• Dynamic language or device detection
Automatic adaptation of selectors according to the system language or detected Android version.
• Advanced capture management
Centralize captures according to status ( passed vs failed ) + archive in reports.
• Additional tests:
- Scenarios with “public transport” or “real-time traffic”
- Validation of map loading on different network types
- Simulation of network errors via tools like Charles Proxy or ADB shell
- Test coverage report
Generate a report to visualize the classes/methods/test cases actually covered.

⸻

Author: Sylvia N'Guessan

QA Consultant | Test Automation

⸻

Useful links
• Appium Docs
• BrowserStack App Automate
• TestNG XML suite config
• GitHub Actions

⸻

Completion check
• e2e Google Maps test meets specs
• Maintainable architecture (POM, config, logs)
• Local & cloud scripting
Djamo README
• GitHub delivery ready with CI documentation
	
---

Thank you for the opportunity and happy reading.

> “ Build for reuse , test for resilience .”


