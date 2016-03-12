package data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class CollectData {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
	JSONObject json = readJsonFromUrl(" http://www.carqueryapi.com/api/0.3/?cmd=getYears");
	JSONObject years = json.getJSONObject("Years");
	
	Integer minYear = Integer.parseInt((String)years.get("min_year"));
	Integer maxYear = Integer.parseInt((String)years.get("max_year"));
	for(int i = minYear; i <= maxYear; i++){
		json = readJsonFromUrl("http://www.carqueryapi.com/api/0.3/?cmd=getMakes&year=" + i);
		System.out.println("Year :: " + i +" :: json :: " + json.toString());
	}
  }
}

