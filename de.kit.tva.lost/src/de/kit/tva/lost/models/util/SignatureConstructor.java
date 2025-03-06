package de.kit.tva.lost.models.util;

import de.kit.tva.lost.models.parser.LostParser.SignatureContext;

public class SignatureConstructor {
	private static SignatureConstructor instance;

	public static SignatureConstructor getInstance() {
		if (instance == null) {
			instance = new SignatureConstructor();
		}
		return instance;
	}

	public String run(SignatureContext sigCtx) {
		var type = "";
		if (sigCtx.TYPE() == null) {
			type = "void";
		} else {
			type = sigCtx.TYPE().getText();
		}
		var sigStr = sigCtx.VISIBILITY().getText() + " " + type + " " + sigCtx.ID().getText();
		if (sigCtx.EMPTY_BRACKETS() != null) {
			return sigStr + "()";
		}
		var params = "";
		for (int i = 0; i < sigCtx.methodParameter().size(); ++i) {
			params += ", " + sigCtx.methodParameter(i).TYPE().getText() + " "
					+ sigCtx.methodParameter(i).ID().getText();
		}
		params = "(" + params.substring(2, params.length()) + ")";
		return sigStr + params;
	}
}
