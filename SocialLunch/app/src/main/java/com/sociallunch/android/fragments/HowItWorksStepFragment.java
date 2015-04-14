package com.sociallunch.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sociallunch.android.R;

public class HowItWorksStepFragment extends Fragment {
    private static final String ARG_IMAGE_RES_ID_ILLUSTRATION = "arg.IMAGE_RES_ID_ILLUSTRATION";
    private static final String ARG_STRING_RES_ID_TITLE = "arg.STRING_RES_ID_TITLE";
    private static final String ARG_STRING_RES_ID_DESC = "arg.STRING_RES_ID_DESC";
    private ImageView mImageViewIllustration;
    private TextView mTextViewTitle;
    private TextView mTextViewDesc;

    public HowItWorksStepFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HowItWorksStepFragment newInstance(int imageResIdIllustration, int stringResIdTitle, int stringResIdDesc) {
        HowItWorksStepFragment fragment = new HowItWorksStepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID_ILLUSTRATION, imageResIdIllustration);
        args.putInt(ARG_STRING_RES_ID_TITLE, stringResIdTitle);
        args.putInt(ARG_STRING_RES_ID_DESC, stringResIdDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_how_it_works_step, container, false);
        mImageViewIllustration = (ImageView) rootView.findViewById(R.id.how_it_works_step__imageview_illustration);
        mTextViewTitle = (TextView) rootView.findViewById(R.id.how_it_works_step__textview_title);
        mTextViewDesc = (TextView) rootView.findViewById(R.id.how_it_works_step__textview_desc);
        if (getArguments() != null) {
            int imageResIdIllustration = getArguments().getInt(ARG_IMAGE_RES_ID_ILLUSTRATION, 0);
            if (imageResIdIllustration != 0) {
                mImageViewIllustration.setImageDrawable(getResources().getDrawable(imageResIdIllustration));
            }
            int stringResIdTitle = getArguments().getInt(ARG_STRING_RES_ID_TITLE, 0);
            if (stringResIdTitle != 0) {
                mTextViewTitle.setText(Html.fromHtml(getString(stringResIdTitle)));
            }
            int stringResIdDesc = getArguments().getInt(ARG_STRING_RES_ID_DESC, 0);
            if (stringResIdDesc != 0) {
                mTextViewDesc.setText(Html.fromHtml(getString(stringResIdDesc)));
            }
        }
        return rootView;
    }
}
