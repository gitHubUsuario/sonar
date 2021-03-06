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
package org.sonar.api.platform;

import org.sonar.api.BatchComponent;
import org.sonar.api.Plugin;
import org.sonar.api.ServerComponent;

import java.util.Collection;

public interface PluginRepository extends BatchComponent, ServerComponent {
  Plugin getPlugin(String key);

  /**
   * Metadata of installed plugins. Metadata includes all the fields available in update center
   * (plugin key, name, version, description, license, ...) and some technical information like
   * list of embedded libraries and classloader strategy.
   * 
   * @since 2.9
   */
  Collection<PluginMetadata> getMetadata();

  /**
   * Search for an installed plugin. Returns null if the plugin is not installed.
   * @since 2.9
   */
  PluginMetadata getMetadata(String pluginKey);
}
