package com.sociallunch.android.workers;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sociallunch.android.R;
import com.sociallunch.android.models.Venue;
import com.sociallunch.android.models.YelpSearchAPIResponse;
import com.sociallunch.android.net.YelpAPI;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VenueSelectionWorkerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VenueSelectionWorkerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueSelectionWorkerFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    protected FetchYelpSearchResultsTask fetchYelpSearchResultsTask;
    public String mSubmittedQuery;
    public String mSize;
    public String mColor;
    public String mType;
    public String mSite;
    public ArrayList<Venue> mSearchResults = new ArrayList<>();

    public static VenueSelectionWorkerFragment newInstance() {
        return new VenueSelectionWorkerFragment();
    }

    public VenueSelectionWorkerFragment() {
        mSearchResults = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        cancelAsyncTasks();

        super.onDestroy();
    }

    public void cancelAsyncTasks() {
        cancelFetchYelpSearchResultsTask();
    }

    public void cancelFetchYelpSearchResultsTask() {
        if (fetchYelpSearchResultsTask != null &&
                !fetchYelpSearchResultsTask.isCancelled()) {
            fetchYelpSearchResultsTask.cancel(true);
        }
    }

    public void fetchYelpSearchResultsAsync(String term, String location) {
        if (fetchYelpSearchResultsTask != null)
            return;

        fetchYelpSearchResultsTask = new FetchYelpSearchResultsTask();
        Object[] params = {
                term,
                location
        };
        fetchYelpSearchResultsTask.execute(params);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement VenueSelectionWorkerFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFetchYelpSearchResultsTaskPreExecute();

        void onFetchYelpSearchResultsTaskCancelled();

        void onFetchYelpSearchResultsTaskPostExecute(YelpSearchAPIResponse result);
    }

    public class FetchYelpSearchResultsTask extends AsyncTask<Object, Void, YelpSearchAPIResponse> {
        @Override
        public void onPreExecute() {
            if (mListener != null) mListener.onFetchYelpSearchResultsTaskPreExecute();
        }

        @Override
        protected YelpSearchAPIResponse doInBackground(Object... params) {
            if (params != null && params.length == 2) {
                String term = (String) params[0];
                String location = (String) params[1];
                YelpAPI yelpAPI = new YelpAPI(getString(R.string.yelp_consumer_key), getString(R.string.yelp_consumer_secret), getString(R.string.yelp_token), getString(R.string.yelp_token_secret));
                return new YelpSearchAPIResponse(yelpAPI.searchForBusinessesByLocation(term, location));
            }
            return null;
        }

        @Override
        protected void onPostExecute(YelpSearchAPIResponse result) {
            if (mListener != null) mListener.onFetchYelpSearchResultsTaskPostExecute(result);
            fetchYelpSearchResultsTask = null;
        }

        @Override
        protected void onCancelled() {
            if (mListener != null) mListener.onFetchYelpSearchResultsTaskCancelled();
            fetchYelpSearchResultsTask = null;
        }
    }
}
