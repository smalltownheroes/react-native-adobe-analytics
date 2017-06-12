
package be.smalltownheroes.ketnet.karrewiet;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNComscoreModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNComscoreModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNComscore";
  }

  @ReactMethod
  public void show(String type, String message) {
    String text = "Type: " + type + " - message:" + message;
    Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_LONG).show();
  }
}