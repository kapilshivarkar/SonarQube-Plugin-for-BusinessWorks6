/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tibco.businessworks6.sonar.plugin.sensor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
//import org.sonar.api.scan.filesystem.FileQuery;
//import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;
import com.tibco.utils.bw.model.Process;


/**
 * XmlSensor provides analysis of xml files.
 * 
 * @author Kapil Shivarkar
 */
public abstract class AbstractSensor implements Sensor {

	//protected ModuleFileSystem fileSystem1;
	protected FileSystem fileSystem;
	protected ResourcePerspectives resourcePerspectives;
	protected String languageKey;
	protected Project project;
	protected SensorContext sensorContext;
	public final CheckFactory checkFactory;
	public static Map<String, String> resourceExtensionMapper = new HashMap<String,String>();
	
	protected AbstractSensor(FileSystem fileSystem,
			ResourcePerspectives resourcePerspectives, String languageKey, CheckFactory checkFactory) {
		this.fileSystem = fileSystem;
		this.resourcePerspectives = resourcePerspectives;
		this.languageKey = languageKey;
		this.checkFactory = checkFactory;
		createResourceExtensionMapper(resourceExtensionMapper);
	}

	public void createResourceExtensionMapper(Map<String, String> resourceExtensionMapper){
		resourceExtensionMapper.put(".httpClientResource", "HTTP Client");
		resourceExtensionMapper.put(".authxml", "XML Authentication");
		resourceExtensionMapper.put(".wssResource", "WSS Authentication");
		resourceExtensionMapper.put(".trustResource", "Trust Provider");
		resourceExtensionMapper.put(".threadPoolResource", "Threal Pool");
		resourceExtensionMapper.put(".tcpResource", "TCP Connection");
		resourceExtensionMapper.put(".sipResource", "Subject Provider");
		resourceExtensionMapper.put(".sslServerResource", "SSL Server Configuration");
		resourceExtensionMapper.put(".sslClientResource", "SSL Client Configuration");
		resourceExtensionMapper.put(".smtpResource", "SMTP Resource");
		resourceExtensionMapper.put(".rvResource", "Rendezvous Transport");
		resourceExtensionMapper.put(".httpProxyResource", "Proxy Configuration");
		resourceExtensionMapper.put(".ldapResource", "LDAP Authentication");
		resourceExtensionMapper.put(".keystoreProviderResource", "Keystore Provider");
		resourceExtensionMapper.put(".jndiConfigResource", "JNDI Configuration");
		resourceExtensionMapper.put(".jmsConnResource", "JMS Connection");
		resourceExtensionMapper.put(".jdbcResource", "JDBC Connection");
		resourceExtensionMapper.put(".javaGlobalInstanceResource", "Java Global Instance");
		resourceExtensionMapper.put(".userIdResource", "Identity Provider");
		resourceExtensionMapper.put(".httpConnResource", "HTTP Connector");
		resourceExtensionMapper.put(".ftpResource", "FTP Connection");
		resourceExtensionMapper.put(".ftlResource", "FTL Realm Server Connection");
		resourceExtensionMapper.put(".dataFormatResource", "Data Format");
		resourceExtensionMapper.put(".sql", "SQL File");
	}
	
	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	/**
	 * Analyze the files.
	 */
	public void analyse(Project project, SensorContext sensorContext) {
		this.project = project;
		this.sensorContext = sensorContext;
		/*for (java.io.File file : fileSystem.files(FileQuery.onSource()
				.onLanguage(languageKey))) {*/
		for (File file : fileSystem.files(fileSystem.predicates().hasLanguage(languageKey))) {
			try {
				analyseFile(file);
			} catch (Exception e) {
				log().error(
						"Could not analyze the file " + file.getAbsolutePath(),
						e);
			}
		}
		analyseDeadLock(fileSystem.files(fileSystem.predicates().hasLanguage(languageKey)));
		processMetrics();
	}

	protected abstract void analyseDeadLock(Iterable<File> filesIterable);
	
	protected abstract void processMetrics();
	
	protected abstract void analyseFile(java.io.File file);

	/**
	 * This sensor only executes on projects with language files.
	 */
	public boolean shouldExecuteOnProject(Project project) {
		/*return !fileSystem1.files(FileQuery.onSource().onLanguage(languageKey))
				.isEmpty();*/
		return !fileSystem.files(fileSystem.predicates().hasLanguage(languageKey)).iterator().hasNext();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected Logger log() {
		return LoggerFactory.getLogger(getClass());
	}
}
