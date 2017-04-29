package mdx.edu.feedbacknative;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.widget.RemoteViews;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

import mdx.edu.feedbacknative.shared.FeedbackUpdate;

/**
 * A {@link Service} that publishes a {@link LiveCard} in the timeline.
 */
public class LiveCardService extends Service {

    private static final String LIVE_CARD_TAG = "LiveCardService";

    private LiveCard mLiveCard;
    private RemoteViews mLiveCardView;
    private final UpdateLiveCardRunnable mUpdateLiveCardRunnable = new UpdateLiveCardRunnable();
    private static final long DELAY_MILLIS = 3000;
    private final Handler mHandler = new Handler();

    private static final int LS_VALUE = 0;//Index of Left Side value in the array
    private static final int RS_VALUE = 1;//Index of Right Side value in the array
    private static final int COUNT_VALUE = 2;//Index of count value in the array

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLiveCard == null) {
            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            mLiveCardView = new RemoteViews(getPackageName(), R.layout.live_card);
            mLiveCard.setViews(mLiveCardView);

            // Display the options menu when the live card is tapped.
            Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));

            mLiveCard.publish(PublishMode.REVEAL);
            mHandler.post(mUpdateLiveCardRunnable);
        } else {
            mLiveCard.navigate();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }

    private class UpdateLiveCardRunnable implements Runnable{

        private boolean mIsStopped  = false;
        @Override
        public void run() {
            if(!mIsStopped ){
                FeedbackUpdate feedbackUpdate = update();

                //set pace
                //--count
                mLiveCardView.setTextViewText(R.id.paceLabel, "Pace (" + String.valueOf(feedbackUpdate.getPace()[COUNT_VALUE] + ")"));
                //--green
                mLiveCardView.setTextViewText(R.id.paceGreenPercentTextView, String.valueOf(feedbackUpdate.getPace()[LS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.paceGreenBar, 100, feedbackUpdate.getPace()[LS_VALUE], false);
                //--red
                mLiveCardView.setTextViewText(R.id.paceRedPercentTextView, String.valueOf(feedbackUpdate.getPace()[RS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.paceRedBar, 100, feedbackUpdate.getPace()[RS_VALUE], false);
                //set volume
                //--count
                mLiveCardView.setTextViewText(R.id.volumeLabel, "Volume (" + String.valueOf(feedbackUpdate.getVolume()[COUNT_VALUE] + ")"));
                //--green
                mLiveCardView.setTextViewText(R.id.volumeGreenPercentTextView, String.valueOf(feedbackUpdate.getVolume()[LS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.volumeGreenBar, 100, feedbackUpdate.getVolume()[LS_VALUE], false);
                //--red
                mLiveCardView.setTextViewText(R.id.volumeRedPercentTextView, String.valueOf(feedbackUpdate.getVolume()[RS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.volumeRedBar, 100, feedbackUpdate.getVolume()[RS_VALUE], false);

                //set body language
                //--count
                mLiveCardView.setTextViewText(R.id.bodyLanguageLabel, "Body Language (" + String.valueOf(feedbackUpdate.getBodyLanguage()[COUNT_VALUE]) + ")");
                //--green
                mLiveCardView.setTextViewText(R.id.bodyLanguageGreenPercentTextView, String.valueOf(feedbackUpdate.getBodyLanguage()[LS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.bodyLanguageGreenBar, 100, feedbackUpdate.getBodyLanguage()[LS_VALUE], false);
                //--lef
                mLiveCardView.setTextViewText(R.id.bodyLanguageRedPercentTextView, String.valueOf(feedbackUpdate.getBodyLanguage()[RS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.bodyLanguageRedBar, 100, feedbackUpdate.getBodyLanguage()[RS_VALUE], false);

                //set Clarity
                //--count
                mLiveCardView.setTextViewText(R.id.clarityLabel, "Clarity (" + String.valueOf(feedbackUpdate.getClarity()[COUNT_VALUE]) + ")");
                //--green
                mLiveCardView.setTextViewText(R.id.clarityGreenPercentTextView, String.valueOf(feedbackUpdate.getClarity()[LS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.clarityGreenBar, 100, feedbackUpdate.getClarity()[LS_VALUE], false);
                //--lef
                mLiveCardView.setTextViewText(R.id.clarityRedPercentTextView, String.valueOf(feedbackUpdate.getClarity()[RS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.clarityRedBar, 100, feedbackUpdate.getClarity()[RS_VALUE], false);

                //set Interesting Topic
                //--count
                mLiveCardView.setTextViewText(R.id.interestLabel, "interest (" + String.valueOf(feedbackUpdate.getInterest()[COUNT_VALUE]) + ")");
                //--green
                mLiveCardView.setTextViewText(R.id.interestGreenPercentTextView, String.valueOf(feedbackUpdate.getInterest()[LS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.interestGreenBar, 100, feedbackUpdate.getInterest()[LS_VALUE], false);
                //--lef
                mLiveCardView.setTextViewText(R.id.interestRedPercentTextView, String.valueOf(feedbackUpdate.getInterest()[RS_VALUE])+"%");
                mLiveCardView.setProgressBar(R.id.interestRedBar, 100, feedbackUpdate.getInterest()[RS_VALUE], false);

                mLiveCard.setViews(mLiveCardView);
                mHandler.postDelayed(mUpdateLiveCardRunnable, DELAY_MILLIS);
            }
        }

        public boolean isStopped(){
            return mIsStopped ;
        }

        public void setStopped(boolean isStopped){
            this.mIsStopped = isStopped;
        }

        private FeedbackUpdate update(){
//            ClientResource clientResoure = new ClientResource("https://optical-mode-159320.appspot.com/getGlassData");
            ClientResource clientResoure = new ClientResource("http://gsd.mdx.ac.uk:8080/google_glass_web/service/getGlassData");
            clientResoure.accept(MediaType.APPLICATION_JSON);
            Representation responseString = clientResoure.get();
            String jsonString = "";
            try {
                jsonString = responseString.getText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            FeedbackUpdate feedbackUpdate = null;

            try {
                feedbackUpdate = mapper.readValue(jsonString, FeedbackUpdate.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return feedbackUpdate;
        }
    }
}

