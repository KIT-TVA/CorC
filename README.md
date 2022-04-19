# VarCorC project setup guide (2021-12-20)
Installation guide for plugins and properties for development of VarCorC including FeatureIDE and CorC 2.0
## Eclipse Modelling Tools
Install [Eclipse Modelling Tools](https://www.eclipse.org/downloads/packages/release/2021-06/r). Version 2021-06 is stable. Version 2021-09 maybe struggles with FeatureIDE.

## Necessary Plugins
### Graphiti
Install Graphiti using the update site https://download.eclipse.org/graphiti/updates/0.18.0/
### KeY
Install KeY using the update site https://formal.iti.kit.edu/key/download/releases/2.6/eclipse/
### Jamopp
Pull de.tu_bs.cs.isf.cbc.update from master and install via "Install new software..." in Eclipse the two Jamopp plugins. It may be clever to install Jamopp after generating the model (see properties & initialisation point 2).
### Xtext
Available in Eclipse Marketplace
### FeatureIDE
Available in Eclipse Marketplace
### Mylyn
Available in Eclipse Marketplace (Mylyn 3.23)

## Properties & Initialisation
1. Window -> Preferences -> Java -> Compiler -> Building -> Build path problems -> Circular dependencies -> Warning
2. Generate model/edit/editor: Open .genmodel of cbcmodel and cbcclassmodel and rightclick on Generate ... If multiple referencing errors occur, uninstall Jamopp Plugins via Window -> Preferences -> Install/Update -> Uninstall or update. After uninstalling both plugins, reinstall with the update package from above.
