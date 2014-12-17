package org.adho.dhconvalidator.util;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.XPathContext;

public class DocumentUtil {
	public static Element getFirstMatch(Document document, String query) {
		return getFirstMatch(document, query, null);
	}

	public static Element getFirstMatch(Document document, String query,
			XPathContext xPathContext) {
		Nodes nodes = document.query(query, xPathContext);
		if ((nodes.size() > 0) && (nodes.get(0) instanceof Element)) {
			return (Element)nodes.get(0);
		}
		
		throw new IllegalStateException("unexpected result");
	}
}
