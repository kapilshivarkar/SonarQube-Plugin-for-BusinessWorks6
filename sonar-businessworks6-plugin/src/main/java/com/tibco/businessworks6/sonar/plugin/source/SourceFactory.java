package com.tibco.businessworks6.sonar.plugin.source;

import java.io.File;

import com.tibco.businessworks6.sonar.plugin.file.XmlFile;

public class SourceFactory {

	
	
	
	@SuppressWarnings("unchecked")
	public static <S extends Source> S create(Class<S> clazz, File file) {
		if(clazz.equals(ProcessSource.class)){
			return (S) new ProcessSource(file);
		}else if(clazz.equals(XmlSource.class)){
			return (S) new XmlSource(file);
		}else if(clazz.equals(DefaultSource.class)){
			return (S) new DefaultSource(file);
		}
		return null;
	}
	
	/*@SuppressWarnings("unchecked")
	static public <S extends Source> S  create(Class<S> clazz, String code) {
		if(clazz.equals(ProcessSource.class)){
			return (S) new ProcessSource(code);
		}else if(clazz.equals(XmlSource.class)){
			return (S) new XmlSource(code);
		}else if(clazz.equals(DefaultSource.class)){
			return (S) new DefaultSource(code);
		}
		return null;
	}*/

	
	public static XmlSource create(XmlFile xmlFile) {
		return new XmlSource(xmlFile);
	}
	
}
