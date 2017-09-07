
package be.smalltownheroes.ketnet.karrewiet;

import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.comscore.Analytics;
import com.comscore.EventInfo;
import com.comscore.streaming.StreamingAnalytics;
import com.comscore.PublisherConfiguration;

public class RNComScoreModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;

	private StreamingAnalytics streamingAnalytics;
	private String appName;

	public RNComScoreModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
	}

	@Override
	public String getName() {
		return "RNComScore";
	}

	@ReactMethod
	public void init(ReadableMap options) {
		String comScoreAppName = options.getString("appName");
		String comScorePublisherId = options.getString("publisherId");
		String comScorePublisherSecret = options.getString("publisherSecret");
		String comScorePixelUrl = options.getString("pixelUrl");

		String streamSenseMediaPlayer = options.getString("streamSenseMediaPlayer");
		String streamSenseVersion = options.getString("streamSenseVersion");
		String streamSenseChannel = options.getString("streamSenseChannel");

		this.streamingAnalytics = new StreamingAnalytics();

		HashMap<String, String> labels = new HashMap<String, String>();
				labels.put("category", "app");
				labels.put("behoefte", "verbindend");
				labels.put("doelgroep", "none");
				labels.put("marketing", "none");
				labels.put("mediatype", "tv");
				labels.put("productiehuis", "Small Town Heroes");
				labels.put("waar", "app");

		PublisherConfiguration publisher = new PublisherConfiguration.Builder()
				.applicationName(comScoreAppName)
				.publisherId(comScorePublisherId)
				.publisherSecret(comScorePublisherSecret)
				.liveEndpointUrl(comScorePixelUrl)
				.persistentLabels(labels)
				.secureTransmission(true)
				.build();

		Analytics.getConfiguration().addClient(publisher);
		Analytics.start(this.reactContext);
		setAppName(comScoreAppName);
	}

	@ReactMethod
	public void trackView(String view) {
		String comScoreViewName = getAppName() + view;
		HashMap<String,String> labels = new HashMap<String,String>();
		labels.put("name", comScoreViewName.replace("/", "."));
		Analytics.notifyViewEvent(labels);
	}

	@ReactMethod
	public void trackEvent(String action, String category) {
		String comScoreEventName = category + "." + action;
		HashMap<String,String> labels = new HashMap<String,String>();
		labels.put("event", comScoreEventName);
		Analytics.notifyViewEvent(labels);
	}

	@ReactMethod
	public void trackVideoStreaming(ReadableMap videoInfo, String videoAction) {
		Log.i("React-native-comscore", "####### videoAction: " + videoAction);
		Log.i("React-native-comscore", "####### videoInfo: " + videoInfo);
		if (videoInfo != null) {
			int position = 0;
			if (!videoInfo.isNull("position")) {
				position = videoInfo.getInt("position");
			}
			HashMap<String, String> playbackLabels = this.getPlaybackLabels(videoInfo);
			// Not sure of how this works since no documentation:
			//  - is StreamAnaltics === StreamSense?
			// 	- do we have to create a playback session once on start and set the labels once?
			// 	- or set labels on every notify (like in the older version of comscore android)?
			// 	see https://github.com/amzn/fire-app-builder/blob/master/ComScoreAnalyticsComponent/src/main/java/com/amazon/analytics/comscore/ComScoreAnalytics.java#L348
			// 			[which suggests only add label/asset on start]
			// + Has the notion of "clip" disappeared and got swapped with "playback"-session?
			if (videoAction == "start") {
				this.streamingAnalytics.createPlaybackSession();
				this.streamingAnalytics.getPlaybackSession().setLabels(playbackLabels);
				Log.i("React-native-comscore", "================== notifyPlay: " + position);
				this.streamingAnalytics.notifyPlay(position);
			} else if (videoAction == "stop") {
				Log.i("React-native-comscore", "================== notifyEnd: " + position);
				this.streamingAnalytics.notifyEnd(position);
			} else if (videoAction == "pause") {
				Log.i("React-native-comscore", "================== notifyPause: " + position);
				this.streamingAnalytics.notifyPause(position);
			} else if (videoAction == "resume") {
				Log.i("React-native-comscore", "================== notifyPlay: " + position);
				this.streamingAnalytics.notifyPlay(position);
			}
		}
	}

	public HashMap<String, String> getPlaybackLabels(ReadableMap videoInfo) {
		HashMap<String, String> playbackLabels = new HashMap<String, String>();
		if (videoInfo.hasKey("length")) {
			playbackLabels.put("ns_st_cl", String.valueOf(videoInfo.getInt("length")));
			playbackLabels.put("ns_st_el", String.valueOf(videoInfo.getInt("length")));
		}
		if (videoInfo.hasKey("parts")) {
			playbackLabels.put("ns_st_tp", videoInfo.getString("parts"));
		}
		if (videoInfo.hasKey("whatson")) {
			Log.i("-whatson getString----", videoInfo.getString("whatson"));
			playbackLabels.put("ns_st_ci", videoInfo.getString("whatson"));
			playbackLabels.put("vrt_vid_id", videoInfo.getString("whatson"));
		}
		if (videoInfo.hasKey("program")) {
			playbackLabels.put("ns_st_pr", videoInfo.getString("program"));
		}
		if (videoInfo.hasKey("episode")) {
			playbackLabels.put("ns_st_ep", videoInfo.getString("episode"));
		}
		if (videoInfo.hasKey("type_stream")) {
			playbackLabels.put("ns_st_ty", videoInfo.getString("type_stream"));
		}
		if (videoInfo.hasKey("publication_date")) {
			String publicationDate = videoInfo.getString("publication_date");
			Date date = null;
			try {
				Log.i("-publicationDate ----", publicationDate);
				date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(publicationDate);
				playbackLabels.put("vrt_dat_id", (new SimpleDateFormat("EEE LLL MM yyyy HH:mm:ss ZZZZ zzz")).format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return playbackLabels;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
