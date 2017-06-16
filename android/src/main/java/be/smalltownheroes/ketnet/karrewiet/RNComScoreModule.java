
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
    String comScorePublisherId = options.getString("publisherid");
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
  	EventInfo eventInfo = new EventInfo();
    eventInfo.setLabel("view", comScoreViewName.replace("/", "."));
    Analytics.notifyViewEvent(eventInfo);
  }

  @ReactMethod
  public void trackEvent(String action, String category) {
  	String comScoreEventName = category + "." + action;
  	EventInfo eventInfo = new EventInfo();
    eventInfo.setLabel("event", comScoreEventName);
    Analytics.notifyViewEvent(eventInfo);
  }

  public String getAppName() {
  	return this.appName;
  }

  public void setAppName(String appName) {
  	this.appName = appName;
  }

}
