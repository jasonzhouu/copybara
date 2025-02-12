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

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.copybara.checks.Checker;
import com.google.copybara.checks.CheckerException;
import com.google.copybara.exception.ValidationException;
import com.google.copybara.util.console.testing.TestingConsole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@RunWith(JUnit4.class)
public class GitHubApiTransportWithCheckerTest {

  private TestingConsole console;
  private GitHubApiTransport delegate;
  private Checker checker;

  private GitHubApiTransport transport;

  @Before
  public void setup() throws Exception {
    console = new TestingConsole();
    delegate = Mockito.mock(GitHubApiTransport.class);
    checker = Mockito.mock(Checker.class);
    transport = new GitHubApiTransportWithChecker(delegate, checker, console);
  }

  @Test
  public void testGet() throws Exception {
    transport.get("path/foo", String.class);
    verify(checker).doCheck(
        ImmutableMap.of("path", "path/foo", "response_type", "class java.lang.String"),
        console);
    verify(delegate).get(eq("path/foo"), eq(String.class), any(ImmutableListMultimap.class));
  }

  @Test
  public void testGetThrowsException() throws Exception {
    doThrow(new CheckerException("Error!"))
        .when(checker)
        .doCheck(ArgumentMatchers.<ImmutableMap<String, String>>any(), eq(console));
    try {
      transport.get("path/foo", String.class);
      fail();
    } catch (ValidationException expected) {
    }
    verifyZeroInteractions(delegate);
  }

  @Test
  public void testPost() throws Exception {
    transport.post("path/foo", "request_content", String.class);
    verify(checker).doCheck(
            ImmutableMap.of(
                "path", "path/foo",
                "request", "request_content",
                "response_type", "class java.lang.String"),
        console);
    verify(delegate).post(eq("path/foo"), eq("request_content"), eq(String.class));
  }

  @Test
  public void testPostThrowsException() throws Exception {
    doThrow(new CheckerException("Error!"))
        .when(checker)
        .doCheck(ArgumentMatchers.<ImmutableMap<String, String>>any(), eq(console));
    try {
      transport.post("path/foo", "request_content", String.class);
      fail();
    } catch (ValidationException expected) {
    }
    verifyZeroInteractions(delegate);
  }
}
