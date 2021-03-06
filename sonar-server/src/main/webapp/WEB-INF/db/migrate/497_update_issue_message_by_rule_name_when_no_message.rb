#
# SonarQube, open source software quality management tool.
# Copyright (C) 2008-2013 SonarSource
# mailto:contact AT sonarsource DOT com
#
# SonarQube is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.
#
# SonarQube is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
#

#
# Sonar 4.2
# SONAR-4785
#
class UpdateIssueMessageByRuleNameWhenNoMessage < ActiveRecord::Migration

  class Rule < ActiveRecord::Base
  end

  class Issue < ActiveRecord::Base
    belongs_to :rule, :class_name => 'Rule', :foreign_key => 'rule_id'
  end

  def self.up
    issuesWithoutMessage = Issue.all(
        :include => :rule,
        :conditions => 'message IS NULL'
    )
    issuesWithoutMessage.each do |issue|
      if issue.rule
        issue.message = issue.rule.name
        issue.save!
      end
    end
  end
end
