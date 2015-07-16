/**
 * Copyright (C) 2015 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocunha.taskerbox.impl.http;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.brunocunha.taskerbox.core.TaskerboxChannel;
import org.brunocunha.taskerbox.core.annotation.TaskerboxField;
import org.brunocunha.taskerbox.core.http.TaskerboxHttpBox;
import org.hibernate.validator.constraints.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.syndication.io.FeedException;

/**
 * HTTP HTML Input Channel
 * 
 * @author Bruno Candido Volpato da Cunha
 * 
 */
@Log4j
public class HTTPHTMLChannel extends TaskerboxChannel<Document> {

  @URL
  @TaskerboxField("URL")
  @Getter
  @Setter
  private String url;

  @TaskerboxField("Filter")
  @Getter
  @Setter
  private String filter;

  @Getter
  @Setter
  private boolean unique;

  @Override
  protected void execute() throws IOException, IllegalArgumentException, FeedException {
    log.debug("Checking #" + checkCount + "... [" + url + " / '" + filter + "']");

    try {
      HttpResponse response = TaskerboxHttpBox.getInstance().getResponseForURLNewClient(url);

      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != HttpStatus.SC_OK) {
        logWarn(log, "Error while fetching " + url + " - " + response.getStatusLine());
        return;
      }

      String responseBody =
          TaskerboxHttpBox.getInstance().readResponseFromEntity(response.getEntity());
      log.debug("Got Response: " + responseBody);
      if (filter != null && !filter.equals("")) {
        if (!responseBody.contains(filter)) {
          log.debug("Response filtered out!");
          return;
        }
      }

      Document doc = Jsoup.parse(responseBody);
      if (this.unique) {
        performUnique(doc);
      } else {
        perform(doc);
      }
    } catch (Exception e) {
      logError(log, "Error executing " + getId(), e);
      e.printStackTrace();
    }

  }

  @Override
  protected String getItemFingerprint(Document entry) {
    return entry.data();
  }

  @Override
  public String getDisplayName() {
    StringBuffer sb = new StringBuffer();
    sb.append(this.getId());
    if (this.getUrl() != null && !this.getUrl().equals("")) {
      sb.append(" (").append(this.getUrl()).append(")");
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return "HTTPHTMLChannel [url=" + url + ", filter=" + filter + ", unique=" + unique + ", every="
        + getEvery() + "]";
  }


}
