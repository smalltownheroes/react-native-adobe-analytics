
package be.smalltownheroes.ketnet.karrewiet;

import java.util.HashMap;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.comscore.Analytics;
import com.comscore.EventInfo;
import com.comscore.PublisherConfiguration;

public class RNComScoreModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

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
    this.comScoreAppName = options.getString("appName");
    String comScorePublisherSecret = options.getString("publisherSecret");
    String comScorePixelUrl = options.getString("pixelUrl");

    HashMap<String, String> labels = new HashMap<String, String>();
        labels.put("category", "app");
        labels.put("behoefte", "verbindend");
        labels.put("doelgroep", "none");
        labels.put("marketing", "none");
        labels.put("mediatype", "tv");
        labels.put("productiehuis", "Small Town Heroes");
        labels.put("waar", "app");

    PublisherConfiguration publisher = new PublisherConfiguration.Builder()
        .applicationName(this.comScoreAppName)
        .publisherSecret(comScorePublisherSecret)
        .pixelUrl(comScorePixelUrl)
        .persistentLabels(labels)
        .build();

    Analytics.getConfiguration().addClient(publisher);
    Analytics.start(getApplicationContext());
  }

  @ReactMethod
  public void trackView(String view) {
    String comScoreViewName = this.comScoreAppName + viewName;
  	EventInfo eventInfo = new EventInfo();
    eventInfo.setLabel("view", comScoreViewName.replace("/", "."));
    Analytics.notifyViewEvent(eventInfo);
  }

  @ReactMethod
  public void trackEvent(String action, String category) {
  	EventInfo eventInfo = new EventInfo();
    eventInfo.setLabel("event", event);
    Analytics.notifyViewEvent(eventInfo);
  }

  @Override
  public void onResume () {
    Analytics.notifyEnterForeground();
  }

  @Override
  public void onPause () {
    Analytics.notifyExitForeground();
  }

}
