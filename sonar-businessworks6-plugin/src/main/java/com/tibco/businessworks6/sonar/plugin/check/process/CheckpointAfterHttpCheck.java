package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.Iterator;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;
import com.tibco.utils.bw.model.Process;
import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Transition;

@Rule(key = CheckpointAfterHttpCheck.RULE_KEY, name="Checkpoint after HTTP Activities Check", priority = Priority.CRITICAL, description = "This rule checks the placement of a Checkpoint activity within a process. When placing your checkpoint in a process, be careful with certain types of process starters or incoming events, so that a recovered process instance does not attempt to access resources that no longer exist. For example, consider a process with an HTTP process starter that takes a checkpoint after receiving a request but before sending a response. In this case, when the engine restarts after a crash, the recovered process instance cannot respond to the request since the HTTP socket is already closed. As a best practice, do not place Checkpoint activity right after or in parallel path to HTTP activities.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.CRITICAL)
public class CheckpointAfterHttpCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "CheckpointProcessHTTP";
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
				if(activityType.contains("bw.http.")){
					if(onlyOneViolation){
						String proc = process.getName();
						proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
						Violation violation = new DefaultViolation(getRule(),
								1,
								"The process "+proc+" has a Checkpoint activity placed after HTTP activity or in a parallel flow to a HTTP activity.");
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
