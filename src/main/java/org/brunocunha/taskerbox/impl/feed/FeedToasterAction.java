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
package org.brunocunha.taskerbox.impl.feed;

import java.awt.Desktop;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.log4j.Log4j;

import org.brunocunha.taskerbox.core.DefaultTaskerboxAction;
import org.brunocunha.taskerbox.core.TaskerboxConstants;
import org.brunocunha.taskerbox.core.utils.TaskerboxTrayUtils;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Action that shows Feeds in a Toaster Popup
 * 
 * @author Bruno Candido Volpato da Cunha
 * 
 */
@Log4j
public class FeedToasterAction extends DefaultTaskerboxAction<SyndEntryWrapper> {

	@Override
	public void action(final SyndEntryWrapper entry) {
		TaskerboxTrayUtils.displayMessage(TaskerboxConstants.TITLE, entry.getValue().getTitle(), MessageType.INFO,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						logInfo(log, "Action -> Open: " + entry.getValue().getUri());

						if (Desktop.isDesktopSupported()) {
							try {
								Desktop.getDesktop().browse(new URI(entry.getValue().getUri()));
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
	}

}
