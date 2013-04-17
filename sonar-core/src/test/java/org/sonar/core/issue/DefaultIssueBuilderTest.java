/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.core.issue;

import org.junit.Test;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.Severity;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultIssueBuilderTest {

  OnIssueCreation callback = mock(OnIssueCreation.class);

  @Test
  public void should_create_issue() throws Exception {
    String componentKey = "org.apache.struts:struts-core:Action.java";
    DefaultIssue issue = (DefaultIssue) new DefaultIssueBuilder(callback, componentKey)
      .message("msg")
      .line(123)
      .cost(10000.0)
      .ruleKey(RuleKey.of("squid", "NullDereference"))
      .severity(Severity.CRITICAL)
      .done();

    assertThat(issue).isNotNull();
    assertThat(issue.key()).isNull();
    assertThat(issue.cost()).isEqualTo(10000.0);
    assertThat(issue.componentKey()).isEqualTo(componentKey);
    assertThat(issue.message()).isEqualTo("msg");
    assertThat(issue.line()).isEqualTo(123);
    assertThat(issue.ruleKey().repository()).isEqualTo("squid");
    assertThat(issue.ruleKey().rule()).isEqualTo("NullDereference");
    assertThat(issue.severity()).isEqualTo(Severity.CRITICAL);
    assertThat(issue.updatedAt()).isNull();
    assertThat(issue.closedAt()).isNull();
    assertThat(issue.assigneeLogin()).isNull();
    assertThat(issue.isNew()).isTrue();
    verify(callback).onIssueCreation(issue);
  }
}