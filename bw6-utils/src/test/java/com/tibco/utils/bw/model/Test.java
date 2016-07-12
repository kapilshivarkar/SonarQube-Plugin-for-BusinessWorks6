package com.tibco.utils.bw.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.tibco.utils.bw.model.Operation;
import com.tibco.utils.bw.model.Process;

public class Test {
	Map<String, Process> servicetoprocess = new HashMap<String, Process>();
	public static void main(String[] args) {

		List<Process> processList = new ArrayList<Process>();
		Process process1 = new Process();
		File file1 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/MakeNoise.bwp");
		process1.setProcessXmlDocument1(file1).startParsing();
		processList.add(process1);
		Process process2 = new Process();
		File file2 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/Log.bwp");
		process2.setProcessXmlDocument1(file2).startParsing();
		processList.add(process2);
		Process process3 = new Process();
		File file3 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/Invoke.bwp");
		process3.setProcessXmlDocument1(file3).startParsing();
		processList.add(process3);
		Process process4 = new Process();
		File file4 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/LoopProcess.bwp");
		process4.setProcessXmlDocument1(file4).startParsing();
		processList.add(process4);
		Process process5 = new Process();
		File file5 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/Dead.bwp");
		process5.setProcessXmlDocument1(file5).startParsing();
		processList.add(process5);
		Process process6 = new Process();
		File file6 = new File("C:/Work/GitRepository/TibcoTestProjectsRepository/bw6-utils/src/main/java/com/tibco/utils/bw/model/Dummy.bwp");
		process6.setProcessXmlDocument1(file6).startParsing();
		processList.add(process6);
		Test test = new Test();
		test.find(processList);
		
		//test.drillProcess(processList);

	}
	
	public void find(List<Process> processesList){
		for (int i = 0; i < processesList.size(); i++) {
			Set<String> serviceNames = processesList.get(i).getServices().keySet();
			for (Iterator<String> iterator = serviceNames.iterator(); iterator.hasNext();) {
				String string = iterator.next();
				servicetoprocess.put(string, processesList.get(i));
			}
		}
		for (Iterator<Process> iterator  = processesList.iterator(); iterator.hasNext();) {
			Process process = iterator.next();
			Map<String, Service> services = process.getServices();
			Map<String, Service> referencedServices = process.getProcessReferenceServices();
			if(services.size() > 0 && referencedServices.size() > 0){
				Set<String> serviceName = services.keySet();
				Set<String> referenceServiceName = referencedServices.keySet();
				Set<String> dupReferencedServiceName = new HashSet<String>(referenceServiceName);
				findDeadLock(serviceName, referenceServiceName, dupReferencedServiceName, process);
			}
		}
	}
	
	public void findDeadLock(Set<String> serviceName, Set<String> referenceServiceName, Set<String> dupReferencedServiceName, Process process){
		dupReferencedServiceName.retainAll(serviceName);
		if(dupReferencedServiceName.size() > 0 ){
			String[] deadlockedService = dupReferencedServiceName.toArray(new String[dupReferencedServiceName.size()]);
			String proc1 = servicetoprocess.get(deadlockedService[0]).getName();
			   proc1 = proc1.substring(proc1.lastIndexOf(".")+1).concat(".bwp");
			   String proc2 = process.getName();
				proc2 = proc2.substring(proc2.lastIndexOf(".")+1).concat(".bwp");
			System.out.println("Check for possible deadlocks for exposed service "+deadlockedService[0] +" in process "+proc1+" and consumed service "+deadlockedService[0] +" in process "+proc2);
		}else{
			for (String name : referenceServiceName) {
				Process proc = servicetoprocess.get(name);
				Set<String> embedreferenceService = proc.getProcessReferenceServices().keySet();
				Set<String> dupembedreferenceService = new HashSet<String>(embedreferenceService);
				findDeadLock(serviceName, embedreferenceService, dupembedreferenceService, proc);
			}
			
		}
	}
	

