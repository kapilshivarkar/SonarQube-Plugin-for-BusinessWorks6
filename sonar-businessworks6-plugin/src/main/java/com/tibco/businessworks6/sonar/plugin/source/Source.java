package com.tibco.businessworks6.sonar.plugin.source;

import java.nio.charset.Charset;
import java.util.List;

import org.sonar.api.resources.Resource;

import com.tibco.businessworks6.sonar.plugin.violation.Violation;

public interface Source {

	/**
	 * Parses the source and returns true if succeeded false if the file is corrupted.
	 */
	public boolean parseSource(Charset charset);

	public List<Violation> getViolations();

	public void setCode(String code);
	
	public String getCode();
	
	public void addViolation(Violation violation);
	
	@SuppressWarnings("rawtypes")
	public Resource create(Resource parent, String key);


}