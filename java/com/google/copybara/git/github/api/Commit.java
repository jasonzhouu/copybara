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

import com.google.api.client.util.Key;
import com.google.common.base.MoreObjects;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;
import com.google.devtools.build.lib.skylarkinterface.SkylarkValue;
import java.time.ZonedDateTime;

/** Represents the current status of a ref, as returned by the git/refs API call */
@SkylarkModule(
    name = "github_api_commit_obj",
    category = SkylarkModuleCategory.BUILTIN,
    doc =
        "Commit field for GitHub commit information"
            + " https://developer.github.com/v3/git/commits/#get-a-commit."
            + " This is a subset of the available fields in GitHub")
public class Commit implements SkylarkValue {
  @Key private String message;
  @Key private CommitAuthor author;
  @Key private CommitAuthor committer;

  @SkylarkCallable(name = "message", doc = "Message of the commit", structField = true)
  public String getMessage() {
    return message;
  }

  @SkylarkCallable(name = "author", doc = "Author of the commit", structField = true)
  public CommitAuthor getAuthor() {
    return author;
  }

  @SkylarkCallable(name = "committer", doc = "Committer of the commit", structField = true)
  public CommitAuthor getCommitter() {
    return committer;
  }

  @SkylarkModule(
      name = "github_api_commit_author_obj",
      category = SkylarkModuleCategory.BUILTIN,
      doc =
          "Author/Committer for commit field for GitHub commit information"
              + " https://developer.github.com/v3/git/commits/#get-a-commit."
              + " This is a subset of the available fields in GitHub")
  public static class CommitAuthor implements SkylarkValue {
    @Key String name;
    @Key String email;
    @Key String date;

    public ZonedDateTime getDate() {
      return ZonedDateTime.parse(date);
    }

    @SkylarkCallable(name = "date", doc = "Date of the commit", structField = true)
    public String getDateForSkylark() {
      return date;
    }

    @SkylarkCallable(name = "name", doc = "Name of the author/committer", structField = true)
    public String getName() {
      return name;
    }

    @SkylarkCallable(name = "email", doc = "Email of the author/committer", structField = true)
    public String getEmail() {
      return email;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("name", name)
          .add("email", email)
          .add("date", date)
          .toString();
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("message", message)
        .add("author", author)
        .add("committer", committer)
        .toString();
  }
}
