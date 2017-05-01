package edu.mdx.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class GoogleGlassesApplication extends Application {
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/getFeedback", GetFeedbackStatistics.class);
		router.attach("/register", RegisterStudentServerResource.class);
		router.attach("/updatePace", UpdatePaceServerResource.class);
		router.attach("/updateVolume", UpdateVolumeServerResource.class);
		router.attach("/updateBodyLanguage", UpdateBodyLanguageResource.class);
		router.attach("/updateClarity", UpdateClarityServerResource.class);
		router.attach("/updateInterest", UpdateInterestServerResource.class);
		router.attach("/getGlassData", GlassViewer.class);
		router.attach("/resetViewer", ResetViewer.class);
		router.attach("/resetDB", ResetViewer.class);
		router.attach("/clearLogs", ClearLogs.class);
		router.attach("/downloadLogs", DownloadLogs.class);
		router.attach("/downloadViewerSource", DownloadViewerSource.class);
		/*
		router.attach("/act", SetActoin.class);
		router.attach("/downloadActionLogs", DownloadActions.class);
		*/
		router.attach("/test", Test.class);
		return router;
	}
}
