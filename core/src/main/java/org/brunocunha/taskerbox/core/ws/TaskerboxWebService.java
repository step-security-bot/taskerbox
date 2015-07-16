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
package org.brunocunha.taskerbox.core.ws;

import lombok.Getter;
import lombok.Setter;

import org.brunocunha.taskerbox.impl.soap.SOAPChannel;


/**
 * POJO that ties the WebService to Channel
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 * @param <T>
 */
public class TaskerboxWebService<T> {

  @Getter
  @Setter
  private SOAPChannel<T> channel;

}
