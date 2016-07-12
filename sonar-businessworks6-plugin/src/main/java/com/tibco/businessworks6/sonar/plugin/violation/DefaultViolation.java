package com.tibco.businessworks6.sonar.plugin.violation;

/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.sonar.api.rules.Rule;

import com.tibco.businessworks6.sonar.plugin.violation.Violation;

/**
 * Checks and analyzes report measurements, violations and other findings in
 * WebSourceCode.
 * 
 * @author Kapil Shivarkar
 */
public class DefaultViolation implements Violation {

	private final Rule rule;
	private final int line;
	private final String message;

	public DefaultViolation(Rule rule, int line, String message) {
		this.rule = rule;
		this.line = line;
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getRule()
	 */
	public Rule getRule() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getLine()
	 */
	public int getLine() {
		return line;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getMessage()
	 */
	public String getMessage() {
		return message;
	}
}
