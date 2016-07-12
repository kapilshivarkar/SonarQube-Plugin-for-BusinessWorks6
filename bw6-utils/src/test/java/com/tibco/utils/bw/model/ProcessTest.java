package com.tibco.utils.bw.model;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter.MapSplitter;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProcessTest {
	private boolean flag = true;
	@Test
	public void TestProcess()
	{
		/*Process process = new Process();
		//URL url = Resources.getResource("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/InvokeClientBundle.bwp");
		//String processSourceCode;
		//processSourceCode = Resources.toString(url, Charsets.UTF_8);
		File file = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/Process.bwp");
		process.setProcessXmlDocument1(file).startParsing();
		System.out.println("ProcessName: "+process.getName());
		System.out.println("EventSources: "+process.getEventSourcesCount());
		System.out.println("Total Activities: "+process.countAllActivities());
		System.out.println("Total Transitions: "+process.countAllTransitions());
		System.out.println("Total Groups: "+process.getGroupcount());
		System.out.println("Total Error Handlers: "+process.getCatchcount());
		System.out.println("Total Services Exposed: "+ process.getServices().size());*/
		

		/*List<Activity> activities = process.getActivities();
		for (Activity activity : activities) {
			Node node = activity.getNode();
			if (node != null && activity.getType() != null && activity.getType().contains("bw.jms.")) {
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					if(children.item(i).getNodeName().equals("tibex:config")){
						Node bwactivity_config = children.item(i).getChildNodes().item(1);
						NodeList nodes = bwactivity_config.getChildNodes();
						for (int j = 0; j < nodes.getLength(); j++) {
							if(nodes.item(j) != null && nodes.item(j).getNodeName().equals("activityConfig")){
								NodeList childNodes = nodes.item(j).getChildNodes();
								NodeList propertiesNodes = childNodes.item(1).getChildNodes();
								for (int k = 0; k < propertiesNodes.getLength(); k++) {
									if(propertiesNodes.item(k) != null && propertiesNodes.item(k).getNodeName().equals("value")){
										if(propertiesNodes.item(k).getAttributes().getNamedItem("ackMode") == null){
											System.out.println("Create violation for activity "+activity.getName());
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
		/*for (Iterator<Activity> iterator = process.getActivities().iterator(); iterator.hasNext();) {
			Activity activity =  iterator.next();
			if(activity.getType().equals("bw.internal.checkpoint")){
				NodeList nodes = activity.getNode().getChildNodes();
				for(int i=0; i< nodes.getLength();i++){
					System.out.println(nodes.item(i).getNodeName());
					if(nodes.item(i).getNodeName().equals("bpws:targets")){
						NodeList transitions_To = nodes.item(i).getChildNodes();
						for (int j = 0; j < transitions_To.getLength(); j++) {
							if(transitions_To.item(j).getNodeName().equals("bpws:target")){
								String transitionName = transitions_To.item(j).getAttributes().getNamedItem("linkName").getTextContent();
								Transition transition = process.getTransitions().get(transitionName);
								String from = transition.getFrom();
								test(from, process);
							}
						}
					}
				}
			}
			
		}*/
		
		/*List<EventSource> eventSources = process.getEventSources();
		for (Iterator<EventSource> iterator2 = eventSources.iterator(); iterator2.hasNext();) {
			EventSource eventSource =  iterator2.next();
			System.out.println(eventSource.getType());
		}
		Map<String, Transition> transition = process.getTransitions();
		for (String key : transition.keySet()) {
		    System.out.println("Key: " + key + ", Value: " + transition.get(key).getName()+"  FROM : "+transition.get(key).getFrom());
		}
*/		
		/*Map<String, Transition> map = process.getTransitions();
		Iterator<Map.Entry<String, Transition>> it = map.entrySet().iterator();
		Set<String> set = new HashSet<String>();
		while (it.hasNext()) {
			Map.Entry<String, Transition> pair = it.next();
		    	System.out.println("NAME: "+pair.getKey()+"   From:  "+pair.getValue().getFrom()+"     To: "+pair.getValue().getTo());
		
		}*/
		
		/*for (Iterator<Map.Entry<String,Service>> iterator = process.getServices().entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String,Service> serviceMap = iterator.next();
			System.out.println(serviceMap.getKey());
			for (Iterator<Map.Entry<String,Operation>> iterator2 = serviceMap.getValue().getOperations().entrySet().iterator(); iterator2.hasNext();) {
				Map.Entry<String,Operation> mapOperation = iterator2.next();
				System.out.println("--->"+mapOperation.getKey());
				if(mapOperation.getValue().getReferenceService().iterator() != null){
					for (Iterator<Service> iterator3 = mapOperation.getValue().getReferenceService().iterator(); iterator3.hasNext();) {
						Service referenceService = iterator3.next();
						System.out.println("------------->"+referenceService.getName());
						for (Iterator<String> iterator4 = referenceService.getOperations().keySet().iterator(); iterator4.hasNext();) {
							System.out.println(iterator4.next());
						}
					}
				}
			}
		}*/
		/*assertEquals("Processes/Metrics/Groups/FourGroupsInterlinked.process", process.name);
		assertEquals(3, process.activities.size());
		assertEquals(2, process.groups.size());
		assertEquals(7, process.transitions.size());*/	
		
		
		/*Iterator it = process.getOnlyReferenceServices().entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        Service service = (Service)pair.getValue();
		        Iterator it1 = service.getOperations().entrySet().iterator();
		        		while(it1.hasNext()){
		        			 Map.Entry pair1 = (Map.Entry)it1.next();
		     		        System.out.println("----->"+pair1.getKey() + " = " + pair1.getValue());
		        		}
		    }*/
		System.out.println("Test Sucess");
	}

	
	public void test(String from, Process process){
		Activity activity2 = process.getActivityByName(from);
		if(activity2.getType() != null && (activity2.getType().contains("bw.http.") || activity2.getType().equals("bw.jdbc.JDBCQuery") || activity2.getType().contains("bw.restjson.Rest"))){
			if(flag){
				System.out.println("ERROR VIOLATION");	
				flag = false;
			}
			
		}else{
			Map<String, Transition> transition123 = process.getTransitions();
			for (String key : transition123.keySet()) {
				int index = key.indexOf("To");
				String toActivity = key.substring(index+2);
				if(toActivity.equals(activity2.getName())){
					String fromActivity = key.substring(0, index);
					test(fromActivity, process);
				}
			}
		}
	}
}
