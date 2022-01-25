# TraitCbC

This is the slim version that supports only TraitCbC.
To install TraitCbC or CorC, please refer to the [wiki](https://github.com/TUBS-ISF/CorC/wiki).

The following projects must be imported in the workspace of Eclipse:<br>
de.tu-bs.cs.isf.cbc.model<br>
de.tu-bs.cs.isf.cbc.tool<br>
de.tu-bs.cs.isf.cbc.util<br>
de.tu-bs.cs.isf.cbcclass.tool<br>
de.tu-bs.cs.isf.wizards<br>
de.tu_bs.cs.isf.cbc.cbcclass.model<br>
de.tu_bs.cs.isf.commands.toolbar

To run TraitCbC, please start an Eclipse Application and import the case studies:<br>
BankAccountCorC<br>
ElevatorCorC<br>
EmailCorC<br>
IntListImpl

The case studies are implemented
1) in Java<br>
2) with TraitCbC<br>
3) with CorC

For TraitCbC each project contains implementations as interfaces. To run the check that the interfaces (traits) are compatible:<br>
Right click on a file "*.tc"<br>
Choose "Check Trait Composition"
