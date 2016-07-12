/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.tibco.businessworks6.sonar.plugin.language;

import org.sonar.api.config.Settings;

import com.tibco.businessworks6.sonar.plugin.BusinessWorksPlugin;

/**
 * Process language implementation
 * 
 * @since 1.3
 */
public class ProcessLanguage extends AbstractBusinessWorksLanguage {

	public ProcessLanguage() {
		super(KEY, LANGUAGE_NAME);
	}
	
	public ProcessLanguage(Settings settings) {		
		super(settings,KEY,LANGUAGE_NAME);
	}

	public static final ProcessLanguage INSTANCE = new ProcessLanguage();

	/**
	 * Process key
	 */
	public static final String KEY = "process";

	/**
	 * Process name
	 */
	public static final String LANGUAGE_NAME = "BusinessWorks 6";

	/**
	 * Key of the file suffix parameter
	 */
	public static final String FILE_SUFFIXES_KEY = BusinessWorksPlugin.PROCESS_FILE_SUFFIXES_KEY;

	/**
	 * Default Process files knows suffixes
	 */
	//public static final String[] DEFAULT_FILE_SUFFIXES = { ".process" };
	public static final String[] DEFAULT_FILE_SUFFIXES = { ".bwp" };
	public String[] getFileSuffixes() {
		return getFileSuffixes(FILE_SUFFIXES_KEY, DEFAULT_FILE_SUFFIXES);
	}

}
