package mdx.edu.feedbacknative;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 * A transparent {@link Activity} displaying a "Stop" options menu to remove the {@link com.google.android.glass.timeline.LiveCard}.
 */
public class LiveCardMenuActivity extends Activity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Open the options menu right away.
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.live_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ClientResource clientResoure = null;
        Representation responseString = null;
        switch (item.getItemId()) {
            case R.id.action_stop:
                // Stop the service which will unpublish the live card.
                stopService(new Intent(this, LiveCardService.class));
                return true;
            case R.id.action_act:
//                clientResoure = new ClientResource("https://optical-mode-159320.appspot.com/act");
                clientResoure = new ClientResource("http://gsd.mdx.ac.uk:8080/google_glass_web/service/act");
                clientResoure.accept(MediaType.APPLICATION_JSON);
                responseString = clientResoure.get();
                return true;
            case R.id.action_reset:
//                clientResoure = new ClientResource("https://optical-mode-159320.appspot.com/resetDB");
                clientResoure = new ClientResource("http://gsd.mdx.ac.uk:8080/google_glass_web/service/resetDB");
                clientResoure.accept(MediaType.APPLICATION_JSON);
                responseString = clientResoure.get();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        // Nothing else to do, finish the Activity.
        finish();
    }
}
