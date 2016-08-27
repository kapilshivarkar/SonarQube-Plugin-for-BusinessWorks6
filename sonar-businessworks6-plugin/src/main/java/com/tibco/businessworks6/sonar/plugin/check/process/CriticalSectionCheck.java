package com.tibco.businessworks6.sonar.plugin.check.process;

import com.google.common.collect.ImmutableList;
import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Group;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Transition;
import java.util.List;
import java.util.Map;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;

@Rule(key="CriticalSection", name="Activities in Critical Section Check", priority=Priority.CRITICAL, description="Critical section groups cause multiple concurrently running process instances to wait for one process instance to execute the activities in the group. As a result, there may be performance implications when using these groups. This rules checks that the Critical Section group does not include any activities that wait for incoming events or have long durations, such as Request/Reply activities, Wait For (Signal-In) activities, Sleep activity, or other activities that require a long time to execute.")
@BelongsToProfile(title="Sonar way", priority=Priority.CRITICAL)
public class CriticalSectionCheck
  extends AbstractProcessCheck
{
  public static final String RULE_KEY = "CriticalSection";
  public static final ImmutableList<String> CONSTANTS = ImmutableList.of("bw.http.waitForHTTPRequest", "bw.file.wait", "bw.generalactivities.sleep", "bw.jms.signalin", "bw.rv.waitforRVMessage", "bw.tcp.waitfortcp", "bw.http.sendHTTPRequest", "bw.ftl.requestreply", "bw.jms.requestreply", "bw.rv.sendRVRequest");
  
  
  protected void validate(ProcessSource processSource)
  {
	  final Process process = processSource.getProcessModel();
      boolean runvalidationflag = false;
      final List<Group> groups = (List<Group>)process.getGroups();
      for (final Group group : groups) {
          if (group.getType().equals("critical")) {
              runvalidationflag = true;
          }
      }
      if (groups.size() > 0 && runvalidationflag) {
          for (final Activity activity : process.getActivities()) {
              if (activity.getType() != null && CriticalSectionCheck.CONSTANTS.contains((Object)activity.getType())) {
                  final NodeList nodes = activity.getNode().getChildNodes();
                  for (int i = 0; i < nodes.getLength(); ++i) {
                      if (nodes.item(i).getNodeName().equals("bpws:targets")) {
                          final NodeList transitions_To = nodes.item(i).getChildNodes();
                          for (int j = 0; j < transitions_To.getLength(); ++j) {
                              if (transitions_To.item(j).getNodeName().equals("bpws:target")) {
                                  String transitionName = transitions_To.item(j).getAttributes().getNamedItem("linkName").getTextContent();
                                  if (process.getTransitions().get(transitionName) == null) {
                                      final Map<String, String> groupMapping = (Map<String, String>)process.getSynonymsGroupMapping();
                                      transitionName = groupMapping.get(transitionName);
                                  }
                                  final Transition transition = process.getTransitions().get(transitionName);
                                  final String from = transition.getFrom();
                                  this.findViolation(from, process, processSource, activity);
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  
  public void findViolation(final String from, final Process process, final ProcessSource processSource, final Activity activity1) {
      final Activity activity2 = process.getActivityByName(from);
      if (activity2 != null) {
          final String activityType = activity2.getType();
          if (activityType != null) {
              final Map<String, Transition> transition123 = (Map<String, Transition>)process.getTransitions();
              for (final String key : transition123.keySet()) {
                  final int index = key.indexOf("To");
                  final String toActivity = key.substring(index + 2);
                  if (toActivity.equals(activity2.getName())) {
                      final String fromActivity = key.substring(0, index);
                      this.findViolation(fromActivity, process, processSource, activity1);
                  }
              }
          }
      }
      else if (process.getEventSourceByName(from) == null && process.getGroupByName(from) != null && process.getGroupByName(from).getType().equals("critical")) {
          String proc = process.getName();
          proc = proc.substring(proc.lastIndexOf(".") + 1).concat(".bwp");
          final Violation violation = new DefaultViolation(this.getRule(), 1, "The activity " + activity1.getName() + " in process " + proc + " should not be used within Critical Section group.");
          processSource.addViolation(violation);
      }
  }
}
