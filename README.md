# CorC

CbC is an approach to create correct programs incrementally. Guided by pre-/postcondition specifications, a program is developed using refinement rules, guaranteeing the implementation is correct. With [CorC](https://github.com/KIT-TVA/CorC/wiki), we implemented a graphical and textual IDE to create programs following the CbC approach. Starting with an abstract specification, CorC supports CbC developers in refining a program by a sequence of refinement steps and in verifying the correctness of these refinement steps using a deductive verifier.

# C-CorC
CorC for information flow ([C-CorC](https://github.com/KIT-TVA/CorC/wiki/CorC-for-Information-Flow)) is an extension of CorC. 

# Getting Started
## Java Version
Install [**JDK 16**](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html). CorC may not work out of the box with newer versions.
## Eclipse Modelling Tools
- Install [Eclipse Modelling Tools (EMT)](https://www.eclipse.org/downloads/packages/release/2023-09/r/eclipse-modeling-tools) (Version 2023-09). 
- Get the latest release of [Z3](https://github.com/Z3Prover/z3/releases) and add the `*/z3-[cur-version]-[x64/x32]-win/bin` folder to the environment variable [PATH](https://www.wikihow.com/Change-the-PATH-Environment-Variable-on-Windows)

## EMT Plugins
- **Graphiti** Install Graphiti using the update site https://download.eclipse.org/graphiti/updates/release/0.18.0

- **FeatureIDE** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/featureide)

- **Mylyn** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/mylyn) (Mylyn 3.23)

- **TestNG** Available in [Eclipse Marketplace](https://marketplace.eclipse.org/content/testng-eclipse)

## CorC Setup
1. Clone the repo:
    ```sh
    git clone https://github.com/KIT-TVA/CorC.git
    ```
2. In EMT, select `Open Projects... -> CorC` and check the boxes for the following packages:
    - `de.tu-bs.cs.isf.cbc.exceptions`
    - `de.tu-bs.cs.isf.cbc.model`
    - `de.tu-bs.cs.isf.cbc.mutation`
    - `de.tu-bs.cs.isf.cbc.tool`
    - `de.tu-bs.cs.isf.cbc.util`
    - `de.tu-bs.cs.isf.cbcclass.tool`
    - `de.tu-bs.cs.isf.wizards`
    - `de.tu_bs.cs.isf.cbc.parser`
    - `de.tu_bs.cs.isf.cbc.statistics`
    - `de.tu_bs.cs.isf.cbc.statistics.ui`
    - `de.tu_bs.cs.isf.commands.toolbar`
    - `de.tu_bs.cs.isf.lattice`

3.  Open:
    - `*.cbc.model -> model -> genmodel.genmodel`
    - `*.cbc.statistics -> model -> cbcstatistics.genmodel` 
    
    Right click and `Generate Model/Edit/Editor Code` for all files listed.
    If EMT still displays errors, clean and rebuild all projects as described in the [common issues](#common-issues) section.

4. Select any package and run project as `Eclipse Application`.

# Examples & Case Study Introduction
We provide different examples and case studies to explore CorC!
## Examples
Create CorC-examples via `File -> New -> Other... -> CorC -> CorC Examples`.
## Case studies
The repository you checked out contains various software product line case studies in the folder `CaseStudies`. They can be loaded via `File -> Open project from file system`. 
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

**Solution:** Clean and rebuild all projects via `Project -> Clean...`.

---

**Problem:** Cycling depedency issues.

**Solution:** Navigate to: `Project -> Properties -> Java Compiler -> Building -> Configure Workspace Settings -> Build path problems -> Circular dependencies` and set the listbox to `Warning`.

---

**Problem:** Errors in certain files about undefined methods and classes.

**Solution:** Changing the compliance: `Project -> Java Compiler -> JDK Complicance -> Use compliance from execution environment 'JavaSE-16'`.

---

**Problem:** Errors involving the message 'Cannot modify resource set without a write transaction'.

**Solution:** Delete the folder `.settings` in `org.eclipse.core.runtime` within the current workspace. If that doesn't resolve the issue, delete all `.settings` folders and the `.project` file in the `CorC` folder.
