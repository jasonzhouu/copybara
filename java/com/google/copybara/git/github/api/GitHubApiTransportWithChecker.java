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

package com.google.copybara.git.github.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.copybara.checks.Checker;
import com.google.copybara.exception.RepoException;
import com.google.copybara.exception.ValidationException;
import com.google.copybara.util.console.Console;
import java.io.IOException;
import java.lang.reflect.Type;

public class GitHubApiTransportWithChecker implements GitHubApiTransport {

  private final GitHubApiTransport delegate;
  private final Checker checker;
  private final Console console;

  public GitHubApiTransportWithChecker(
      GitHubApiTransport delegate, Checker checker, Console console) {
    this.delegate = Preconditions.checkNotNull(delegate);
    this.checker = Preconditions.checkNotNull(checker);
    this.console = Preconditions.checkNotNull(console);
  }

  @Override
  public <T> T get(String path, Type responseType, ImmutableListMultimap<String, String> headers)
      throws RepoException, ValidationException {
    try {
      checker.doCheck(
          ImmutableMap.of("path", path, "response_type", responseType.toString()), console);
    } catch (IOException e) {
      throw new RuntimeException("Error running checker", e);
    }
    return delegate.get(path, responseType, headers);
  }

  @Override
  public <T> T post(String path, Object request, Type responseType)
      throws RepoException, ValidationException {
    try {
      checker.doCheck(
          ImmutableMap.of(
              "path", path,
              "request", request.toString(),
              "response_type", responseType.toString()),
          console);
    } catch (IOException e) {
      throw new RuntimeException("Error running checker", e);
    }
    return delegate.post(path, request, responseType);
  }

  @Override
  public void delete(String path) throws RepoException, ValidationException {
    try {
      checker.doCheck(ImmutableMap.of("path", path), console);
    } catch (IOException e) {
      throw new RuntimeException("Error running checker", e);
    }
    delegate.delete(path);
  }
}
