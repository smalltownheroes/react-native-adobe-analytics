
package be.smalltownheroes.ketnet.karrewiet;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.comscore.analytics.comScore;

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
    try {
        comScore.setAppContext(getReactApplicationContext());
        comScore.setAppName(comScoreAppName);
        comScore.setPublisherSecret(comScorePublisherSecret);
        comScore.setPixelURL(comScorePixelUrl);
        HashMap<String, String> labels = new HashMap<String, String>();
        labels.put("category", "app");
        labels.put("behoefte", "verbindend");
        labels.put("doelgroep", "none");
        labels.put("marketing", "none");
        labels.put("mediatype", "tv");
        labels.put("productiehuis", "Small Town Heroes");
        labels.put("waar", "app");
        comScore.setLabels(labels);
      } catch (Exception ex) {
      	// fail silently for now
      }
    }
    // String text = "INIT: appName: {" + comScoreAppName + "} - publisherSecret: {" + comScorePublisherSecret + "} - pixelUrl: {" + comScorePixelUrl + "}";
    // Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

  @ReactMethod
  public void trackView(String view) {
  	try {
	    String comScoreViewName = this.comScoreAppName + viewName;
	    HashMap<String, String> labels = new HashMap<String, String>();
	    labels.put("name", comScoreViewName.replace("/", "."));
	    comScore.view(labels);
	  } catch (Exception ex) {
    	// fail silently for now
    }
    // String text = "TRACKVIEW: view: {" + view + "}";
    // Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

  @ReactMethod
  public void trackEvent(String action, String category) {
  	// not implemented yet
    // String text = "TRACKEVENT: action: {" + action + "} - category: {" + category + "}";
    // Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

}
