package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.Iterator;
import java.util.List;
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
import com.tibco.utils.bw.model.Group;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Transition;

@Rule(key = CheckpointInTransation.RULE_KEY, name="Checkpoint inside Transaction Group Check", priority = Priority.CRITICAL, description = "This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint within or in parallel to a Transaction Group or a Critical Section Group. Checkpoint activities should be placed at points that are guaranteed to be reached before or after the transaction group is reached.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.CRITICAL)
public class CheckpointInTransation extends AbstractProcessCheck{
	public static final String RULE_KEY = "CheckpointProcessTransaction";
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		boolean runvalidationflag = false;
		List<Group> groups = process.getGroups();
		for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext();) {
			Group group = iterator.next();
			if(group.getType().equals("localTX"))
				runvalidationflag = true;
		}
		
		if(groups.size() > 0 && runvalidationflag){
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
	}

	public void findViolation(String from, Process process, ProcessSource processSource){
		Activity activity = process.getActivityByName(from);
		if(activity != null){
			String activityType = activity.getType();
			if(activityType != null){
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
		}else{
			if(process.getEventSourceByName(from) == null){
				if(process.getGroupByName(from) != null){
					String proc = process.getName();
					proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
					Violation violation = null;
					if(process.getGroupByName(from).getType().equals("localTX")){
						violation = new DefaultViolation(getRule(),
								1,
								"The Checkpoint activity in the process "+proc+" is placed within a Transaction group. Checkpoint should not be placed within or in parallel flow to a transaction.");
					}else if(process.getGroupByName(from).getType().equals("critical")){
						violation = new DefaultViolation(getRule(),
								1,
								"The Checkpoint activity in the process "+proc+" is placed within a Critical Section group. Checkpoint should not be placed within a Critical Section group.");
					}
					processSource.addViolation(violation);
				}
			}
		}
	}
}