	public void drillProcess(List<Process> processList){
		for (int i = 0; i < processList.size(); i++) {
			Set<String> serviceNames = processList.get(i).getServices().keySet();
			for (Iterator<String> iterator = serviceNames.iterator(); iterator.hasNext();) {
				String string = iterator.next();
				servicetoprocess.put(string, processList.get(i));
			}
		}


		for (int i = 0; i < processList.size()-1; i++) {
			for (int k = i+1; k < processList.size(); k++){ 
				Set<String> services = processList.get(k).getServices().keySet();
				Set<String> referencedServices = processList.get(i).getProcessReferenceServices().keySet();
				Set<String> dupReferencedServices = new HashSet<String>(referencedServices);
				//checkDeadlock(services, referencedServices);
				dupReferencedServices.retainAll(services);
					   if(dupReferencedServices.size() > 0){
						   Set<String> services1 = processList.get(i).getServices().keySet();
						   Set<String> referencedServices1 = processList.get(k).getProcessReferenceServices().keySet();
						   Set<String> dupReferencedServices1 = new HashSet<String>(referencedServices1);
						   dupReferencedServices1.retainAll(services1);
						   if(dupReferencedServices1.size() > 0){
							   String[] deadlockedService = dupReferencedServices1.toArray(new String[dupReferencedServices1.size()]);
							   if(processList.get(i).getProcessReferenceServices().get(deadlockedService[0]) != null){
								   Map<String, Operation> operationMap = processList.get(i).getProcessReferenceServices().get(deadlockedService[0]).getOperations();
								   Iterator<Map.Entry<String, Operation>> it = operationMap.entrySet().iterator();
								    while (it.hasNext()) {
								    	Map.Entry<String, Operation> pair = it.next();
								    	if(pair.getValue().getOperationReferencedService().contains(processList.get(k).getServices().get(deadlockedService[0]))){
								    		System.out.println("The referenced services from operation implementaion "+pair.getKey()+" of service "+deadlockedService[0]+" in process "+processList.get(k).getName()+" might be creating a deadlock");
								    	}
								    }
							   }else{
								   Map<String, Operation> operationMap = processList.get(k).getProcessReferenceServices().get(deadlockedService[0]).getOperations();
								   Iterator<Map.Entry<String, Operation>> it = operationMap.entrySet().iterator();
								    while (it.hasNext()) {
								    	Map.Entry<String, Operation> pair = it.next();
								    	if(pair.getValue().getOperationReferencedService().contains(processList.get(k).getServices().get(deadlockedService[0]))){
								    		if(pair.getKey().equals(processList.get(k).getServices().get(deadlockedService[0]).getOperations().get(pair.getKey())))
								    		System.out.println("The referenced services from operation implementaion "+pair.getKey()+" of service "+deadlockedService[0]+" in process "+processList.get(k).getName()+" might be creating a deadlock");
								    	}
								    }
							   }
							   String proc1 = processList.get(i).getName();
							   String proc2 = processList.get(k).getName();
							   proc1 = proc1.substring(proc1.lastIndexOf(".")+1).concat(".bwp");
							   proc2 = proc2.substring(proc2.lastIndexOf(".")+1).concat(".bwp");
							   System.out.println("Check service "+dupReferencedServices1.toString()+" exposed by process "+proc1+" and consumed by process "+proc2+" for any possible deadlocks.");

						   }else if(services1.size() > 0){//no deadlock found...need to drill more
							   loopReference(referencedServices1, services1);
						   }

					   }
			}
		}
		System.out.println("DONE.");
	}
	
	public void loopReference(Set<String> referencedServices1, Set<String> services1 ){
		for (Iterator<String> iterator = referencedServices1.iterator(); iterator.hasNext();) {
			String string =  iterator.next();
			Process process = servicetoprocess.get(string);
			Set<String> referencereferenceServices = process.getProcessReferenceServices().keySet();
			Set<String> dupreferencereferenceServices = new HashSet<String>(referencereferenceServices);
			dupreferencereferenceServices.retainAll(services1);
			if(dupreferencereferenceServices.size() > 0){
				String[] deadlockedService123 = dupreferencereferenceServices.toArray(new String[dupreferencereferenceServices.size()]);
				System.out.println("DEAD  " +process.getName()+"       "+servicetoprocess.get(dupreferencereferenceServices.toArray(new String[dupreferencereferenceServices.size()])[0]).getName());
				//System.out.println("HERE"+deadlockedService123[0]+"    "+ process.getName()+"   "+processList.get(k).getName());
			}else if(referencereferenceServices != null){
				loopReference(referencereferenceServices, services1);
			}
		}
	}

	/*public void checkDeadlock(Set<String> services, Set<String> referencedServices){
		Set<String> dupReferencedServices = new HashSet<String>(referencedServices);
		dupReferencedServices.retainAll(services);
		if(dupReferencedServices.size() > 0){
			Set<String> services1 = processList.get(i).getServices().keySet();
			Set<String> referencedServices1 = processList.get(k).getProcessReferenceServices().keySet();
			Set<String> dupReferencedServices1 = new HashSet<String>(referencedServices1);
			dupReferencedServices1.retainAll(services1);
			if(dupReferencedServices1.size() > 0){				  
				String proc1 = processList.get(i).getName();
				String proc2 = processList.get(k).getName();
				proc1 = proc1.substring(proc1.lastIndexOf(".")+1).concat(".bwp");
				proc2 = proc2.substring(proc2.lastIndexOf(".")+1).concat(".bwp");
				System.out.println("Check service "+dupReferencedServices1.toString()+" exposed by process "+proc1+" and consumed by process "+proc2+" for any possible deadlocks.");

			}

		}
	}
	
	public void checkDeadlock1(Set<String> services, Set<String> referencedServices){
		Set<String> dupReferencedServices = new HashSet<String>(referencedServices);
		dupReferencedServices.retainAll(services);
		if(dupReferencedServices.size() > 0){
			Set<String> services1 = processList.get(i).getServices().keySet();
			Set<String> referencedServices1 = processList.get(k).getProcessReferenceServices().keySet();
			Set<String> dupReferencedServices1 = new HashSet<String>(referencedServices1);
			dupReferencedServices1.retainAll(services1);
			if(dupReferencedServices1.size() > 0){				  
				String proc1 = processList.get(i).getName();
				String proc2 = processList.get(k).getName();
				proc1 = proc1.substring(proc1.lastIndexOf(".")+1).concat(".bwp");
				proc2 = proc2.substring(proc2.lastIndexOf(".")+1).concat(".bwp");
				System.out.println("Check service "+dupReferencedServices1.toString()+" exposed by process "+proc1+" and consumed by process "+proc2+" for any possible deadlocks.");

			}
		}else{
			
		}
	}*/
}

