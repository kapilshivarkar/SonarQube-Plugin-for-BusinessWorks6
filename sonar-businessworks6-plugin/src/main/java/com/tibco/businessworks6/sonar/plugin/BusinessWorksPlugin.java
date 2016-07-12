package com.tibco.businessworks6.sonar.plugin;

import java.util.List;
import com.google.common.collect.ImmutableList;

import com.tibco.businessworks6.sonar.plugin.ProcessExtensions;

import org.sonar.api.SonarPlugin;

public class BusinessWorksPlugin extends SonarPlugin {
	
	public static final String TIBCO_BUSINESSWORK_CATEGORY = "TIBCO BusinessWorks 6";
	public static final String TIBCO_BUSINESSWORK_RULEREPOSITORY = "bwcommon";
	public static final String PROCESS_FILE_SUFFIXES_KEY = "sonar.bw.process.file.suffixes";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getExtensions() {
		// TODO Auto-generated method stub
		ImmutableList.Builder<Object> builder = ImmutableList.builder();		
		builder.addAll(ProcessExtensions.getExtensions());
		builder.addAll(CommonExtensions.getExtensions());
		return builder.build();
	}

}
