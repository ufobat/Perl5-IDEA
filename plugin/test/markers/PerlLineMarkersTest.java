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

package markers;


import base.PerlLightTestCase;
import com.intellij.codeInsight.daemon.GutterMark;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.navigation.GotoRelatedItem;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.UsefulTestCase;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
public class PerlLineMarkersTest extends PerlLightTestCase {
  @Override
  protected String getBaseDataPath() {
    return "testData/linemarkers/perl";
  }

  @Test
  public void testMooseAttrs() {doTest();}

  @Test
  public void testMojoAttrs() {doTest();}

  @Test
  public void testClassAccessor() {doTest();}

  @Test
  public void testSupermethods() {
    doTest();
  }

  @Test
  public void testExceptionClasses() {
    doTest();
  }

  private void doTest() {
    initWithFileSmart();
    List<GutterMark> allMarkers = myFixture.findAllGutters();
    String text = myFixture.getDocument(myFixture.getFile()).getText();
    StringBuilder b = new StringBuilder();
    for (GutterMark gutterMarker : allMarkers) {
      if (gutterMarker instanceof LineMarkerInfo.LineMarkerGutterIconRenderer) {
        LineMarkerInfo lineMarkerInfo = ((LineMarkerInfo.LineMarkerGutterIconRenderer)gutterMarker).getLineMarkerInfo();
        b.append(lineMarkerInfo.startOffset).append(" - ").append(lineMarkerInfo.endOffset).append(": ")
          .append('\'')
          .append(text, lineMarkerInfo.startOffset, lineMarkerInfo.endOffset)
          .append('\'')
          .append(": ")
          .append(lineMarkerInfo.getLineMarkerTooltip())
          .append("\n");

        if (!(lineMarkerInfo instanceof RelatedItemLineMarkerInfo)) {
          b.append("Uknown targets: ").append(lineMarkerInfo.getClass().getSimpleName()).append("\n");
          continue;
        }

        Collection<GotoRelatedItem> gotoRelatedItems = ((RelatedItemLineMarkerInfo)lineMarkerInfo).createGotoRelatedItems();
        b.append("Targets: ").append(gotoRelatedItems.size()).append("\n");

        for (GotoRelatedItem gotoRelatedItem : gotoRelatedItems) {
          PsiElement element = gotoRelatedItem.getElement();
          if (element != null) {
            b.append("\t").append(serializePsiElement(element)).append("\n");
          }
        }
      }
      else {
        b.append(gutterMarker);
      }
      b.append("\n");
    }
    UsefulTestCase.assertSameLinesWithFile(getTestResultsFilePath(), b.toString());
  }
}
