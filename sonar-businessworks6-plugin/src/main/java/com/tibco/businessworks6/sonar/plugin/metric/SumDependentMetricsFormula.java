/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.tibco.businessworks6.sonar.plugin.metric;

import org.sonar.api.measures.Formula;
import org.sonar.api.measures.FormulaContext;
import org.sonar.api.measures.FormulaData;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SumDependentMetricsFormula implements Formula {

  private Metric[] dependenciesMetrics;
  
  /**
   * This method should be private but it kep package-protected because.
   */
  SumDependentMetricsFormula(Metric[] dependenciesMetrics) {
    this.dependenciesMetrics = dependenciesMetrics;
  }


  /**
   * {@inheritDoc}
   */
  public List<Metric> dependsUponMetrics() {
    return newArrayList(dependenciesMetrics);
  }

  /**
   * {@inheritDoc}
   */
  public Measure calculate(FormulaData data, FormulaContext context) {
    if (!shouldDecorateResource(data, context)) {
      return null;
    }

    Double result = (double) 0;
    for(Metric metric:dependenciesMetrics){
    	result += MeasureUtils.getValue(data.getMeasure(metric),(double)0);
    }
    if(result.equals((double)0)){
    	result = null;
    }
    return new Measure(context.getTargetMetric(), result);
  }

  private boolean shouldDecorateResource(FormulaData data, FormulaContext context) {
    return !MeasureUtils.hasValue(data.getMeasure(context.getTargetMetric()));
  }

}
