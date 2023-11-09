# [CorC](https://github.com/KIT-TVA/CorC/wiki)
Tool Support for Correctness-by-Construction

# [C-CorC](https://github.com/KIT-TVA/CorC/wiki/CorC-for-Information-Flow)
Tool Support for Confidentiality- and Correctness-by-Construction

# CorC Project Setup Guide & Case Study Introduction (2023-11-09)
Installation guide for plugins and properties for **development with CorC** with introduction of case studies.
## Java Version
* Please use [**JDK 16**](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html). CorC may not work out of the box with newer versions.
## Setup
### **Plugins and Software**
* **Eclipse** Install [Eclipse Modelling Tools](https://www.eclipse.org/downloads/packages/release/2023-09/r). Please use version 2023-09 (Eclipse 4.29.0). CorC may not work correctly with other versions.
* Before installing any plugins, navigate to *Preferences > Install/Update > Available Software Sites* and disable *Latest Eclipse IDE Packages Release* and *Latest Eclipse Simultaneous Release*.
* **Graphiti** Install Graphiti using the update site https://download.eclipse.org/graphiti/updates/0.18.0/
* **KeY** Install KeY using the update site https://formal.iti.kit.edu/key/download/releases/2.6/eclipse/ (disable *Group items* to show available plugins, install all)
* **Xtext** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/eclipse-xtext)
* **FeatureIDE** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/featureide)
* **Mylyn** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/mylyn) (Mylyn 3.23)
* **TestNG** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/testng-eclipse)
* **Z3** Get the latest release of [Z3](https://github.com/Z3Prover/z3/releases) and add it to the environment variable [PATH](https://www.wikihow.com/Change-the-PATH-Environment-Variable-on-Windows).

## Properties & Initialisation
1. Checkout master from https://github.com/TUBS-ISF/CorC.git
2. Install **JaMoPP** using the two plugins of the tool.update package from Git (disable *Group items* to show available plugins)
3. Open the following packages in Eclipse Modelling Tools:
* de.tu-bs.cs.isf.cbc.model
* de.tu-bs.cs.isf.cbc.tool
* de.tu-bs.cs.isf.cbc.util
* de.tu-bs.cs.isf.cbcclass.tool
* de.tu-bs.cs.isf.wizards
* de.tu_bs.cs.isf.cbc.parser
* de.tu_bs.cs.isf.cbc.statistics
* de.tu_bs.cs.isf.cbc.statistics.ui
* de.tu_bs.cs.isf.commands.toolbar
* de.tu_bs.cs.isf.lattice

4. Generate model/edit/editor: Open model/genmodel.genmodel of package cbc.model and model/cbcstatistics.genmodel of package cbc.statistics and rightclick on Generate model/edit/editor. If multiple resolving errors occur, uninstall JaMoPP Plugins via Window -> Preferences -> Install/Update -> Uninstall or update. After uninstalling, reinstall as described above.
5. Select any package and run project as Eclipse Application.

## Examples and Case Studies
We provide different examples and case studies to explore CorC!
### Examples
Create CorC-examples via File -> New -> Other... -> CorC -> CorC Examples -> Select examples you want to create.
### Case studies
The repository you checked out contains various software product line case studies. They can be loaded via File -> Open project from file system. 
#### BankAccount
The BankAccount implements basic functions of a bank account such as withdrawals, limits, money transfers and checking the account balance.
* **BankAccount** Object-oriented implementation with class structure and CbC-Classes.
* **BankAccountOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.
#### Elevator
The Elevator implements basic functions of an elevator such as the movement and entering and leaving of persons.
* **Elevator** Object-oriented implementation with class structure and CbC-Classes.
#### Email
The product line Email implements basic functions of an email system including server- and client-side interactions.
* **EmailOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.
* **EmailFeatureInteraction** Java-Implementation without implementation with CbC.
#### IntegerList
The IntegerList implements a list of integers with add and sort operations.
* **IntegerList** Object-oriented implementation with class structure and CbC-Classes.
* **IntegerListOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.
