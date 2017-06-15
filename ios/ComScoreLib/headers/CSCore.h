//
//  CScomScore.h
//  comScore
//
// Copyright 2014 comScore, Inc. All right reserved.
//

#ifndef CSCOMSCORE_H
#define CSCOMSCORE_H

#import "CSEventType.h"
#import "CSTransmissionMode.h"
#import "CSApplicationState.h"
#import "CSSessionState.h"
#import "CSComScore.h"

@class CSCacheFlusher;
@class CSCensus;
@class CSNotificationsObserver;
@class CSOfflineCache;
@class CSStorage;
@class CSKeepAlive;
@class CSTaskExecutor;
@class CSMeasurementDispatcher;

/**
 ComScore analytics interface
 */
@interface CSCore : NSObject{

    BOOL _errorHandlingEnabled;
    
    CSStorage *_storage;
    CSTaskExecutor *_taskExecutor;
    CSMeasurementDispatcher *_measurementDispatcher;
    CSOfflineCache *_offlineCache;
    CSNotificationsObserver *observer;
    CSKeepAlive *_keepAlive;
    CSCacheFlusher *_cacheFlusher;
}

/**
 PixelURL setter.
 
 Parameters:
 
 - value: A NSString that contains the PixelURL.
 */
- (NSString *)setPixelURL:(NSString *)value background:(BOOL)background;

/**
 Notify Application event (Start / Close / Aggregate) with custom labels
 
 Parameters:
 
 - type: A CSApplicationEventType enum that value must be Start, Close or Aggregate.
 - labels: A NSDictionary that contains a set of custom labels with key-value pairs.
 */
- (void)notifyWithApplicationEventType:(CSApplicationEventType)type labels:(NSDictionary *)labels;

- (id)init;

- (CSStorage *)storage;

- (CSOfflineCache *)offlineCache;

- (CSNotificationsObserver *)observer;

- (CSKeepAlive *)keepAlive;

- (CSCacheFlusher *)cacheFlusher;

- (void)setVisitorId:(NSString *)value background:(BOOL)background;

- (void)resetVisitorID;

- (void)restoreVisitorId;

- (void)setPublisherSecret:(NSString *)value background:(BOOL)background;

- (void)setAppName:(NSString *)value background:(BOOL)background;

- (NSString *)generateVisitorId;

- (NSString *)generateVisitorIdWithPublisherSecret:(NSString *)publisherSecret;

- (long long)previousGenesis;

- (NSString *)getPlainMACAddress;

- (void)setLabel:(NSString *)name value:(NSString *)value background:(BOOL)background;

- (void)setLabels:(NSDictionary *)labels background:(BOOL)background;

- (NSString *)label:(NSString *)labelName;

- (void)setAutoStartLabel:(NSString *)name value:(NSString *)value background:(BOOL)background;

- (void)setAutoStartLabels:(NSDictionary *)labels background:(BOOL)background;

- (NSString *)autoStartLabel:(NSString *)labelName;

- (void)setKeepAliveEnabled:(BOOL)enabled background:(BOOL)background;

- (void)setCustomerC2:(NSString *)c2 background:(BOOL)background;

- (NSString *)customerC2;

- (void)setSecure:(BOOL)secure background:(BOOL)background;

- (NSString *)crossPublisherID;

- (BOOL)crossPublisherIdChanged;

- (void)disableAutoUpdate;

- (void)enableAutoUpdate:(int)intervalInSeconds foregroundOnly:(BOOL)foregroundOnly;

- (NSString *)previousVersion;

- (BOOL)handleColdStart:(long long)timestamp;

- (BOOL)isAutoUpdateEnabled;

- (void)onEnterForeground;

- (void)onExitForeground;

- (void)onUserInteraction;

- (void)onUxActive;

- (void)onUxInactive;

- (void)setMeasurementLabelOrder:(NSArray *)ordering background:(BOOL)background;

- (void)update:(long long)timestamp store:(BOOL)store;

- (NSString *)version;

- (BOOL)isJailBroken;

- (long long)totalBackgroundTime:(BOOL)reset;

- (long long)totalInactiveTime:(BOOL)reset;

- (void)update;

- (BOOL)isNotProperlyInitialized;

- (void)setOfflineURL:(NSString *)value background:(BOOL)background;

