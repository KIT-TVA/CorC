package de.kit.tva.lost.models.codeviews;

import de.kit.tva.lost.interfaces.AbstractCodeView;
import de.kit.tva.lost.interfaces.CodeView;

public class ExtendedCodeView extends AbstractCodeView implements CodeView {

    @Override
    public String transform(String code) {
	return code;
    }

}
