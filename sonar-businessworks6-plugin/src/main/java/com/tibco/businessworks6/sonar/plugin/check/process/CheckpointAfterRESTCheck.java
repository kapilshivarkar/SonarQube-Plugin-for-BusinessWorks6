package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.Iterator;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Transition;

@Rule(key = CheckpointAfterRESTCheck.RULE_KEY, name="Checkpoint after REST Webservice Call Check", priority = Priority.MAJOR, description = "This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint after or in a parallel flow of REST webservice call.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class CheckpointAfterRESTCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "CheckpointProcessREST";
	private boolean onlyOneViolation = true;
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		for (Iterator<Activity> iterator = process.getActivities().iterator(); iterator.hasNext();) {
			Activity activity =  iterator.next();
			if(activity.getType() != null && activity.getType().equals("bw.internal.checkpoint")){
				NodeList nodes = activity.getNode().getChildNodes();
				for(int i=0; i< nodes.getLength();i++){
					if(nodes.item(i).getNodeName().equals("bpws:targets")){
						NodeList transitions_To = nodes.item(i).getChildNodes();
						for (int j = 0; j < transitions_To.getLength(); j++) {
							if(transitions_To.item(j).getNodeName().equals("bpws:target")){
								String transitionName = transitions_To.item(j).getAttributes().getNamedItem("linkName").getTextContent();
								if(process.getTransitions().get(transitionName) == null){
									Map<String, String> groupMapping = process.getSynonymsGroupMapping();
									transitionName = groupMapping.get(transitionName);
								}
								Transition transition = process.getTransitions().get(transitionName);
								String from = transition.getFrom();
								findViolation(from, process, processSource);
							}
						}
					}
				}
			}

		}
	}

	public void findViolation(String from, Process process, ProcessSource processSource){
		Activity activity = process.getActivityByName(from);
		if(activity != null){
			String activityType = activity.getType();
			if(activityType != null){
				if(activityType.contains("bw.restjson.Rest")){
					if(onlyOneViolation){
						String proc = process.getName();
						proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
						Violation violation = new DefaultViolation(getRule(),
								1,
								"The process "+proc+" has a Checkpoint activity placed after a REST webservice call or in a parallel flow to a REST webservice call.");
						processSource.addViolation(violation);
						onlyOneViolation = false;
					}
				}else{
					Map<String, Transition> transition123 = process.getTransitions();
					for (String key : transition123.keySet()) {
						int index = key.indexOf("To");
						String toActivity = key.substring(index+2);
						if(toActivity.equals(activity.getName())){
							String fromActivity = key.substring(0, index);
							findViolation(fromActivity, process, processSource);
						}
					}
				}
			}
		}
	}
}
