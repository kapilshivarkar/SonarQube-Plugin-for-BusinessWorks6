package com.tibco.businessworks6.sonar.plugin.check.process;

import com.tibco.utils.bw.model.Process;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;

@Rule(key = ForEachGroupCheck.RULE_KEY, name="For-Each Group Check", priority = Priority.INFO, description = "This rule checks the ForEach group. It is recommended to use For-Each activity input mapping instead of using For-Each/Iteration Group wherever possible. Do not use iteration groups just for mapping repeating elements.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.INFO)
public class ForEachGroupCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "ForEachGroup";
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		if(process.isHasForEachGroup()){
			String proc = process.getName();
			proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
			Violation violation = new DefaultViolation(getRule(),
					1,
					"For-Each group is used in process "+proc+". For performance reasons it is recommended to use For-Each in activity input mapping instead of using For-Each Group whenever possible. ");
			processSource.addViolation(violation);
		}
	}
}
