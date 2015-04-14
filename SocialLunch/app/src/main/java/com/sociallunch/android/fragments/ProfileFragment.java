package com.sociallunch.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.sociallunch.android.R;
import com.sociallunch.android.activities.HowItWorksActivity;
import com.sociallunch.android.activities.SignupActivity;
import com.sociallunch.android.models.User;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    @InjectView(R.id.ivProfileImage) ProfilePictureView ivProfileImage;
    @InjectView(R.id.tvName) TextView tvName;
    @InjectView(R.id.tvLikes) TextView tvLikes;
    @InjectView(R.id.tvDislikes) TextView tvDislikes;
    @InjectView(R.id.btnLogout) Button btnLogout;

    private OnFragmentInteractionListener mListener;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        User user = getArguments().getParcelable("user");
        tvName.setText(user.getFullName());
        tvLikes.setText(user.getFoodsLiked());
        tvDislikes.setText(user.getFoodsDisliked());
        ivProfileImage.setProfileId(user.getUid().replace("facebook:", ""));
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                if(sp != null && sp.getBoolean(HowItWorksActivity.PREF_HOW_IT_WORKS_SHOWN, true)) {
                    sp.edit().putBoolean(HowItWorksActivity.PREF_HOW_IT_WORKS_SHOWN, false).apply();
                }
                LoginManager.getInstance().logOut();
                Intent i = new Intent(getActivity(), SignupActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            if (mListener != null) {
                mListener.onProfileFragmentAttached();
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ProfileFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onProfileFragmentAttached();
    }

}
