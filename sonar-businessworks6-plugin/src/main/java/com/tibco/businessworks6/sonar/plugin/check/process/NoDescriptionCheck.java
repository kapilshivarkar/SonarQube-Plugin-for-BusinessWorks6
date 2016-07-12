/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.tibco.businessworks6.sonar.plugin.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.helper.XmlHelper;
import com.tibco.utils.bw.model.Process;

@Rule(key = NoDescriptionCheck.RULE_KEY, name="No Process Description Check", priority = Priority.MINOR, description = "This rule checks if there is description specified for a process.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MINOR)
public class NoDescriptionCheck extends AbstractProcessCheck {

	public static final String RULE_KEY = "ProcessNoDescription";
	public static final String DESCRIPTION_ELEMENT_NAME = "documentation";
	public static final String DESCRIPTION_ELEMENT_NAMESPACE = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";

	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		Document document = process.getProcessXmlDocument();
		try {
			Element description = XmlHelper.firstChildElement(
					document.getDocumentElement(),
					DESCRIPTION_ELEMENT_NAMESPACE, DESCRIPTION_ELEMENT_NAME);
			if (description.getTextContent() == null
					|| description.getTextContent().isEmpty()) {
				Violation violation = new DefaultViolation(getRule(),
						processSource.getLineForNode(description),
						"Empty description for this process");
				processSource.addViolation(violation);
			}
		} catch (Exception e) {
			Violation violation = new DefaultViolation(getRule(), 1,
					"No description found in this process");
			processSource.addViolation(violation);
		}
	}

}
