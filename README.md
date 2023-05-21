# [CorC](https://github.com/TUBS-ISF/CorC/wiki)
Tool Support for Correctness-by-Construction

# [C-CorC](https://github.com/TUBS-ISF/CorC/wiki/CorC-for-Information-Flow)
Tool Support for Confidentiality- and Correctness-by-Construction

# CorC Project Setup Guide & Case Study Introduction (2022-09-27)
Installation guide for plugins and properties for **development with CorC** with introduction of case studies.
## Setup
* **Eclipse** Install [Eclipse Modelling Tools](https://www.eclipse.org/downloads/packages/release/2021-06/r). Please use version 2021-06 (Eclipse 4.20.0). CorC may not work correctly with newer versions.
* **Graphiti** Install Graphiti using the update site https://download.eclipse.org/graphiti/updates/0.18.0/
* **KeY** Install KeY using the update site https://formal.iti.kit.edu/key/download/releases/2.6/eclipse/
* **Xtext** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/eclipse-xtext)
* **Jamopp** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/jamopp)
* **FeatureIDE** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/featureide)
* **Mylyn** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/mylyn) (Mylyn 3.23)
* **TestNG** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/testng-eclipse)
* **Z3** Get the latest release of [Z3](https://github.com/Z3Prover/z3/releases) and add it to the environment variable [PATH](https://www.wikihow.com/Change-the-PATH-Environment-Variable-on-Windows).

## Properties & Initialisation
1. Checkout master from https://github.com/TUBS-ISF/CorC.git
2. Open the following packages in Eclipse Modelling Tools:
* de.tu_bs.cs.isf.cbc.cbcclass.model
* de.tu_bs.cs.isf.cbc.statistics
* de.tu_bs.cs.isf.cbc.statistics.ui
* de.tu_bs.cs.isf.commands.toolbar
* de.tu-bs.cs.isf.cbc.model
* de.tu-bs.cs.isf.cbc.tool
* de.tu-bs.cs.isf.cbc.util
* de.tu-bs.cs.isf.cbcclass.tool
* de.tu-bs.cs.isf.wizards

3. Disable warnings for circular dependencies: 
Window -> Preferences -> Java -> Compiler -> Building -> Build path problems -> Circular dependencies -> Warning
4. Generate model/edit/editor: Open model/.genmodel of cbcmodel, cbcclassmodel, and statistics and rightclick on Generate ... If multiple referencing errors occur, uninstall Jamopp Plugins via Window -> Preferences -> Install/Update -> Uninstall or update. After uninstalling, reinstall via Marketplace.
5. Select any package and run project as Eclipse Application.

## Examples and Case Studies
We provide different examples and case studies to explore VarCorC!
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
