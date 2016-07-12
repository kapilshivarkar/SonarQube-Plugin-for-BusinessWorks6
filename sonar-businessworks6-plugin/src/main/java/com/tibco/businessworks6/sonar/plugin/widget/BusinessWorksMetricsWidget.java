package com.tibco.businessworks6.sonar.plugin.widget;

import org.sonar.api.web.*;

@UserRole(UserRole.USER)
@WidgetCategory({"BusinessWorks"})
@WidgetScope("GLOBAL")
@Description("BusinessWorks 6 Project Statistics")
@WidgetProperties({
	  @WidgetProperty(key = "max", type = WidgetPropertyType.INTEGER, defaultValue = "80")
	})
public class BusinessWorksMetricsWidget extends AbstractRubyTemplate implements
		RubyRailsWidget {

	public String getId() {
		return "BusinessWorksProjectMetrics";
	}

	public String getTitle() {
		return "BusinessWorks Project Metrics";
	}

	@Override
	protected String getTemplatePath() {
		return "/com/tibco/businessworks6/sonar/plugin/widget/BusinessWorksMetrics.html.erb";
	}

}
