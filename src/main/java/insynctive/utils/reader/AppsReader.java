package insynctive.utils.reader;

import insynctive.pages.insynctive.exception.ConfigurationException;
import insynctive.utils.data.App;

import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class AppsReader {

	JSONParser parser = new JSONParser();
	JSONArray jsonAppsList;

	public AppsReader(String installAppsJsonFile) throws ConfigurationException {
		try {
			jsonAppsList = (JSONArray) parser.parse(new FileReader(installAppsJsonFile));
		} catch (Exception ex) {
			throw new ConfigurationException("Fail in AppReader");
			// TODO THROW EXCEPTION
		}
	}

	public List<App> getApps() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		
		List<App> returnAppList = new ArrayList<App>();

		for (Object appObject : jsonAppsList) {
			/* CheckList */
			String appName = (String) appObject;
			App newApp = App.valueOf(appName);

			returnAppList.add(newApp);
		}
		return returnAppList;
	}

}
