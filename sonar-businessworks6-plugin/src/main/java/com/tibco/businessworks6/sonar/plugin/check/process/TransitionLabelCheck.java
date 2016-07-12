package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.Iterator;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Transition;

@Rule(key = TransitionLabelCheck.RULE_KEY, name="Transition Labels Check", priority = Priority.MAJOR, description = "This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class TransitionLabelCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "TransitionLabels";
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		Map<String, Transition> transitions = process.getTransitions();
		Iterator<Map.Entry<String,Transition>> it = transitions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Transition> pair = (Map.Entry<String, Transition>)it.next();
			Transition transition = pair.getValue();
			//System.out.println("Line Number: "+transition.getLineNumber() + newline+"Transition Name :"+pair.getKey() + newline+"Transition From: " + transition.getFrom()+newline+"Transition To: "+transition.getTo()+newline+"Transition Type: "+transition.getConditionType()+newline+"Transition XPath: "+transition.getXpath()+newline+"Label :"+transition.getLabel());
			if (transition.getConditionType() != null && transition.getConditionType().equals("SUCCESSWITHCONDITION") && transition.getLabel() == null) {
				if(transition.getFrom() == null){
					String name = transition.getName();
					transition.setFrom(name.substring(0, name.indexOf("To")));
				}
				if(transition.getTo() == null){
					String name = transition.getName();
					transition.setTo(name.substring(name.indexOf("To")+2,name.length()));
				}
					Violation violation = new DefaultViolation(getRule(),
							transition.getLineNumber(),
							"The transition from "+transition.getFrom()+" to "+transition.getTo()+" doesn't have a proper label");
					processSource.addViolation(violation);
				}
		}
	}
}
