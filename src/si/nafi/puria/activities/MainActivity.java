package si.nafi.puria.activities;

import si.nafi.R;
import si.nafi.puria.fragments.ContactsFragment_;
import si.nafi.puria.fragments.MusicFragment_;
import si.nafi.puria.fragments.PuriaFragment_;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

@NoTitle
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {
	SectionsPagerAdapter	mSectionsPagerAdapter= new SectionsPagerAdapter(getSupportFragmentManager());
	@ViewById ViewPager pager;
	
	@AfterViews
	void on_create() {
		pager.setAdapter(mSectionsPagerAdapter);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(2 * 1024 * 1024)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.enableLogging()
		.build();
		ImageLoader.getInstance().init(config);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new PuriaFragment_();
			case 1:
				return new ContactsFragment_();
			case 2:
				return new MusicFragment_();
			default:
				break;
			}
			
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "HOME";
			case 1:
				return "CONTACTS";
			case 2:
				return "MUSIC";
			}
			return null;
		}
	}
}
