//
//  CSStreamingTag.h
//  comScore
//
//  Copyright (c) 2014 comScore. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    CSContentTypeLongFormOnDemand = 111,
    CSContentTypeShortFormOnDemand = 112,
    CSContentTypeLive = 113,
    CSContentTypeUserGeneratedLongFormOnDemand = 121,
    CSContentTypeUserGeneratedShortFormOnDemand = 122,
    CSContentTypeUserGeneratedLive = 123,
    CSContentTypeBumper = 199,
    CSContentTypeOther = 100
} CSContentType;

typedef enum {
    CSAdTypeLinearOnDemandPreRoll = 211,
    CSAdTypeLinearOnDemandMidRoll = 212,
    CSAdTypeLinearOnDemandPostRoll = 213,
    CSAdTypeLinearLive = 221,
    CSAdTypeOther = 200
} CSAdType;

@interface CSStreamingTag : NSObject

- (void) playAdvertisement __deprecated_msg("Calling deprecated function 'playAdvertisement'. Please call 'playVideoAdvertisement' or 'playAudioAdvertisement' functions instead.");
- (void) playVideoAdvertisementWithMetadata:(NSDictionary *)metadata __deprecated_msg("Calling deprecated function 'playVideoAdvertisementWithMetadata:'. Please call 'playVideoAdvertisementWithMetadata:andMediaType:' instead.");
- (void) playVideoAdvertisementWithMetadata:(NSDictionary *)metadata andMediaType:(CSAdType)mediaType;
- (void) playVideoAdvertisement;
- (void) playAudioAdvertisementWithMetadata:(NSDictionary *)metadata __deprecated_msg("Calling deprecated function 'playAudioAdvertisementWithMetadata:'. Please call 'playAudioAdvertisementWithMetadata:andMediaType:' instead.");
- (void) playAudioAdvertisementWithMetadata:(NSDictionary *)metadata andMediaType:(CSAdType)mediaType;
- (void) playAudioAdvertisement;
- (void) playContentPartWithMetadata:(NSDictionary *)metadata __deprecated_msg("Calling deprecated function 'playContentPart'. Please call 'playVideoContentPart' or 'playAudioContentPart' functions instead.");
- (void) playVideoContentPartWithMetadata:(NSDictionary *)metadata __deprecated_msg("Calling deprecated function 'playVideoContentPartWithMetadata:'. Please call 'playVideoContentPartWithMetadata:andMediaType:' instead.");
- (void) playVideoContentPartWithMetadata:(NSDictionary *)metadata andMediaType:(CSContentType)mediaType;
- (void) playAudioContentPartWithMetadata:(NSDictionary *)metadata __deprecated_msg("Calling deprecated function 'playAudioContentPartWithMetadata:'. Please call 'playAudioContentPartWithMetadata:andMediaType:' instead.");
- (void) playAudioContentPartWithMetadata:(NSDictionary *)metadata andMediaType:(CSContentType)mediaType;
- (void) stop;

@end
