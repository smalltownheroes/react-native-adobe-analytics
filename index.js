
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

	trackVideo(action, settings) {
		RNAdobeAnalytics.trackVideo(action, settings);
	}

}

export default AdobeAnalyticsTracker;
