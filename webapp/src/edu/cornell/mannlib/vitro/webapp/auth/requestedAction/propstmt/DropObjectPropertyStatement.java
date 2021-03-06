/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.auth.requestedAction.propstmt;

import com.hp.hpl.jena.ontology.OntModel;

import edu.cornell.mannlib.vitro.webapp.beans.ObjectPropertyStatement;

/**
 * Should we allow the user to delete this ObjectPropertyStatement from this
 * model?
 */
public class DropObjectPropertyStatement extends
		AbstractObjectPropertyStatementAction {
	public DropObjectPropertyStatement(OntModel ontModel, String sub,
			String pred, String obj) {
		super(ontModel, sub, pred, obj);
	}

	public DropObjectPropertyStatement(OntModel ontModel,
			ObjectPropertyStatement ops) {
		super(ontModel, ops);
	}
}
