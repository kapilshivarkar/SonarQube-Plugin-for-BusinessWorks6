package com.tibco.businessworks6.sonar.plugin.source;

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

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import org.sonar.api.resources.Resource;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 * 
 * @author Kapil Shivarkar
 */
public class DefaultSource extends AbstractSource {

	private File file;

	public DefaultSource(File file) {
		super();
		this.file = file;
	}
	
	public DefaultSource(String code) {
		super();
		setCode(code);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.sonar.plugins.bw.source.Source#parseSource(org.sonar.api.scan
	 * .filesystem.ModuleFileSystem)
	 */
	public boolean parseSource(Charset charset) {
		if (code == null || code.isEmpty()) {
			try {
				code = Files.toString(file, charset);
			} catch (IOException e) {
				return false;
			}
			return true;
		}
		return true;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	public static AbstractSource create(File file) {
		return new DefaultSource(file);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public org.sonar.api.resources.File create(@Nullable Resource parent,@Nullable String key) {
		return new org.sonar.api.resources.File(file.getAbsolutePath());
	}

}
