package org.brunocunha.taskerbox.impl.custom.hardmob;

import java.io.IOException;
import java.net.URISyntaxException;

import lombok.extern.log4j.Log4j;

import org.apache.http.client.ClientProtocolException;
import org.brunocunha.taskerbox.core.http.TaskerboxHttpBox;
import org.brunocunha.taskerbox.impl.email.EmailAction;
import org.brunocunha.taskerbox.impl.email.EmailValueVO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Log4j
public class HardmobEmailAction extends HardmobAction {

	
	@Override
	public void spreadAction(final String url, String postTitle) {
		EmailAction email = getEmailAction();
		
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		EmailValueVO emailVO = new EmailValueVO();
		emailVO.setTitle("Hardmob - " + postTitle);
		
		try {
			Document doc = TaskerboxHttpBox.getInstance().getDocumentForURL(url);
			
			for (Element post : doc.select(".postcontent")) {
				sb.append("<br>");
				sb.append(post.html());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		emailVO.setBody(sb.toString());
		email.action(emailVO);
		
	}



}
