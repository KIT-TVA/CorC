package de.tu_bs.cs.isf.cbc.textual.tool.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MyHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String CSTRING_ID = "String";
	public static final String COMMENT_ID = "comment";
	
	public MyHighlightingConfiguration() {
	}
	
	 
	  public void configure(IHighlightingConfigurationAcceptor acceptor) {
		 super.configure(acceptor);
	    acceptor.acceptDefaultHighlighting(
	      CSTRING_ID, "String", codeStyle());
	  }
	  
	  public TextStyle codeStyle() {
	    TextStyle textStyle = new TextStyle();
	    textStyle.setColor(new RGB(127, 0, 85));
	    textStyle.setStyle(SWT.BOLD);
	    return textStyle;
	  }

}
