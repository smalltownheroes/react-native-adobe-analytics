
package be.smalltownheroes.adobe.analytics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

public class RNAdobeAnalyticsPackage implements ReactPackage {
		@Override
		public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
			return Arrays.<NativeModule>asList(new RNAdobeAnalyticsModule(reactContext));
		}

		public List<Class<? extends JavaScriptModule>> createJSModules() {
			return Collections.emptyList();
		}

		@Override
		public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
			return Collections.emptyList();
		}
}
