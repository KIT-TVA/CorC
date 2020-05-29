package de.tu_bs.cs.isf.cbc.textual.tool.ui;

import java.util.regex.Pattern;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class MyAntlrTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper {
	
	private static final Pattern QUOTED = Pattern.compile("(?:^'([^']*)'$)|(?:^\"([^\"]*)\")$", Pattern.MULTILINE);

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		String superString = super.calculateId(tokenName, tokenType);
		if(QUOTED.matcher(tokenName).matches()) {
			return MyHighlightingConfiguration.CSTRING_ID;
		}
		return superString;
}

}
