package si.nafi.puria.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import si.nafi.R;
import si.nafi.puria.utils.H;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

@EFragment(R.layout.music_fragment)
public class MusicFragment extends Fragment {
	String[]			imageUrls = {"", "", "", "", "", "","", "", "",};
	DisplayImageOptions	options;
	@ViewById GridView grid_view;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	JSONArray tracks;
	int limit = 9;


	@AfterInject
	@Background
	void getMusic() {
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		
		String lastfmUrl = "http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=puria&api_key=" + H.LASTFM_API_KEY + "&limit=" + limit + "&format=json";
		JSONObject json = H.getJsonObjectFromUrl(lastfmUrl);

		try {
			tracks = json.getJSONObject("recenttracks").getJSONArray("track");
			int i = limit - 1;
			do {
				imageUrls[i] = tracks.getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text");
				H.d(i + "");
			} while (i-- > 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		attachAdapter();
	}
	
	@UiThread
	void attachAdapter() {
		grid_view.setAdapter(new ImageAdapter());
		grid_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tracks.getJSONObject(position).getString("url")));
					getActivity().startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		grid_view.setOnScrollListener(new PauseOnScrollListener(true, true));		
	}
	
	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater(getArguments()).inflate(R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage(imageUrls[position], imageView, options);

			return imageView;
		}
	}
}
