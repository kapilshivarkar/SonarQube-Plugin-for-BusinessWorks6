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

@Rule(key = LogOnlyInSubprocessCheck.RULE_KEY, name="Log Only in Subprocess Check", priority = Priority.MAJOR, description = "If there is logging or auditing required at multiple points in your project, its advised to write logging and auditing code in a sub process and invoke this process from any point where this functionality is required. This rule checks whether LOG activity is used in sub process.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class LogOnlyInSubprocessCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "LogSubprocess";

	@Override
	protected void validate(ProcessSource processSource) {
		if(!processSource.getProcessModel().isSubProcess()){
			List<Activity> list = processSource.getProcessModel().getActivities();
			for (Iterator<Activity> iterator = list.iterator(); iterator.hasNext();) {
				Activity activity = iterator.next();
				if(activity.getType() !=null && activity.getType().equals("bw.generalactivities.log")){
					String proc = processSource.getProcessModel().getName();
					proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
					Violation violation = new DefaultViolation(getRule(),
							1,
							"The Log activity ["+activity.getName()+"] should be preferrably used in a sub process.  "+proc+" is not a subprocess.");
					processSource.addViolation(violation);
				}
				
			}
		}
	}
	
}
