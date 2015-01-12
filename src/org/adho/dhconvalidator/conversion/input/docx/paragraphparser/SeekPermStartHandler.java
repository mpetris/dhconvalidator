/*
 * Copyright (c) 2015 http://www.adho.org/
 * License: see LICENSE file
 */
package org.adho.dhconvalidator.conversion.input.docx.paragraphparser;

import org.adho.dhconvalidator.conversion.input.docx.DocxInputConverter.Namespace;
import org.adho.dhconvalidator.util.DocumentUtil;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.XPathContext;

/**
 * Handles paragraphs while in {@link State#SEEKPERMSTART}.
 * 
 * @author marco.petris@web.de
 *
 */
public class SeekPermStartHandler implements StateHandler {

	/* (non-Javadoc)
	 * @see org.adho.dhconvalidator.conversion.input.docx.paragraphparser.StateHandler#handleParagraph(nu.xom.Element, nu.xom.Document, nu.xom.XPathContext)
	 */
	@Override
	public State handleParagraph(Element matchElement, Document document,
			XPathContext xPathContext) {
		
		// found an editable paragraph?
		if (matchElement.getFirstChildElement("permStart", Namespace.MAIN.toUri()) != null) { //$NON-NLS-1$
			return State.INPERM; // yes, so change state
		}
		else if (DocumentUtil.hasMatch(
					matchElement, "w:pPr/w:pStyle[@w:val='DH-BibliographyHeading']", xPathContext) //$NON-NLS-1$
				&& (DocumentUtil.hasMatch(matchElement, "w:r", xPathContext))) { //$NON-NLS-1$
			// the References section is not editable but we want to keep it and continue with searching
			return State.SEEKPERMSTART;
		}
		else {
			// remove the non editable section and continue with searching
			matchElement.getParent().removeChild(matchElement);
			return State.SEEKPERMSTART;
		}
	}

}
