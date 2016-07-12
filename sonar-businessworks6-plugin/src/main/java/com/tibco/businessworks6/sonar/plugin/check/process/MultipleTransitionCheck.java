package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.tibco.utils.bw.model.Transition;

@Rule(key = MultipleTransitionCheck.RULE_KEY, name="Multiple Transitions Check", priority = Priority.MAJOR, description = "EMPTY activity should be used if you want to join multiple transition flows. For example, there are multiple transitions out of an activity and each transition takes a different path in the process. In this scenario you can create a transition from the activity at the end of each path to an Empty activity to resume a single flow of execution in the process. This rule checks whether multiple transitions from an activity in a parallel flow merge into EMPTY activity")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class MultipleTransitionCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "MultipleTransitions";

	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		Map<String, Transition> map = process.getTransitions();
		Iterator<Map.Entry<String, Transition>> it = map.entrySet().iterator();
		Set<String> set = new HashSet<String>();
		boolean activityFlag = false;
		while (it.hasNext()) {
			Map.Entry<String, Transition> pair = it.next();
			if(pair.getValue().getTo() != null){
				if (!set.add(pair.getValue().getTo())) {
					for (Iterator<Activity> iterator = process.getActivities().iterator(); iterator.hasNext();) {
						Activity activity = iterator.next();
						if(activity.getName().equals(pair.getValue().getTo())){
							if(activity.getType() != null ){
								Violation violation = new DefaultViolation(getRule(),
										1,
										"There are multiple transitions converging into activity "+pair.getValue().getTo()+". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.");
								processSource.addViolation(violation);
								activityFlag = true;
							}
						}
					}
					if(activityFlag)
						activityFlag = false;
					else
						activityFlag = true;
				} 
			}
			
			//ToDo: Need to fix this issue...for groups there will always be multiple transitions converging as done a fix for Checkpoint in transaction for which GroupStart = GroupEnd = GroupName 
			/*if(activityFlag){
				Violation violation = new DefaultViolation(getRule(),
						1,
						"There are multiple transitions converging into group "+pair.getValue().getTo()+". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.");
				processSource.addViolation(violation);
				activityFlag = false;
			}*/
		}

	}

}
