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

package com.perl5.lang.perl.idea.intellilang;

import com.intellij.psi.PsiLanguageInjectionHost;
import com.perl5.lang.perl.psi.PerlCompositeElement;
import org.intellij.plugins.intelliLang.inject.AbstractLanguageInjectionSupport;
import org.jetbrains.annotations.NotNull;

public class PerlInjectionSupport extends AbstractLanguageInjectionSupport {
  private static final Class[] PATTERNS_CLASSES = new Class[]{PerlInjectionPatterns.class};

  @NotNull
  @Override
  public String getId() {
    return "perl5";
  }

  @Override
  public boolean isApplicableTo(PsiLanguageInjectionHost host) {
    return host instanceof PerlCompositeElement;
  }

  @Override
  public boolean useDefaultInjector(PsiLanguageInjectionHost host) {
    return true;
  }

  @NotNull
  @Override
  public Class[] getPatternClasses() {
    return PATTERNS_CLASSES;
  }
}
