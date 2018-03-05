
import { NativeModules } from 'react-native';

const { RNAdobeAnalytics } = NativeModules;

class AdobeAnalyticsTracker {

	constructor(options) {
		RNAdobeAnalytics.init(options);
	}

	trackState(state, context) {
		RNAdobeAnalytics.trackState(state, context);
	}

	trackAction(action, context) {
		RNAdobeAnalytics.trackAction(action, context);
	}

	trackVideo(action, settings, cb) {
		RNAdobeAnalytics.trackVideo(action, settings, cb);
	}

}

export default AdobeAnalyticsTracker;
