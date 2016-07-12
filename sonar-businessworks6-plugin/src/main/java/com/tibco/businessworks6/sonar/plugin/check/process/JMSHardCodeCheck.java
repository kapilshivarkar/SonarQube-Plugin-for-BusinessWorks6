package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.EventSource;
import com.tibco.utils.bw.model.ProcessNode;

@Rule(key = JMSHardCodeCheck.RULE_KEY, name="JMS HardCoded Check", priority = Priority.MAJOR, description = "This rule checks JMS activities for hardcoded values for fields Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval. Use Process property or Module property.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class JMSHardCodeCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "JMSHardCoded";

	@Override
	protected void validate(ProcessSource processSource) {
		
		List<Activity> activities = processSource.getProcessModel().getActivities();
		for (Activity activity : activities) {
			Node node = activity.getNode();
			if (node != null && activity.getType() != null && activity.getType().contains("bw.jms.")) {
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					if(children.item(i).getNodeName().equals("tibex:config")){
						detectViolation(children.item(i).getChildNodes().item(1), processSource, activity);
					}
				}
			}
		}

		List<EventSource> eventSources = processSource.getProcessModel().getEventSources();

		for (EventSource eventSource : eventSources) {
			Node node = eventSource.getNode();
			if (node != null && eventSource.getType() != null && eventSource.getType().contains("bw.jms.")){
				NodeList children = node.getChildNodes();
				for(int i =0; i < children.getLength(); i++)
					if(children.item(i).getNodeName().equals("tibex:eventSource")){
						Node subchild = children.item(i).getChildNodes().item(1);
						detectViolation(subchild, processSource, eventSource);
					}

			}
		}
	}
	
	public void detectViolation(Node bwactivity_config, ProcessSource processSource, ProcessNode processNode){
		NodeList nodes = bwactivity_config.getChildNodes();
		for (int j = 0; j < nodes.getLength(); j++) {
			if(nodes.item(j) != null && nodes.item(j).getNodeName().equals("activityConfig")){
				NodeList childNodes = nodes.item(j).getChildNodes();
				NodeList propertiesNodes = childNodes.item(1).getChildNodes();
				for (int k = 0; k < propertiesNodes.getLength(); k++) {
					if(propertiesNodes.item(k) != null && propertiesNodes.item(k).getNodeName().equals("value")){
						if(propertiesNodes.item(k).getAttributes().getNamedItem("destinationName") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Destination Name setting in the JMS activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("replyToDestination") != null ||  propertiesNodes.item(k).getAttributes().getNamedItem("nullreplyToQueue") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Reply to Destination setting in the JMS activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("maxSessions") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Max Sessions setting in the JMS activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("messageSelector") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Message Selector setting in the JMS activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("receiveTimeout") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Polling Interval setting in the JMS activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("destinationName") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Destination Name setting in the JMS Event Source activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("messageSelector") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Message Selector setting in the JMS Event Source activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
						if(propertiesNodes.item(k).getAttributes().getNamedItem("receiveTimeout") != null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"The Polling Interval setting in the JMS Event Source activity "+processNode.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
							processSource.addViolation(violation);
						}
					}
				}
			}
		}
	}
}
