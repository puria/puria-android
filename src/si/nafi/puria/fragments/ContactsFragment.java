package si.nafi.puria.fragments;

import si.nafi.R;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;

@EFragment(R.layout.contacts_fragment)
public class ContactsFragment extends Fragment {

	@Click
	void call() {
		Intent intent = new Intent(Intent.ACTION_CALL);

		intent.setData(Uri.parse("tel:+39 393 926 7295"));
		getActivity().startActivity(intent);
	}

}
