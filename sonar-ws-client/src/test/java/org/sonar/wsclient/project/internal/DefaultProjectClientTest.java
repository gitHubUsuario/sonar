/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
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
package org.sonar.wsclient.project.internal;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.wsclient.MockHttpServerInterceptor;
import org.sonar.wsclient.internal.HttpRequestFactory;
import org.sonar.wsclient.project.NewProject;
import org.sonar.wsclient.project.Project;
import org.sonar.wsclient.project.ProjectClient;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

public class DefaultProjectClientTest {

  @Rule
  public MockHttpServerInterceptor httpServer = new MockHttpServerInterceptor();

  @Test
  public void should_create_project() {
    HttpRequestFactory requestFactory = new HttpRequestFactory(httpServer.url());

    httpServer.stubResponseBody("{\"id\":\"1\",\"k\":\"polop\",\"nm\":\"Polop\",\"sc\":\"PRJ\",\"qu\":\"TRK\"}");

    ProjectClient client = new DefaultProjectClient(requestFactory);
    Project result = client.create(NewProject.create().key("polop").name("Polop"));

    assertThat(httpServer.requestedPath()).isEqualTo("/api/projects/create");
    assertThat(httpServer.requestParams()).includes(
      entry("key", "polop"),
      entry("name", "Polop")
    );
    assertThat(result).isNotNull();

  }
}
