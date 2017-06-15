
package be.smalltownheroes.ketnet.karrewiet;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

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
    String comScoreAppName = options.getString("appName");
    String comScorePublisherSecret = options.getString("publisherSecret");
    String comScorePixelUrl = options.getString("pixelUrl");
    String text = "INIT: appName: {" + comScoreAppName + "} - publisherSecret: {" + comScorePublisherSecret + "} - pixelUrl: {" + comScorePixelUrl + "}";
    Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

  @ReactMethod
  public void trackView(String view) {
    String text = "TRACKVIEW: view: {" + view + "}";
    Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

  @ReactMethod
  public void trackEvent(String action, String category) {
    String text = "TRACKEVENT: action: {" + action + "} - category: {" + category + "}";
    Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_LONG).show();
  }

}