/** 
 Enables or disables live events (GETs) dispatched one by one when connectivity is available
 */
- (void)allowLiveTransmission:(CSTransmissionMode)mode background:(BOOL)background;

/**
 Enables or disables automatic offline cache flushes (POSTS). The cache can always be manually 
 flushed using the public api comScore.FlushOfflineCache()
 */
- (void)allowOfflineTransmission:(CSTransmissionMode)mode background:(BOOL)background;

- (void)setCacheFlushingInterval:(long)seconds background:(BOOL)background;

/** Returns the task executor queue.
    The task executor queue is used to perform sequential operations in a background thread.
 */
- (CSTaskExecutor *)taskExecutor;

/** Returns the measurement dispatcher instance */
- (CSMeasurementDispatcher *)measurementDispatcher;

- (void)setAutoStartEnabled:(BOOL)value background:(BOOL)background;

/**
 * Enables or disables tracking. When tracking is disabled, no measurement is sent and
 * no data is collected.
 */
- (void)setEnabled:(BOOL)enabled background:(BOOL)background;

/**
 * Indicates if tracking is enabled. When tracking is disabled, no measurement is sent and
 * no data is collected.
 */
- (BOOL)enabled;

@property(retain) NSString *visitorID;
@property(retain) NSString *publisherSecret;
@property(retain) NSString *appName;
@property(retain) NSMutableDictionary *labels;
@property(retain) NSMutableDictionary *autoStartLabels;
@property(retain) NSString *devModel;
@property BOOL isKeepAliveEnabled;
@property long cacheFlushingInterval;
@property(retain) NSString *crossPublisherID_;
@property(retain) NSString *md5CrossPublisherRawId;
@property BOOL isCrossPublisherIdBasedOnIFDA;
@property NSUncaughtExceptionHandler *defaultUncaughtExceptionHandler;
@property BOOL autoStartEnabled;
@property BOOL secure;
@property CSTransmissionMode liveTransmissionMode;
@property CSTransmissionMode offlineTransmissionMode;
@property(retain) NSArray *measurementLabelOrder;
@property(retain) NSNumber *adSupportFrameworkAvailable; // this is used so that we only check once for availability of the ad support framework
@property BOOL adIdChanged;
@property int adIdEnabled; // -1: not setted, 0 disabled, 1 enabled
@property BOOL idChangedWhenAppNotRunning;

// Common state machine fields
@property long long autoUpdateInterval;
@property BOOL autoUpdateInForegroundOnly;
@property int runsCount;
@property long long coldStartId;
@property int coldStartCount;
@property BOOL coldStart;
@property long long installId;
@property long long firstInstallId;
@property(retain) NSString *currentVersion;
@property(retain) NSString *previousVersion_;

// Application State Machine
@property CSApplicationState currentApplicationState;
@property int foregroundComponentsCount;
@property int activeUxComponentsCount;
@property int foregroundTransitionsCount;
@property long long totalForegroundTime;
@property long long accumulatedBackgroundTime;
@property long long accumulatedForegroundTime;
@property long long accumulatedInactiveTime;
@property long long genesis;
@property long long previousGenesis_;
@property long long lastApplicationAccumulationTimestamp;
@property long long totalBackgroundTime;
@property long long totalInactiveTime;

// Session State Machine
@property CSSessionState currentSessionState;
@property long long accumulatedApplicationSessionTime;
@property long long accumulatedUserSessionTime;
@property long long accumulatedActiveUserSessionTime;
@property int userSessionCount;
@property int activeUserSessionCount;
@property long long lastApplicationSessionTimestamp;
@property long long lastUserSessionTimestamp;
@property long long lastActiveUserSessionTimestamp;
@property int userInteractionCount;
@property long long lastUserInteractionTimestamp;
@property long long lastSessionAccumulationTimestamp;
@property int applicationSessionCount;
@property(retain) NSString *userInteractionTimerId;
@property(retain) NSString *autoUpdateTimerId;

@property(retain) NSMutableSet *ssids;
@property BOOL enabled_;
@property BOOL wasErrorHandlingEnabled;

@property(assign, getter = isErrorHandlingEnabled) BOOL errorHandlingEnabled;
@property(readonly, assign) NSString *pixelURL;

@end

#endif // ifndef CSCOMSCORE_H