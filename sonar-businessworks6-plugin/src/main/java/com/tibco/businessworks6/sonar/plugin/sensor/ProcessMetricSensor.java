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
package com.tibco.businessworks6.sonar.plugin.sensor;

import java.io.File;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.component.ResourcePerspectives;

import com.tibco.businessworks6.sonar.plugin.language.ProcessLanguage;

public class ProcessMetricSensor extends AbstractMetricSensor {

	protected ProcessMetricSensor(FileSystem fileSystem, ResourcePerspectives resourcePerspectives, String languageKey,
			CheckFactory checkFactory) {
		super(fileSystem, resourcePerspectives, languageKey, checkFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void analyseDeadLock(Iterable<File> filesIterable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processMetrics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void analyseFile(File file) {
		// TODO Auto-generated method stub
		
	}

	public ProcessMetricSensor(
			FileSystem fileSystem,
			ResourcePerspectives resourcePerspectives, CheckFactory checkFactory) {
		super(fileSystem, resourcePerspectives, ProcessLanguage.KEY, checkFactory);
	}

}
