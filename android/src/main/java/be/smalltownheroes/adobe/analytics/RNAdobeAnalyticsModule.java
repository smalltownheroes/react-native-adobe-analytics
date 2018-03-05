
package be.smalltownheroes.adobe.analytics;

import java.util.Map;
import java.util.HashMap;

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
import com.adobe.mobile.MediaSettings;
import com.adobe.mobile.Media;
import com.adobe.mobile.MediaState;

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
		Log.i("RN-adobe-analytics", "####### trackState ####### " + state);
		Analytics.trackState(state, contextMap);
	}

	@ReactMethod
	public void trackAction(String action, ReadableMap contextData) {
		Map<String, Object> contextMap = convertReadableMapToHashMap(contextData);
		Log.i("RN-adobe-analytics", "####### trackAction ####### " + action);
		Analytics.trackAction(action, contextMap);
	}

	@ReactMethod
	public void trackVideo(String action, ReadableMap settings) {
		Log.i("RN-adobe-analytics", "####### trackVideo ####### " + action);
		switch (action) {
			case "open": {
				String name = settings.getString("name");
				Double length = settings.getDouble("length");
				String playerName = settings.getString("playerName");
				String playerId = settings.getString("playerId");
				final MediaSettings mediaSettings = Media.settingsWith(name, length, playerName, playerId);
				Media.open(mediaSettings);
				break;
			}
			case "close": {
				String name = settings.getString("name");
				Media.close(name);
				break;
			}
			case "play": {
				String name = settings.getString("name");
				Double offset = settings.getDouble("offset");
				Media.play(name, offset);
				break;
			}
			case "stop": {
				String name = settings.getString("name");
				Double offset = settings.getDouble("offset");
				Media.stop(name, offset);
				break;
			}
			case "complete": {
				String name = settings.getString("name");
				Double offset = settings.getDouble("offset");
				Media.complete(name, offset);
				break;
			}
			case "track": {
				String name = settings.getString("name");
				Map<String, Object> contextMap = convertReadableMapToHashMap(settings);
				Media.track(name, contextMap);
				break;
			}
			default: {
				Log.w("RN-adobe-analytics", "Unknown video track action:" + action);
				break;
			}
		}
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
