package com.tibco.businessworks6.sonar.plugin.check.process;

import com.tibco.businessworks6.sonar.plugin.check.AbstractProcessCheck;
import com.tibco.businessworks6.sonar.plugin.source.ProcessSource;
import com.tibco.businessworks6.sonar.plugin.violation.DefaultViolation;
import com.tibco.businessworks6.sonar.plugin.violation.Violation;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Service;
import java.util.Map;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(key="SubProcessInlineCheck", name="Data Availability to Inline SubProcess Check", priority=Priority.INFO, description="This rule checks if there is large set of data being passed everytime to Inline SubProcess. Use of Job Shared Variable is recommended in this scenario to increase performance.")
@BelongsToProfile(title="Sonar way", priority=Priority.INFO)
public class SubProcessInlineCheck
  extends AbstractProcessCheck
{
  public static final String RULE_KEY = "SubProcessInlineCheck";
  
  protected void validate(ProcessSource processSource)
  {
    Process process = processSource.getProcessModel();
    Map<String, Service> referenceServices = process.getProcessReferenceServices();
    for (String referenceService : referenceServices.keySet()) {
      if (((Service)referenceServices.get(referenceService)).getInline().equals("true"))
      {
        String proc = ((Service)referenceServices.get(referenceService)).getImplementationProcess();
        proc = proc.substring(proc.lastIndexOf(".") + 1).concat(".bwp");
        String parentprocess = process.getName();
        parentprocess = parentprocess.substring(parentprocess.lastIndexOf(".") + 1).concat(".bwp");
        Violation violation = new DefaultViolation(getRule(), 
          1, 
          "For performance reasons it is highly recommended to use Job Shared Variable instead of passing a large set of data when invoking Inline SubProcess " + proc + " from parent process " + parentprocess);
        processSource.addViolation(violation);
      }
    }
  }
}
