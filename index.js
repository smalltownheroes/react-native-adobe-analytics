import { NativeModules } from 'react-native';
import { PropTypes } from 'prop-types';

const { RNAdobeAnalytics } = NativeModules;

// ------------------------------------
// API
// ------------------------------------

export class AdobeAnalyticsAPI {
  static init(debug = false) {
    RNAdobeAnalytics.init({ debug });
  }

  static trackState(state, contextData = {}) {
    RNAdobeAnalytics.trackState(state, contextData);
  }

  static trackAction(action, contextData = {}) {
    RNAdobeAnalytics.trackAction(action, contextData);
  }

  static trackVideo(action, settings = {}) {
    RNAdobeAnalytics.trackVideo(action, settings);
  }
}

new AdobeAnalyticsAPI();

// ------------------------------------
// Init component
// ------------------------------------

export const AdobeAnalytics = ({ debug }) => {
  AdobeAnalyticsAPI.init(debug);

  return null;
};

AdobeAnalytics.propTypes = {
  debug: PropTypes.bool
};

// ------------------------------------
// TrackState component
// ------------------------------------

export const AdobeAnalyticsTrackState = ({ state, contextData }) => {
  AdobeAnalyticsAPI.trackState(state, contextData);

  return null;
};

AdobeAnalyticsTrackState.propTypes = {
  state: PropTypes.string.isRequired,
  contextData: PropTypes.object
};

// ------------------------------------
// TrackAction component
// ------------------------------------

export const AdobeAnalyticsTrackAction = ({ state, contextData }) => {
  AdobeAnalyticsAPI.trackAction(state, contextData);

  return null;
};

AdobeAnalyticsTrackAction.propTypes = {
  state: PropTypes.string.isRequired,
  contextData: PropTypes.object
};

// ------------------------------------
// AdobeAnalytics component
// ------------------------------------

export const AdobeAnalyticsTrackVideo = ({ action, settings }) => {
  AdobeAnalyticsAPI.trackVideo(action, settings);

  return null;
};

AdobeAnalyticsTrackVideo.propTypes = {
  action: PropTypes.string.isRequired,
  settings: PropTypes.object
};
