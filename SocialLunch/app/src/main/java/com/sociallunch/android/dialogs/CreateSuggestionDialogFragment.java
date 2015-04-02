package com.sociallunch.android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sociallunch.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateSuggestionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateSuggestionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateSuggestionDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    // TODO: Rename and change types of parameters
    private Integer hour;
    private Integer minute;
    private Button buttonMeetingTime;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateSuggestionDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateSuggestionDialogFragment newInstance(String hour, String minute) {
        CreateSuggestionDialogFragment fragment = new CreateSuggestionDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOUR, hour);
        args.putString(ARG_MINUTE, minute);
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateSuggestionDialogFragment newInstance() {
        return new CreateSuggestionDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hour = getArguments().getInt(ARG_HOUR, -1);
            minute = getArguments().getInt(ARG_MINUTE, -1);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.dialog_fragment_create_suggestion, null);

        buttonMeetingTime = (Button) dialogView.findViewById(R.id.buttonMeetingTime);
        buttonMeetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (hour != null && hour != -1 && minute != null && minute != -1) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                }
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
                        hour = i;
                        minute = i2;
                        updateLabelForMeetingTime();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

                timePickerDialog.show(getChildFragmentManager(), TimePickerDialog.class.toString());
            }
        });

        builder.setTitle(getString(R.string.create_suggestion_title))
                .setView(dialogView)
                .setPositiveButton(R.string.label_suggest, null)
                .setNegativeButton(R.string.label_cancel, null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnSubmit = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        updateLabelForMeetingTime();

        return alertDialog;
    }

    public void updateLabelForMeetingTime() {
        if (hour != null && hour != -1 && minute != null && minute != -1) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            buttonMeetingTime.setText(String.format(getString(R.string.create_suggestion_label_meeting_time), sdf.format(calendar.getTime())));
        } else {
            buttonMeetingTime.setText(getString(R.string.create_suggestion_label_meeting_time_default));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CreateSuggestionDialogFragment.OnFragmentInteractionListener");
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
    }

}
