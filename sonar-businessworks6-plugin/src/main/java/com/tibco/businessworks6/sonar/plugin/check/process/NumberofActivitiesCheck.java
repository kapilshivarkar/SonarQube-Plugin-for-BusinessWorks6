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

@Rule(key = NumberofActivitiesCheck.RULE_KEY, name="Number of Activities Check", priority = Priority.MINOR, description = "This rule checks the number of activities within a process, too many activities reduces the process readability")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MINOR)
public class NumberofActivitiesCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "NumberOfActivities";
	
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		int activityCount = process.getActivities().size();
			if (activityCount > 24) {
				Violation violation = new DefaultViolation(getRule(),
						1,
						"The process has too many activities, this reduces the process readablity");
				processSource.addViolation(violation);
			}
	}
}
