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

@Rule(key = JDBCHardCodeCheck.RULE_KEY, name="JDBC HardCoded Check", priority = Priority.MAJOR, description = "This rule checks JDBC activities for hardcoded values for fields Timeout and MaxRows. Use Process property or Module property.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class JDBCHardCodeCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "JDBCHardCoded";

	@Override
	protected void validate(ProcessSource processSource) {
		List<Activity> list = processSource.getProcessModel().getActivities();
		for (Iterator<Activity> iterator = list.iterator(); iterator.hasNext();) {
			Activity activity = iterator.next();
			if(activity.getType() != null && activity.getType().contains("bw.jdbc.")){
				if(activity.isJdbcMaxRows()){
					Violation violation = new DefaultViolation(getRule(),
							1,
							"The max rows setting in the JDBC activity "+activity.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
					processSource.addViolation(violation);
				}
				if(activity.isJdbcTimeout()){
					Violation violation = new DefaultViolation(getRule(),
							1,
							"The timeout setting in the JDBC activity "+activity.getName()+" is assigned a harcoded value. It should be defined as Process property or Module property.");
					processSource.addViolation(violation);
				}
			}
		}
	}
}
