package com.sociallunch.android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sociallunch.android.R;
import com.sociallunch.android.activities.VenueSelectionActivity;
import com.sociallunch.android.models.Venue;

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
    public static final int REQUEST_CODE_VENUE_SELECTION = 1001;

    private Venue venue;
    private Calendar meetingTime;
    private Button buttonVenue;
    private Button buttonMeetingTime;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateSuggestionDialogFragment.
     */
    public static CreateSuggestionDialogFragment newInstance(String hour, String minute) {
        return new CreateSuggestionDialogFragment();
    }

    public static CreateSuggestionDialogFragment newInstance() {
        return new CreateSuggestionDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CreateSuggestionDialogFragment.REQUEST_CODE_VENUE_SELECTION &&
                resultCode == Activity.RESULT_OK) {
            venue = (Venue) data.getParcelableExtra(VenueSelectionActivity.RESULT_SELECTED_VENUE);
            updateLabelForVenue();
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

        buttonVenue = (Button) dialogView.findViewById(R.id.buttonVenue);
        buttonVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), VenueSelectionActivity.class);
                startActivityForResult(i, REQUEST_CODE_VENUE_SELECTION);
            }
        });

        buttonMeetingTime = (Button) dialogView.findViewById(R.id.buttonMeetingTime);
        buttonMeetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar meetingTime = CreateSuggestionDialogFragment.this.meetingTime != null ? CreateSuggestionDialogFragment.this.meetingTime : Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
                        Calendar meetingTime = Calendar.getInstance();
                        meetingTime.set(Calendar.HOUR_OF_DAY, i);
                        meetingTime.set(Calendar.MINUTE, i2);
                        meetingTime.set(Calendar.SECOND, 0);
                        meetingTime.set(Calendar.MILLISECOND, 0);
                        CreateSuggestionDialogFragment.this.meetingTime = meetingTime;
                        updateLabelForMeetingTime();
                    }
                }, meetingTime.get(Calendar.HOUR_OF_DAY), meetingTime.get(Calendar.MINUTE), false, false);

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
                        if (venue == null) {
                            Toast.makeText(getActivity(), getString(R.string.create_suggestion_toast_venue_required), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (meetingTime == null) {
                            Toast.makeText(getActivity(), getString(R.string.create_suggestion_toast_meeting_time_required), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mListener != null) {
                            mListener.requestToCreateSuggestion(venue, meetingTime);
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });

        updateLabelForMeetingTime();
        updateLabelForVenue();

        return alertDialog;
    }

    public void updateLabelForMeetingTime() {
        if (meetingTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            buttonMeetingTime.setText(String.format(getString(R.string.create_suggestion_label_meeting_time), sdf.format(meetingTime.getTime())));
        } else {
            buttonMeetingTime.setText(getString(R.string.create_suggestion_label_meeting_time_default));
        }
    }

    public void updateLabelForVenue() {
        if (venue != null) {
            buttonVenue.setText(venue.name);
        } else {
            buttonVenue.setText(getString(R.string.create_suggestion_label_venue_default));
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
        public void requestToCreateSuggestion(Venue venue, Calendar meetingTime);
    }

}
