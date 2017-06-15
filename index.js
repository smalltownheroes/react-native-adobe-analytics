
import { NativeModules } from 'react-native';

const { RNComScore } = NativeModules;

class ComScoreTracker {

	constructor(options) {
		RNComScore.init(options);
	}

	trackView(view) {
		RNComScore.trackView(view);
	}

	trackEvent(action, category) {
		RNComScore.trackEvent(action, category);
	}

}

export default ComScoreTracker;
