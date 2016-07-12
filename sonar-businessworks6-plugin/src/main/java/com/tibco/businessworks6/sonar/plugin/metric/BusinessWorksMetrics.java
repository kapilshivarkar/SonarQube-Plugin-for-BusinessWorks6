package com.tibco.businessworks6.sonar.plugin.metric;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.SumChildValuesFormula;
import org.sonar.api.utils.SonarException;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tibco.businessworks6.sonar.plugin.sensor.ProcessRuleSensor;

public class BusinessWorksMetrics implements Metrics{

	public static final String BWLANGUAGEFLAG_KEY = "isbwproject";
	public static final Metric BWLANGUAGEFLAG = new Metric.Builder(BWLANGUAGEFLAG_KEY,
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource is a TIBCO BusinessWorks project or module")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	
	/*
	 * 
	 * Module Properties metrics
	 * 
	 */
	
	public static final String GLOBALVARIABLES_KEY = "globalvariables";
	public static final Metric GLOBALVARIABLES = new Metric.Builder(GLOBALVARIABLES_KEY,
			"Module Properties", Metric.ValueType.INT)
			.setDescription("Total number of module properties")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String JOBSHAREDVARIABLES_KEY = "jobsharedvariables";
	public static final Metric JOBSHAREDVARIABLES = new Metric.Builder(JOBSHAREDVARIABLES_KEY,
			"Job Shared Variables", Metric.ValueType.INT)
			.setDescription("Total number of job shared variables")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String MODULESHAREDVARIABLES_KEY = "modulesharedvariables";
	public static final Metric MODULESHAREDVARIABLES = new Metric.Builder(MODULESHAREDVARIABLES_KEY,
			"Module Shared Variables", Metric.ValueType.INT)
			.setDescription("Total number of module shared variables")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String CATCHBLOCK_KEY = "catchblock";
	public static final Metric CATCHBLOCK = new Metric.Builder(CATCHBLOCK_KEY,
			"Catch Blocks", Metric.ValueType.INT)
			.setDescription("Total number of catch blocks")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String EVENTHANDLER_KEY = "eventhandler";
	public static final Metric EVENTHANDLER = new Metric.Builder(EVENTHANDLER_KEY,
			"Event Handlers", Metric.ValueType.INT)
			.setDescription("Total number of event handlers")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	/*
	 * 
	 * BW resources metrics
	 * 
	 */
	
	public static Metric[] resourceMetrics(){
		Iterator<Map.Entry<String, Integer>> it = ProcessRuleSensor.foundResources.entrySet().iterator();
		Metric[] BWRESOURCES_METRICS_LIST = new Metric[ProcessRuleSensor.foundResources.size()];
		int i = 0 ;
		while (it.hasNext()) {
	        Map.Entry<String, Integer> pair = it.next();
	        String BWRESOURCE_KEY = pair.getKey().replaceAll("\\s","");
	        Metric BWRESOURCE = new Metric.Builder(BWRESOURCE_KEY,
	    			pair.getKey(), Metric.ValueType.INT)
	    			.setDescription("Total of shared resources")
	    			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
	    			.setFormula(new SumChildValuesFormula(false))
	    			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	        BWRESOURCES_METRICS_LIST[i] = BWRESOURCE;
	        i++;
	    }
		return BWRESOURCES_METRICS_LIST;
	}
	
	public static final Metric BWRESOURCES_HTTP_CONNECTION_FLAG = new Metric.Builder("ishttpclient",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();	
	public static final String BWRESOURCES_HTTP_CONNECTION_KEY = "HTTPClient";
	public static final Metric BWRESOURCES_HTTP_CONNECTION = new Metric.Builder(BWRESOURCES_HTTP_CONNECTION_KEY,
			"HTTP Clients", Metric.ValueType.INT)
			.setDescription("Total of shared HTTP connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric BWRESOURCES_HTTP_CONNECTOR_FLAG = new Metric.Builder("ishttpconnector",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final String BWRESOURCES_HTTP_CONNECTOR_KEY = "HTTPConnector";
	public static final Metric BWRESOURCES_HTTP_CONNECTOR = new Metric.Builder(BWRESOURCES_HTTP_CONNECTOR_KEY,
			"HTTP Connectors", Metric.ValueType.INT)
			.setDescription("Total of shared HTTP connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	
	public static final Metric BWRESOURCES_JDBC_CONNECTION_FLAG = new Metric.Builder("isjdbcconnection",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final String BWRESOURCES_JDBC_CONNECTION_KEY = "JDBCConnection";
	public static final Metric BWRESOURCES_JDBC_CONNECTION = new Metric.Builder(BWRESOURCES_JDBC_CONNECTION_KEY,
			"JDBC Connections", Metric.ValueType.INT)
			.setDescription("Total of shared JDBC connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric BWRESOURCES_JMS_CONNECTION_FLAG = new Metric.Builder("isjmsconnection",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final String BWRESOURCES_JMS_CONNECTION_KEY = "JMSConnection";
	public static final Metric BWRESOURCES_JMS_CONNECTION = new Metric.Builder(BWRESOURCES_JMS_CONNECTION_KEY,
			"JMS Connections", Metric.ValueType.INT)
			.setDescription("Total of shared JMS connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric XML_AUTHENTICATION_FLAG = new Metric.Builder("isxmlauthentication",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final String XML_AUTHENTICATION_KEY = "XMLAuthentication";
	public static final Metric XML_AUTHENTICATION = new Metric.Builder(XML_AUTHENTICATION_KEY,
			"XML Authentication", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric SSL_Server_Configuration_Flag = new Metric.Builder("issslserverconfiguration",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric SSL_Server_Configuration = new Metric.Builder("SSLServerConfiguration",
			"SSL Server Configuration", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric SSL_Client_Configuration_Flag = new Metric.Builder("issslclientconfiguration",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric SSL_Client_Configuration = new Metric.Builder("SSLClientConfiguration",
			"SSL Client Configuration", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	
	public static final Metric SMTP_Resource_Flag = new Metric.Builder("issmtpresource",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric SMTP_Resource = new Metric.Builder("SMTPResource",
			"SMTP Resource", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Data_Format_Flag = new Metric.Builder("isdataformat",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Data_Format = new Metric.Builder("DataFormat",
			"Data Format", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric SQL_File_Flag = new Metric.Builder("issqlfile",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric SQL_File = new Metric.Builder("SQLFile",
			"SQL File", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric FTL_Realm_Server_Connection_Flag = new Metric.Builder("isftlrealmserver",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric FTL_Realm_Server_Connection = new Metric.Builder("FTLRealmServerConnection",
			"FTL Realm Server Connection", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric FTP_Connection_Flag = new Metric.Builder("isftpconnection",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric FTP_Connection = new Metric.Builder("FTPConnection",
			"FTP Connection", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Identity_Provider_Flag = new Metric.Builder("isidentityprovider",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Identity_Provider = new Metric.Builder("IdentityProvider",
			"Identity Provider", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Java_Global_Instance_Flag = new Metric.Builder("isjavaglobalinstance",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Java_Global_Instance = new Metric.Builder("JavaGlobalInstance",
			"Java Global Instance", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric JNDI_Configuration_Flag = new Metric.Builder("isjndiconfiguration",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric JNDI_Configuration = new Metric.Builder("JNDIConfiguration",
			"JNDI Configuration", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Keystore_Provider_Flag = new Metric.Builder("iskeystoreprovider",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Keystore_Provider = new Metric.Builder("KeystoreProvider",
			"Keystore Provider", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric LDAP_Authentication_Flag = new Metric.Builder("isldapauthentication",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric LDAP_Authentication = new Metric.Builder("LDAPAuthentication",
			"LDAP Authentication", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Proxy_Configuration_Flag = new Metric.Builder("isproxyconfiguration",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Proxy_Configuration = new Metric.Builder("ProxyConfiguration",
			"Proxy Configuration", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Trust_Provider_Flag = new Metric.Builder("istrustprovider",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Trust_Provider = new Metric.Builder("TrustProvider",
			"Trust Provider", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Threal_Pool_Flag = new Metric.Builder("isthreadpool",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Threal_Pool = new Metric.Builder("ThrealPool",
			"Threal Pool", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric Subject_Provider_Flag = new Metric.Builder("issubjectprovider",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric Subject_Provider = new Metric.Builder("SubjectProvider",
			"Subject Provider", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric TCP_Connection_Flag = new Metric.Builder("istcpconnection",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric TCP_Connection = new Metric.Builder("TCPConnection",
			"TCP Connection", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric WSS_Authentication_FLAG = new Metric.Builder("isWSSAuthentication",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final Metric WSS_Authentication = new Metric.Builder("WSSAuthentication",
			"WSS Authentication", Metric.ValueType.INT)
			.setDescription("Total resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final Metric RVTRANSPORTFLAG = new Metric.Builder("isrvtransport",
			"TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
			.setDescription("Equals true if the resource exists in the project")
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).create();
	public static final String BWRESOURCES_RV_TRANSPORT_KEY = "RendezvousTransport";
	public static final Metric BWRESOURCES_RV_TRANSPORT = new Metric.Builder(BWRESOURCES_RV_TRANSPORT_KEY,
			"RV Transport", Metric.ValueType.INT)
			.setDescription("Total of RV Transport resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	
	public static final String BWRESOURCES_KEY = "bwresources";
	public static final Metric[] BWRESOURCES_METRICS_LIST = {
		BWRESOURCES_JDBC_CONNECTION,
		Java_Global_Instance,
		BWRESOURCES_JMS_CONNECTION,
		BWRESOURCES_HTTP_CONNECTION,
		BWRESOURCES_RV_TRANSPORT,
		XML_AUTHENTICATION,
		WSS_Authentication,
		TCP_Connection,
		Subject_Provider,
		Threal_Pool,
		Trust_Provider,
		Proxy_Configuration,
		LDAP_Authentication,
		Keystore_Provider,
		JNDI_Configuration,
		Identity_Provider,
		FTP_Connection,
		FTL_Realm_Server_Connection,
		SQL_File,
		SSL_Server_Configuration,
		SSL_Client_Configuration,
		Data_Format,
		SMTP_Resource,
	};

	//public static final Metric[] BWRESOURCES_METRICS_LIST = resourceMetrics();
			
			
	public static final Metric BWRESOURCES = new Metric.Builder(BWRESOURCES_KEY,
			"Resources", Metric.ValueType.INT)
			.setDescription("Number of BusinessWorks resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumDependentMetricsFormula(BWRESOURCES_METRICS_LIST))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	/*
	 * 
	 * 
	 * Processes metrics
	 * 
	 */
	
	public static final String PROCESSES_KEY = "processes";
	public static final Metric PROCESSES = new Metric.Builder(PROCESSES_KEY,
			"Processes", Metric.ValueType.INT)
			.setDescription("Number of processes")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String SUBPROCESSES_KEY = "subprocesses";
	public static final Metric SUBPROCESSES = new Metric.Builder(SUBPROCESSES_KEY,
			"SubProcesses", Metric.ValueType.INT)
			.setDescription("Number of sub processes")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();

	public static final String GROUPS_KEY = "groups";
	public static final Metric GROUPS = new Metric.Builder(GROUPS_KEY,
			"Groups", Metric.ValueType.INT).setDescription("Number of groups")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();

	public static final String ACTIVITIES_KEY = "activities";
	public static final Metric ACTIVITIES = new Metric.Builder(ACTIVITIES_KEY,
			"Activities", Metric.ValueType.INT)
			.setDescription("Number of activities")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();

	public static final String PROCESSSTARTER_KEY = "eventSources";
	public static final Metric PROCESSSTARTER = new Metric.Builder(PROCESSSTARTER_KEY,
			"Event Sources", Metric.ValueType.INT)
			.setDescription("Number of process starters/ event sources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String TRANSITIONS_KEY = "transitions";
	public static final Metric TRANSITIONS = new Metric.Builder(
			TRANSITIONS_KEY, "Transitions", Metric.ValueType.INT)
			.setDescription("Number of transitions")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();

	public static final String SERVICES_KEY = "services";
	public static final Metric SERVICES = new Metric.Builder(SERVICES_KEY,
			"Total Services Exposed", Metric.ValueType.INT)
			.setDescription("Exposed Services")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String SUBSERVICES_KEY = "subservices";
	public static final Metric SUBSERVICES = new Metric.Builder(SUBSERVICES_KEY,
			"Services", Metric.ValueType.INT)
			.setDescription("Services")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String SUBREFERENCE_KEY = "subreference";
	public static final Metric SUBREFERENCE = new Metric.Builder(SUBREFERENCE_KEY,
			"References", Metric.ValueType.INT)
			.setDescription("References")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String PROJECTCOMPLEXITY_KEY = "projectcomplexity";
	public static final Metric PROJECTCOMPLEXITY = new Metric.Builder(PROJECTCOMPLEXITY_KEY,
			"Project Complexity", Metric.ValueType.STRING)
			.setDescription("Project Complexity")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String CODEQUALITY_KEY = "codequality";
	public static final Metric CODEQUALITY = new Metric.Builder(CODEQUALITY_KEY,
			"Code Quality", Metric.ValueType.STRING)
			.setDescription("Code Quality")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setFormula(new SumChildValuesFormula(false))
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	private static final List<Metric> METRICS;

	static {
		METRICS = Lists.newLinkedList();
		for (Field field : BusinessWorksMetrics.class.getFields()) {
			if (Metric.class.isAssignableFrom(field.getType())) {
				try {
					Metric metric = (Metric) field.get(null);
					METRICS.add(metric);
				} catch (IllegalAccessException e) {
					throw new SonarException("can not introspect "
							+ CoreMetrics.class + " to get metrics", e);
				}
			}
		}
	}

	public List<Metric> getMetrics() {
		return METRICS;
	}

	public static Metric getMetric(final String key) {
		return Iterables.find(METRICS, new Predicate<Metric>() {
			public boolean apply(@Nullable Metric input) {
				return input != null && input.getKey().equals(key);
			}
		});
	}

}
