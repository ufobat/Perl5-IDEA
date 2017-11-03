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

package editor;

import com.perl5.PerlBundle;

public class PerlIntentionsTest extends PerlIntentionsTestCase {

  public void testModifierToStatementFor() {doTestConvertToCompound();}

  public void testModifierToStatementForeach() {doTestConvertToCompound();}

  public void testModifierToStatementIf() {doTestConvertToCompound();}

  public void testModifierToStatementIfDo() {doTestConvertToCompound();}

  public void testModifierToStatementIfEmpty() {doTestConvertToCompound(false);}

  public void testModifierToStatementIfParenthesized() {doTestConvertToCompound();}

  public void testModifierToStatementUnless() {doTestConvertToCompound();}

  public void testModifierToStatementUntil() {doTestConvertToCompound();}

  public void testModifierToStatementWhen() {doTestConvertToCompound();}

  public void testModifierToStatementWhile() {doTestConvertToCompound();}

  public void testModifierToStatementWithError() {doTestNoIntention(PerlBundle.message("perl.intention.convert.to.compound"));}

  private void doTestConvertToCompound(boolean checkErrors) {
    doTestIntention(PerlBundle.message("perl.intention.convert.to.compound"), checkErrors);
  }

  private void doTestConvertToCompound() {doTestConvertToCompound(true);}

  public void testForeachToFor() {doTestForeachToForIntention();}

  public void testForeachToForFor() {doTestForeachToForIntention();}

  private void doTestForeachToForIntention() {doTestIntention(PerlBundle.message("perl.intention.foreach.to.for"));}

  public void testStringToHeredocQQ() {doTestConvertToHeredoc();}

  public void testStringToHeredocQQMnemonic() {doTestConvertToHeredoc();}

  public void testStringToHeredocSQ() {doTestConvertToHeredoc();}

  public void testStringToHeredocSQMnemonic() {doTestConvertToHeredoc();}

  public void testStringToHeredocXQ() {doTestConvertToHeredoc();}

  public void testStringToHeredocXQMnemonic() {doTestConvertToHeredoc();}

  private void doTestConvertToHeredoc() {
    doTestIntention(PerlBundle.message("perl.intention.heredoc.last.prefix"));
  }
}