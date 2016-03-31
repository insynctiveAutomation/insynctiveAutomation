package insynctive.utils.airbrake;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import airbrake.AirbrakeNotice;
import airbrake.NoticeXml;

public class AirbrakeUtil {

	private void err(final AirbrakeNotice notice, final Exception e) {
		e.printStackTrace();
	} 

	public String notify(final AirbrakeNotice notice) {
		try {
			HttpPost httpPost = new HttpPost("http://api.airbrake.io/notifier_api/v2/notices");
			httpPost.addHeader("Content-type", "text/xml");
			httpPost.addHeader("Accept", "text/xml, application/xml");
			
			String dataToPost = new NoticeXml(notice).toString();
			StringEntity entity = new StringEntity(dataToPost, "UTF-8");
			
			httpPost.setEntity(entity);
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(httpPost);
			String responseJsonEntity = EntityUtils.toString(response.getEntity());
			
			return getTag(responseJsonEntity, "url");
		} catch (final Exception e) {
			err(notice, e);
		}
		return null;
	}
	
	public String getTag(String xml, String name) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xml));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName(name);
	        
	        Node child = nodes.item(0).getFirstChild();
	        return child.getTextContent();
	}
	
}
