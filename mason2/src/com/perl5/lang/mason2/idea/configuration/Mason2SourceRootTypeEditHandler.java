/*
 * Copyright 2015-2017 Alexandr Evstigneev
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

package com.perl5.lang.mason2.idea.configuration;

import com.perl5.lang.mason2.Mason2Icons;
import com.perl5.lang.mason2.MasonBundle;
import com.perl5.lang.perl.idea.configuration.settings.sdk.PerlTemplatesRootEditHandler;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Mason2SourceRootTypeEditHandler extends PerlTemplatesRootEditHandler {
  public Mason2SourceRootTypeEditHandler() {
    super(Mason2SourceRootType.INSTANCE);
  }

  @NotNull
  @Override
  public String getRootTypeName() {
    return MasonBundle.message("mason2.root.type");
  }

  @NotNull
  @Override
  public Icon getRootIcon() {
    return Mason2Icons.ROOT_ICON;
  }

  @NotNull
  @Override
  public String getUnmarkRootButtonText() {
    return MasonBundle.message("mason2.root.unmark");
  }
}
