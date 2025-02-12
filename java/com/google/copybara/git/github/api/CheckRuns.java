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

package com.google.copybara.git.github.api;

import com.google.api.client.util.Key;
import com.google.common.base.MoreObjects;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;
import com.google.devtools.build.lib.skylarkinterface.SkylarkValue;
import java.util.List;

/**
 * Represents the response of list check runs for a specific ref.
 * https://developer.github.com/v3/checks/runs/#list-check-runs-for-a-specific-ref
 */
@SkylarkModule(
    name = "github_check_runs_obj",
    category = SkylarkModuleCategory.BUILTIN,
    doc = "List check runs for a specific ref "
        + "https://developer.github.com/v3/checks/runs/#list-check-runs-for-a-specific-ref")
public class CheckRuns implements SkylarkValue {

  @Key("total_count")
  private int totalCount;

  @Key("check_runs")
  private List<CheckRun> checkRuns;

  @SkylarkCallable(
      name = "total_count",
      doc = "The total count of check runs.",
      structField = true
  )
  public int getTotalCount() {
    return totalCount;
  }

  @SkylarkCallable(
      name = "check_runs",
      doc = "The list of the detail for each check run.",
      structField = true
  )
  public List<CheckRun> getCheckRuns() {
    return checkRuns;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("total_count", totalCount)
        .add("check_runs", checkRuns)
        .toString();
  }

}
