package com.sociallunch.android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sociallunch.android.R;
import com.sociallunch.android.models.Filter;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kelei on 4/5/15.
 */
public class FilterSuggestionDialogFragment extends DialogFragment {
    @InjectView(R.id.btnEarliestMeetingTime) Button btnEarliestMeetingTime;
    @InjectView(R.id.btnLatestMeetingTime) Button btnLatestMeetingTime;

    private Calendar earliestMeetingTime;
    private Calendar latestMeetingTime;

    private OnFragmentInteractionListener mListener;


    public static FilterSuggestionDialogFragment newInstance(Filter filter) {
        FilterSuggestionDialogFragment fragment = new FilterSuggestionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("filter", filter);
        fragment.setArguments(args);
        return fragment;
    }

    public static FilterSuggestionDialogFragment newInstance() {
        return new FilterSuggestionDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.dialog_fragment_filter_suggestion, null);
        ButterKnife.inject(this, dialogView);

        Filter filter = (Filter) getArguments().getSerializable("filter");
        if (filter != null) {
            earliestMeetingTime = filter.getEarliestMeetingTime();
            latestMeetingTime = filter.getLatestMeetingTime();
        }
        btnEarliestMeetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar meetingTime = FilterSuggestionDialogFragment.this.earliestMeetingTime != null ?
                    FilterSuggestionDialogFragment.this.earliestMeetingTime : Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
                        Calendar meetingTime = Calendar.getInstance();
                        meetingTime.set(Calendar.HOUR_OF_DAY, i);
                        meetingTime.set(Calendar.MINUTE, i2);
                        meetingTime.set(Calendar.SECOND, 0);
                        meetingTime.set(Calendar.MILLISECOND, 0);
                        FilterSuggestionDialogFragment.this.earliestMeetingTime = meetingTime;
                        updateLabelForEarliestTime();
                    }
                }, meetingTime.get(Calendar.HOUR_OF_DAY), meetingTime.get(Calendar.MINUTE), false, false);

                timePickerDialog.show(getChildFragmentManager(), TimePickerDialog.class.toString());
            }
        });

        btnLatestMeetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar meetingTime = FilterSuggestionDialogFragment.this.latestMeetingTime != null ?
                    FilterSuggestionDialogFragment.this.latestMeetingTime : Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
                        Calendar meetingTime = Calendar.getInstance();
                        meetingTime.set(Calendar.HOUR_OF_DAY, i);
                        meetingTime.set(Calendar.MINUTE, i2);
                        meetingTime.set(Calendar.SECOND, 0);
                        meetingTime.set(Calendar.MILLISECOND, 0);
                        FilterSuggestionDialogFragment.this.latestMeetingTime = meetingTime;
                        updateLabelForLatestTime();
                    }
                }, meetingTime.get(Calendar.HOUR_OF_DAY), meetingTime.get(Calendar.MINUTE), false, false);

                timePickerDialog.show(getChildFragmentManager(), TimePickerDialog.class.toString());
            }
        });

        builder.setTitle(getString(R.string.filter_suggestions))
            .setView(dialogView)
            .setPositiveButton(R.string.label_okay, null)
            .setNegativeButton(R.string.label_cancel, null);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnSubmit = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (earliestMeetingTime != null && latestMeetingTime != null &&
                            earliestMeetingTime.compareTo(latestMeetingTime) > 0) {
                            Toast.makeText(getActivity(),
                                "Earliest time cannot be later than latest time",
                                Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mListener != null) {
                            mListener.filterSuggestions(earliestMeetingTime, latestMeetingTime);
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });

        updateLabelForEarliestTime();
        updateLabelForLatestTime();

        return alertDialog;
    }

    public void updateLabelForEarliestTime() {
        if (earliestMeetingTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            btnEarliestMeetingTime.setText(String.format(
                getString(R.string.filter_suggestion_earliest_time_filled), sdf.format(earliestMeetingTime.getTime())));
        } else {
            btnEarliestMeetingTime.setText(getString(R.string.filter_suggestion_earliest_time));
        }
    }

    public void updateLabelForLatestTime() {
        if (latestMeetingTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            btnLatestMeetingTime.setText(String.format(getString(
                R.string.filter_suggestion_latest_time_filled), sdf.format(latestMeetingTime.getTime())));
        } else {
            btnLatestMeetingTime.setText(getString(R.string.filter_suggestion_latest_time));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement FilterSuggestionDialogFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void filterSuggestions(Calendar earliestMeetingTime, Calendar latestMeetingTime);
    }
}
