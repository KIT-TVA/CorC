# TraitCbC

This is the slim version that supports only TraitCbC.
To install TraitCbC or CorC, please refer to the [wiki](https://github.com/TUBS-ISF/CorC/wiki).

The following projects must be imported in the workspace of Eclipse:
de.tu-bs.cs.isf.cbc.model
de.tu-bs.cs.isf.cbc.tool
de.tu-bs.cs.isf.cbc.util
de.tu-bs.cs.isf.cbcclass.tool
de.tu-bs.cs.isf.wizards
de.tu_bs.cs.isf.cbc.cbcclass.model
de.tu_bs.cs.isf.commands.toolbar

To run TraitCbC, please start an Eclipse Application and import the case studies:

BankAccountCorC
ElevatorCorC
EmailCorC
IntListImpl

The case studies are implemented
1) in Java
2) with TraitCbC
3) with CorC

For TraitCbC each project contains implementations as interfaces. To run the check that the interfaces (traits) are compatible:
Right click on a file "*.tc"
Choose "Check Trait Composition"
