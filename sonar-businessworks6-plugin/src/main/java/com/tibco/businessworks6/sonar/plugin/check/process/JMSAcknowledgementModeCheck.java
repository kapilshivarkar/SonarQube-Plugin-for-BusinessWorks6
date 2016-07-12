package com.tibco.businessworks6.sonar.plugin.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.profile.ProcessSonarWayProfile;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.EventSource;
import com.tibco.utils.bw.model.ProcessNode;

@Rule(key = JMSAcknowledgementModeCheck.RULE_KEY, name="JMS Acknowledgement Mode Check", priority = Priority.INFO, description = "This rule checks the acknowledgement mode used in JMS activities. Avoid using Auto Acknowledgement to minimize the risk of data loss.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.INFO)
public class JMSAcknowledgementModeCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "JMSAcknowledgementMode";
	@Override
	protected void validate(ProcessSource processSource) {
		List<Activity> activities = processSource.getProcessModel().getActivities();
		for (Activity activity : activities) {
			Node node = activity.getNode();
			if (node != null && activity.getType() != null && activity.getType().contains("bw.jms.getmsg")) {
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
			if (node != null && eventSource.getType() != null && (eventSource.getType().contains("bw.jms.signalin") || eventSource.getType().contains("bw.jms.receive"))){
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
						if(propertiesNodes.item(k).getAttributes().getNamedItem("ackMode") == null){
							Violation violation = new DefaultViolation(getRule(),
									1,
									"Auto Acknowledgement mode is set in the JMS activity "+processNode.getName()+".  Avoid using Auto Acknowledgement to minimize the risk of data loss.");
							processSource.addViolation(violation);
						}
					}
				}
			}
		}
	}
}
