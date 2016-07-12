package com.tibco.businessworks6.sonar.plugin.violation;

import org.sonar.api.rules.Rule;

public interface Violation {

	public abstract Rule getRule();

	public abstract int getLine();

	public abstract String getMessage();

}