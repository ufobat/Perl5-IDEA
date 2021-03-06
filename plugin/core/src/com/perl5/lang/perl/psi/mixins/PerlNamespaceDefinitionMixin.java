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

package com.perl5.lang.perl.psi.mixins;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.Processor;
import com.perl5.PerlIcons;
import com.perl5.lang.perl.extensions.packageprocessor.PerlMroProvider;
import com.perl5.lang.perl.extensions.packageprocessor.PerlPackageProcessor;
import com.perl5.lang.perl.idea.PerlElementPatterns;
import com.perl5.lang.perl.idea.presentations.PerlItemPresentationSimple;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.impl.PerlUseStatementElement;
import com.perl5.lang.perl.psi.mro.PerlMroType;
import com.perl5.lang.perl.psi.stubs.namespaces.PerlNamespaceDefinitionStub;
import com.perl5.lang.perl.psi.utils.PerlNamespaceAnnotations;
import com.perl5.lang.perl.psi.utils.PerlPsiUtil;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public abstract class PerlNamespaceDefinitionMixin extends PerlStubBasedPsiElementBase<PerlNamespaceDefinitionStub>
  implements StubBasedPsiElement<PerlNamespaceDefinitionStub>,
             PerlNamespaceDefinitionWithIdentifier,
             PerlElementPatterns,
             PerlCompositeElement {
  public PerlNamespaceDefinitionMixin(@NotNull ASTNode node) {
    super(node);
  }

  public PerlNamespaceDefinitionMixin(@NotNull PerlNamespaceDefinitionStub stub, @NotNull IStubElementType nodeType) {
    super(stub, nodeType);
  }

  @Nullable
  @Override
  public PsiPerlNamespaceContent getNamespaceContent() {
    return PsiTreeUtil.getChildOfType(this, PsiPerlNamespaceContent.class);
  }

  @Nullable
  @Override
  public String getName() {
    return getNamespaceName();
  }

  @Nullable
  @Override
  public PerlNamespaceElement getNamespaceElement() {
    return findChildByClass(PerlNamespaceElement.class);
  }

  @Nullable
  @Override
  public PsiElement getNameIdentifier() {
    return getNamespaceElement();
  }

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    return PerlPsiUtil.renameNamedElement(this, name);
  }


  @Nullable
  @Override
  public String getNamespaceName() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getNamespaceName();
    }

    return computeNamespaceName();
  }

  protected String computeNamespaceName() {
    PerlNamespaceElement namespaceElement = getNamespaceElement();
    if (namespaceElement != null) {
      return namespaceElement.getCanonicalName();
    }

    return null;
  }

  @NotNull
  @Override
  public List<String> getParentNamespacesNames() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getParentNamespacesNames();
    }
    return PerlPackageUtil.collectParentNamespacesFromPsi(this);
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    return PerlIcons.PACKAGE_GUTTER_ICON;
  }

  @Override
  public ItemPresentation getPresentation() {
    return new PerlItemPresentationSimple(this, getPresentableName());
  }

  @NotNull
  @Override
  public PerlMroType getMroType() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getMroType();
    }

    return CachedValuesManager.getCachedValue(this, () -> {
      MroSearcher searcher = new MroSearcher();
      PerlPsiUtil.processNamespaceStatements(this, searcher);
      return CachedValueProvider.Result.create(searcher.getResult(), PerlNamespaceDefinitionMixin.this);
    });
  }



  @NotNull
  @Override
  public List<String> getEXPORT() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getEXPORT();
    }

    return getExporterInfo().getEXPORT();
  }

  @NotNull
  @Override
  public List<String> getEXPORT_OK() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getEXPORT_OK();
    }

    return getExporterInfo().getEXPORT_OK();
  }

  @NotNull
  @Override
  public Map<String, List<String>> getEXPORT_TAGS() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getEXPORT_TAGS();
    }

    return getExporterInfo().getEXPORT_TAGS();
  }

  @Override
  public int getTextOffset() {
    PsiElement nameIdentifier = getNameIdentifier();

    return nameIdentifier == null
           ? super.getTextOffset()
           : nameIdentifier.getTextOffset();
  }

  @NotNull
  public ExporterInfo getExporterInfo() {
    return CachedValuesManager.getCachedValue(this, () -> {
      ExporterInfo result = new ExporterInfo();
      PerlPsiUtil.processNamespaceStatements(this, result);
      return CachedValueProvider.Result.create(result, PerlNamespaceDefinitionMixin.this);
    });
  }

  @Nullable
  @Override
  public PerlNamespaceAnnotations getAnnotations() {
    PerlNamespaceDefinitionStub stub = getGreenStub();
    if (stub != null) {
      return stub.getAnnotations();
    }
    else {
      // re-parsing
      return getLocalAnnotations();
    }
  }

  @Nullable
  public PerlNamespaceAnnotations getLocalAnnotations() {
    return PerlNamespaceAnnotations.tryToFindAnnotations(this);
  }

  @Override
  public String toString() {
    return super.toString() + "@" + getNamespaceName();
  }

  public static class MroSearcher implements Processor<PsiElement> {
    public int counter = 0;
    @NotNull
    private PerlMroType myResult = PerlMroType.DFS;

    @Override
    public boolean process(PsiElement element) {
      if (element instanceof PerlUseStatementElement) {
        PerlPackageProcessor packageProcessor = ((PerlUseStatementElement)element).getPackageProcessor();
        if (packageProcessor instanceof PerlMroProvider) {
          myResult = ((PerlMroProvider)packageProcessor).getMroType((PerlUseStatementElement)element);
          return false;
        }
      }
      return true;
    }

    @NotNull
    public PerlMroType getResult() {
      return myResult;
    }
  }

  public static class ExporterInfo implements Processor<PsiElement> {
    @NotNull
    private final List<String> EXPORT = new ArrayList<>();
    @NotNull
    private final List<String> EXPORT_OK = new ArrayList<>();
    @NotNull
    private final Map<String, List<String>> EXPORT_TAGS = Collections.emptyMap();

    @Override
    public boolean process(PsiElement element) {
      if (ASSIGN_STATEMENT.accepts(element)) {
        if (EXPORT_ASSIGN_STATEMENT.accepts(element)) {
          PsiElement rightSide = element.getFirstChild().getLastChild();
          if (rightSide != null) {
            EXPORT.clear();
            EXPORT.addAll(getRightSideStrings(rightSide));
          }
        }
        else if (EXPORT_OK_ASSIGN_STATEMENT.accepts(element)) {
          PsiElement rightSide = element.getFirstChild().getLastChild();
          if (rightSide != null) {
            EXPORT_OK.clear();
            EXPORT_OK.addAll(getRightSideStrings(rightSide));
          }
        }
      }

      return true;
    }

    protected List<String> getRightSideStrings(@NotNull PsiElement rightSide) {
      List<String> result = new ArrayList<>();
      if (rightSide.getFirstChild() != null) {
        int lastEnd = -1;
        for (PsiElement psiElement : PerlPsiUtil.collectStringElements(rightSide.getFirstChild())) {
          String text = psiElement.getText();
          if (StringUtil.isNotEmpty(text)) {
            int newStart = psiElement.getNode().getStartOffset();
            if (newStart == lastEnd) // appending
            {
              int lastIndex = result.size() - 1;
              result.add(result.remove(lastIndex) + text);
            }
            else {
              result.add(text);
            }
            lastEnd = newStart + text.length();
          }
        }
      }
      return result;
    }

    @NotNull
    public List<String> getEXPORT() {
      return EXPORT;
    }

    @NotNull
    public List<String> getEXPORT_OK() {
      return EXPORT_OK;
    }

    @NotNull
    public Map<String, List<String>> getEXPORT_TAGS() {
      return EXPORT_TAGS;
    }
  }
}
