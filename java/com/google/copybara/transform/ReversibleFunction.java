/*
 * Copyright (C) 2019 Google Inc.
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

package com.google.copybara.transform;

import com.google.copybara.NonReversibleValidationException;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;
import com.google.devtools.build.lib.skylarkinterface.SkylarkValue;
import java.util.function.Function;

@SkylarkModule(
    name = "mapping_function",
    category = SkylarkModuleCategory.BUILTIN,
    doc = "A function that given an object can map to another object")
public interface ReversibleFunction<T, R> extends Function<T, R>, SkylarkValue {

  /**
   * Create a reverse of the function
   */
  ReversibleFunction<R, T> reverseMapping() throws NonReversibleValidationException;
}
