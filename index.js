
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

	trackVideoStreaming(info, action) {
		RNAdobeAnalytics.trackVideoStreaming(info, action);
	}

}

export default AdobeAnalyticsTracker;
