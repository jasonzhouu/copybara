/*
 * Copyright (C) 2017 Google Inc.
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

/** A user object returned as a field by many GitHub API responses. */
@SkylarkModule(
    name = "github_api_user_obj",
    category = SkylarkModuleCategory.BUILTIN,
    doc = "An object representing a GitHub user")
public class User implements SkylarkValue {

  @Key
  private String login;
  @Key
  private long id;
  @Key
  private String type;
  @Key("site_admin")
  private boolean siteAdmin;

  @SkylarkCallable(name = "login", doc = "Login of the user", structField = true)
  public String getLogin() {
    return login;
  }

  public long getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public boolean isSiteAdmin() {
    return siteAdmin;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("login", login)
        .add("id", id)
        .add("type", type)
        .add("siteAdmin", siteAdmin)
        .toString();
  }
}
