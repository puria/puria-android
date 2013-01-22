package si.nafi.puria.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.twitter.Autolink;

public class H {
	public static final String LASTFM_API_KEY = "717d0649632b18712be3c8604455d536";
	public static final String GOOGLE_API_KEY = "AIzaSyAHCFr-k_1weFeh4LcYhFs0E2IPM-77BQc";

	public static void d(String msg) {
		Log.d("PURIA", msg);
	}
	
	
	public static String parseTweet(String tweetText) {
		Autolink autolink = new Autolink();
		return autolink.autoLink(tweetText);
	}
	
	public static JSONObject getJsonObjectFromUrl(String url) {
		try {
			return new JSONObject(H.jsonCall(url));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JSONArray getJsonArrayFromUrl(String url) {
		try {
			return new JSONArray(H.jsonCall(url));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String jsonCall(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		
		try {
			HttpResponse response = client.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("", "Failed to download file");
			}
			
			return builder.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
