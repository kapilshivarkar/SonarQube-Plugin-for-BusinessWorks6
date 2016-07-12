package com.tibco.businessworks6.sonar.plugin.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Process;

@Rule(key = NumberofServicesCheck.RULE_KEY, name="Number of Exposed Services Check", priority = Priority.MAJOR, description = "This rule checks the number of exposed services within a process. It is a good design practice to construct not more than 5 services in the same process.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class NumberofServicesCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "NumberOfExposedServices";
	
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		if(process.getServices() != null && process.getServices().size() >5){
			String proc = process.getName();
			proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
			Violation violation = new DefaultViolation(getRule(),
					1,
					"The process "+proc+" has too many services exposed, this reduces the process readablity and is not a good design pattern.");
			processSource.addViolation(violation);
		}
	}

}
