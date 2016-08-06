/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.ea;

import com.intellij.lang.Language;
import com.perl5.lang.perl.PerlLanguage;

/**
 * Created by hurricup on 03.08.2016.
 */
public class PerlExternalAnnotationsLanguage extends Language
{
	public static final String NAME = "Perl5 External Annotations";
	public static final PerlExternalAnnotationsLanguage INSTANCE = new PerlExternalAnnotationsLanguage();

	private PerlExternalAnnotationsLanguage()
	{
		super(PerlLanguage.INSTANCE, NAME);
	}

}