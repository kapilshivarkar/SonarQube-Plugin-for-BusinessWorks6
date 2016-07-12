package com.tibco.businessworks6.sonar.plugin.source;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.resources.Resource;

import com.tibco.businessworks6.sonar.plugin.violation.Violation;

public abstract class AbstractSource implements Source {

	protected final List<Violation> issues;

	protected String code;
	
	protected AbstractSource(){
		issues = new ArrayList<Violation>();
	}
	
	public void addViolation(Violation violation) {
		this.issues.add(violation);
	}


	public List<Violation> getViolations() {
		return issues;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public abstract boolean parseSource(Charset charset);

	@SuppressWarnings({ "rawtypes" })
	public abstract Resource create(Resource parent, String key);
	
}
