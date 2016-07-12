package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.Iterator;
import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Process;

@Rule(key = ChoiceOtherwiseCheck.RULE_KEY, name="Choice Condition with No Otherwise Check", priority = Priority.MAJOR, description = "This rule checks all activities input mapping for choice statement. As a coding best practice, the choice statement should always include the option otherwise.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class ChoiceOtherwiseCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "ChoiceWithNoOtherwise";

	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		List<Activity> activities = process.getActivities();
		for (Iterator<Activity> iterator = activities.iterator(); iterator.hasNext();) {
			Activity activity =  iterator.next();
			String expr = activity.getExpression();
			if(expr != null){
				if(expr.contains("xsl:choose") && !expr.contains("xsl:otherwise")){
					Violation violation = new DefaultViolation(getRule(),
							1,
							"The choice statement in activity input of "+activity.getName()+" does not include the option otherwise");
					processSource.addViolation(violation);
				}
			}
		}
	}
}
