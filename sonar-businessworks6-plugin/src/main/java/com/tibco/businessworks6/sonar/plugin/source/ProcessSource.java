package com.tibco.businessworks6.sonar.plugin.source;

import java.io.File;
import java.nio.charset.Charset;
import com.tibco.businessworks6.sonar.plugin.file.XmlFile;
import com.tibco.utils.bw.common.SaxParser;
import com.tibco.utils.bw.model.Process;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 * 
 * @author Kapil Shivarkar
 */
public class ProcessSource extends XmlSource {

	private Process process;

	public ProcessSource(File file){
		super(file);
		this.process = new Process();
		process.setProcessXmlDocument(new SaxParser().parseDocument(file, true));	
	}

	public ProcessSource(XmlFile xmlFile) {
		super(xmlFile);
		this.process = new Process();	
	}

	/*public ProcessSource(String code) {
		super(code);
		setCode(code);
		this.process = new Process();
		InputStream is = createInputStream();
		process.setProcessXmlDocument(new SaxParser().parseDocument(is, true));		
	}*/

	@Override
	public boolean parseSource(Charset charset) {
		boolean result = super.parseSource(charset);
		/*if(result){
			Process proc = process.setProcessXmlDocument(getDocument(true));
			//proc.parse();
			//proc.myparse();
		}*/
		return result;
	}

	public void setProcessModel(Process process){
		this.process = process;
	}

	public Process getProcessModel(){
		return process;
	}
}
