/*
 * Copyright 2015-2018 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.run.prove;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.perl5.lang.perl.fileTypes.PerlFileTypeTest;
import com.perl5.lang.perl.idea.run.GenericPerlRunConfigurationProducer;
import org.jetbrains.annotations.NotNull;

public class PerlTestRunConfigurationProducer extends GenericPerlRunConfigurationProducer<PerlTestRunConfiguration> {
  @NotNull
  @Override
  public ConfigurationFactory getConfigurationFactory() {
    return PerlTestRunConfigurationType.getInstance().getConfigurationFactories()[0];
  }

  protected boolean isOurFile(@NotNull VirtualFile virtualFile) {
    return virtualFile.getFileType() == PerlFileTypeTest.INSTANCE;
  }

  @NotNull
  public static PerlTestRunConfigurationProducer getInstance() {
    return getInstance(PerlTestRunConfigurationProducer.class);
  }
}
