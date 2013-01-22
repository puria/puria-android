package si.nafi.puria.fragments;

import org.json.JSONArray;
import org.json.JSONException;

import si.nafi.R;
import si.nafi.puria.utils.H;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.puria_fragment)
public class PuriaFragment extends Fragment {

	String		twitterUrl	= "http://api.twitter.com/1/statuses/user_timeline/pna.json";
	@ViewById
	TextView	latest_tweet;

	@AfterViews
	@Background
	void getTweet() {
		try {
			JSONArray json = H.getJsonArrayFromUrl(twitterUrl);
			setTweet(json.getJSONObject(0).getString("text"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@UiThread
	void setTweet(String tweet) {
		String htmlTweet = H.parseTweet(tweet);
		latest_tweet.setText(Html.fromHtml(htmlTweet));
		latest_tweet.setMovementMethod(LinkMovementMethod.getInstance());
		latest_tweet.setLinkTextColor(
				new ColorStateList(
					new int[][] { new int[] { android.R.attr.state_selected }, new int[1] }, 
					new int[] { Color.rgb(255, 255, 255), Color.rgb(150, 80, 90)}
				)
		);
	}
}
