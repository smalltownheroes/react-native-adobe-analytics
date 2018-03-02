
package be.smalltownheroes.adobe.analytics;

import java.util.Map;
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
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import com.adobe.mobile.Config;
import com.adobe.mobile.Analytics;

public class RNAdobeAnalyticsModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;

	public RNAdobeAnalyticsModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
	}

	@Override
	public String getName() {
		return "RNAdobeAnalytics";
	}

	@ReactMethod
	public void init(ReadableMap options) {
		Config.setContext(this.reactContext);
		Config.setDebugLogging(options.getBoolean("debug"));
	}

	@ReactMethod
	public void trackState(String state, ReadableMap contextData) {
		Map<String, Object> contextMap = convertReadableMapToHashMap(contextData);
		Analytics.trackState(state, contextMap);
	}

	@ReactMethod
	public void trackAction(String action, ReadableMap contextData) {
		Map<String, Object> contextMap = convertReadableMapToHashMap(contextData);
		Analytics.trackAction(action, contextMap);
	}

	@ReactMethod
	public void trackVideoStreaming(ReadableMap videoInfo, String videoAction) {
		// Log.i("React-native-comscore", "####### videoAction ####### " + videoAction);
		// Log.i("React-native-comscore", "####### videoInfo ####### " + videoInfo);
		// if (videoInfo != null) {
		// 	int position = 0;
		// 	if (videoInfo.hasKey("position")) {
		// 		position = videoInfo.getInt("position");
		// 	}
		// 	HashMap<String, String> playbackLabels = this.getPlaybackLabels(videoInfo);
		// 	// Not sure of how this works since no documentation:
		// 	//  - is StreamAnaltics === StreamSense?
		// 	// 	- do we have to create a playback session once on start and set the labels once?
		// 	// 	- or set labels on every notify (like in the older version of comscore android)?
		// 	// 	see https://github.com/amzn/fire-app-builder/blob/master/ComScoreAnalyticsComponent/src/main/java/com/amazon/analytics/comscore/ComScoreAnalytics.java#L348
		// 	// 			[which suggests only add label/asset on start]
		// 	// + Has the notion of "clip" disappeared and got swapped with "playback"-session?
		// 	if (videoAction.equals("start")) {
		// 		// Log.i("React-native-comscore", "####### notifyPlay ####### " + position);
		// 		this.streamingAnalytics.createPlaybackSession();
		// 		this.streamingAnalytics.getPlaybackSession().setLabels(playbackLabels);
		// 		this.streamingAnalytics.notifyPlay(position);
		// 	} else if (videoAction.equals("stop")) {
		// 		// Log.i("React-native-comscore", "####### notifyEnd ####### " + position);
		// 		this.streamingAnalytics.notifyEnd(position);
		// 	} else if (videoAction.equals("pause")) {
		// 		// Log.i("React-native-comscore", "####### notifyPause ####### " + position);
		// 		this.streamingAnalytics.notifyPause(position);
		// 	} else if (videoAction.equals("resume")) {
		// 		// Log.i("React-native-comscore", "####### notifyPlay ####### " + position);
		// 		this.streamingAnalytics.notifyPlay(position);
		// 	}
		// }
	}

	private Map<String, Object> convertReadableMapToHashMap(ReadableMap readableMap) {
		ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
		HashMap<String, Object> deconstructedMap = new HashMap<>();
		while (iterator.hasNextKey()) {
			String key = iterator.nextKey();
			ReadableType type = readableMap.getType(key);
			switch (type) {
				case Null:
					deconstructedMap.put(key, null);
					break;
				case Boolean:
					deconstructedMap.put(key, readableMap.getBoolean(key));
					break;
				case Number:
					deconstructedMap.put(key, readableMap.getDouble(key));
					break;
				case String:
					deconstructedMap.put(key, readableMap.getString(key));
					break;
				default:
					throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
			}

		}
		return deconstructedMap;
	}

}
