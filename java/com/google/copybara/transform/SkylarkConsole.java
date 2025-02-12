/*
 * Copyright (C) 2018 Google Inc.
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

import com.google.copybara.doc.annotations.DocSignaturePrefix;
import com.google.copybara.util.console.AnsiColor;
import com.google.copybara.util.console.Console;
import com.google.devtools.build.lib.skylarkinterface.Param;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;
import com.google.devtools.build.lib.skylarkinterface.SkylarkValue;
import java.io.IOException;
import java.util.function.Predicate;
import javax.annotation.Nullable;

@SkylarkModule(
    name = "Console",
    category = SkylarkModuleCategory.BUILTIN,
    doc =
        "A console that can be used in skylark transformations to print info, warning or"
            + " error messages.")
@DocSignaturePrefix("console")
public class SkylarkConsole implements Console, SkylarkValue {

  private int errorCount = 0;
  private final Console delegate;

  public SkylarkConsole(Console delegate) {
    this.delegate = delegate;
  }

  @Override
  public void startupMessage(String version) {
    throw new UnsupportedOperationException("Shouldn't be called from skylark");
  }

  @SkylarkCallable(name = "error",
      doc = "Show an error in the log. Note that this will stop Copybara execution.",
      parameters = {@Param(name = "message", doc = "message to log", type = String.class)})
  @Override
  public void error(String message) {
    delegate.error(message);
    errorCount++;
  }

  @Override
  public boolean isVerbose() {
    return delegate.isVerbose();
  }

  @SkylarkCallable(name = "warn", doc = "Show a warning in the console",
      parameters = {@Param(name = "message", doc = "message to log", type = String.class)})
  @Override
  public void warn(String message) {
    delegate.warn(message);
  }

  @SkylarkCallable(name = "verbose",
      doc = "Show an info message in the console if verbose logging is enabled.",
      parameters = {@Param(name = "message", doc = "message to log", type = String.class)})
  @Override
  public void verbose(String message) {
    delegate.verbose(message);
  }

  @SkylarkCallable(name = "info", doc = "Show an info message in the console",
      parameters = {@Param(name = "message", doc = "message to log", type = String.class)})
  @Override
  public void info(String message) {
    delegate.info(message);
  }

  @SkylarkCallable(name = "progress", doc = "Show a progress message in the console",
      parameters = {@Param(name = "message", doc = "message to log", type = String.class)})
  @Override
  public void progress(String progress) {
    delegate.progress(progress);
  }

  @Override
  public boolean promptConfirmation(String message) {
    throw new UnsupportedOperationException("Shouldn't be called from skylark");
  }

  @Override
  public String colorize(AnsiColor ansiColor, String message) {
    return delegate.colorize(ansiColor, message);
  }

  @Override
  public String ask(String msg, @Nullable String defaultAnswer, Predicate<String> validator)
      throws IOException {
    return delegate.ask(msg, defaultAnswer, validator);
  }

  public int getErrorCount() {
    return errorCount;
  }
}
