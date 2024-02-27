# CorC

CbC is an approach to incrementally create correct programs. Guided by pre-/postcondition specications, a program is created using refinement rules, guaranteeing the resulting implementation is correct. With [CorC](https://github.com/KIT-TVA/CorC/wiki), we implemented a graphical and textual IDE to create programs following the CbC approach. Starting with an abstract specification, CorC supports CbC developers in refining a program by a sequence of refinement steps and in verifying the correctness of these refinement steps using a theorem prover.

# C-CorC
CorC for information flow ([C-CorC](https://github.com/KIT-TVA/CorC/wiki/CorC-for-Information-Flow)) is an extension of CorC. 

# Getting Started 
CorC requires [Eclipse Modelling Tools (EMT)](https://www.eclipse.org/downloads/packages/release/2023-09/r/eclipse-modeling-tools) to be installed and extended with various plugins.
## Java Version
Install [**JDK 16**](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html). CorC may not work out of the box with newer versions.
## Software
- Install [Eclipse Modelling Tools](https://www.eclipse.org/downloads/packages/release/2023-09/r/eclipse-modeling-tools) (Version 2023-09). 
- Get the latest release of [Z3](https://github.com/Z3Prover/z3/releases) and add the `*/z3-[cur-version]-[x64/x32]-win/bin` folder to the environment variable [PATH](https://www.wikihow.com/Change-the-PATH-Environment-Variable-on-Windows)

## EMT Plugins
- **Graphiti** Install Graphiti using the update site https://download.eclipse.org/graphiti/updates/0.18.0/

- **KeY** Install KeY using the update site https://formal.iti.kit.edu/key/download/releases/2.6/eclipse/ (disable `Group items` to show available plugins, install all)

- **Xtext** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/eclipse-xtext)

- **FeatureIDE** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/featureide)

- **Mylyn** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/mylyn) (Mylyn 3.23)

- **TestNG** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/testng-eclipse)

## CorC Setup
1. Clone the repo:
    ```sh
    git clone https://github.com/KIT-TVA/CorC.git
    ```
2. Install **JaMoPP** using the two plugins inside package `*.tool.update` (disable `Group items` to show available plugins).
3. Open the following packages in Eclipse Modelling Tools:
    - `de.tu-bs.cs.isf.cbc.model`
    - `de.tu-bs.cs.isf.cbc.exceptions`
    - `de.tu-bs.cs.isf.cbc.tool`
    - `de.tu-bs.cs.isf.cbc.util`
    - `de.tu-bs.cs.isf.cbcclass.tool`
    - `de.tu-bs.cs.isf.wizards`
    - `de.tu_bs.cs.isf.cbc.parser`
    - `de.tu_bs.cs.isf.cbc.statistics`
    - `de.tu_bs.cs.isf.cbc.statistics.ui`
    - `de.tu_bs.cs.isf.commands.toolbar`
    - `de.tu_bs.cs.isf.lattice`

4.  Open:
    - `*.cbc.model -> model -> genmodel.genmodel`
    - `*.cbc.statistics -> model -> cbcstatistics.genmodel` 
    
    Right click and `Generate Model/Edit/Editor Code` for all files listed.

5. Select any package and run project as `Eclipse Application`.

# Examples & Case Study Introduction
We provide different examples and case studies to explore CorC!
## Examples
Create CorC-examples via `File -> New -> Other... -> CorC -> CorC Examples` Select examples you want to create.
## Case studies
The repository you checked out contains various software product line case studies. They can be loaded via `File -> Open project from file system`. 
### BankAccount
The BankAccount implements basic functions of a bank account such as withdrawals, limits, money transfers and checking the account balance.
- **BankAccount** Object-oriented implementation with class structure and CbC-Classes.
- **BankAccountOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.
### Elevator
The Elevator implements basic functions of an elevator such as the movement and entering and leaving of persons.
- **Elevator** Object-oriented implementation with class structure and CbC-Classes.
### Email
The product line Email implements basic functions of an email system including server- and client-side interactions.
- **EmailOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.
- **EmailFeatureInteraction** Java-Implementation without implementation with CbC.
### IntegerList
The IntegerList implements a list of integers with add and sort operations.
- **IntegerList** Object-oriented implementation with class structure and CbC-Classes.
- **IntegerListOO** Object-oriented implementation with class structure and CbC-Classes. Non-SPL implementation.

# Common Issues

**Problem:** EMT gets stuck while trying to generate a model.

**Solution:** Terminate EMT using any process manager and generate the model again.

---

**Problem:** Multiple resolving errors after generating model files.

**Solution:** Uninstall JaMoPP Plugins via `Window -> Preferences -> Install/Update -> Uninstall or update`. Afterwards reinstall as described above.

---

**Problem:** Cycling depedency issues.

**Solution:** Navigate to: `Project -> Properties -> Java Compiler -> Building -> Configure Workspace Settings -> Build path problems -> Circular dependencies` and set the listbox to `Warning`.

---

**Problem:** Errors in certain files about undefined methods and classes.

**Solution:** Changing the compliance: `Project -> Java Compiler -> JDK Complicance -> Use compliance from execution environment 'JavaSE-16'`.
