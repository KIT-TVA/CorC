# CorC

CbC is an approach to incrementally create correct programs. Guided by pre-/postcondition specications, a program is created using refinement rules, guaranteeing the resulting implementation is correct. 

With [CorC](https://github.com/KIT-TVA/CorC/wiki), we implemented a graphical and textual IDE to create programs following the CbC approach. Starting with an abstract specification, CorC supports CbC developers in refining a program by a sequence of refinement steps and in verifying the correctness of these refinement steps using a theorem prover. 

CorC is developed as an extension for [Eclipse Modelling Tools](https://www.eclipse.org/downloads/packages/release).

# Getting Started 
After installing [EMT](https://www.eclipse.org/downloads/packages/release), install CorC via `Help -> Install New Software... -> Add` and enter the following URL: 
```
  https://bwsyncandshare.kit.edu/s/bZBbggzfGmg4C9t/download
```

# Documentation
Head over to our [wiki](https://github.com/fynndemmler/CorC/wiki) to find out more information about CorC.

# Examples
Create CorC-examples via `File -> New -> Other... -> CorC -> CorC Examples` Select examples you want to create.

# Case Studies
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
