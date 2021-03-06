/*
 * Copyright 2015-2019 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.perl5.PerlBundle;
import com.perl5.lang.perl.xsubs.PerlXSubsState;
import org.jetbrains.annotations.NotNull;

public class PerlRegenerateXSubsAction extends PerlActionBase {

  public PerlRegenerateXSubsAction() {
    super(PerlBundle.message("perl.action.generate.xsubs"));
  }

  @Override
  protected boolean isEnabled(@NotNull AnActionEvent event) {
    return super.isEnabled(event) && event.getProject() != null;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    if (project == null) {
      return;
    }
    PerlXSubsState.getInstance(project).reparseXSubs();
  }
}
