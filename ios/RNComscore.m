#import <Foundation/Foundation.h>
#import "CSComScore.h"
#import "CSStreamSense.h"
#import "RNComscore.h"
#if __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import <React/RCTConvert.h>
#endif

static CSStreamSense *streamSense;

@implementation RNComScore {

}

NSString *comScoreAppName;
NSString *comScorePublisherSecret;
NSString *comScorePixelUrl;

NSString *streamSenseMediaPlayer;
NSString *streamSenseVersion;
NSString *streamSenseChannel;

- (dispatch_queue_t)methodQueue
{
	return dispatch_queue_create("com.facebook.React.AsyncLocalStorageQueue", DISPATCH_QUEUE_SERIAL);
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(init:(NSDictionary *) options)
{

	comScoreAppName = [RCTConvert NSString:options[@"appName"]];
	comScorePublisherSecret = [RCTConvert NSString:options[@"publisherSecret"]];
	comScorePixelUrl = [RCTConvert NSString:options[@"pixelUrl"]];

	streamSenseMediaPlayer = [RCTConvert NSString:options[@"streamSenseMediaPlayer"]];
	streamSenseVersion = [RCTConvert NSString:options[@"streamSenseVersion"]];
	streamSenseChannel = [RCTConvert NSString:options[@"streamSenseChannel"]];

	[CSComScore setAppContext];
	[CSComScore setAppName:comScoreAppName];
	[CSComScore setPublisherSecret:comScorePublisherSecret];
	[CSComScore setPixelURL:comScorePixelUrl];

	NSMutableDictionary *labels = [NSMutableDictionary dictionary];
	labels[@"category"] = @"karrewiet";
	labels[@"behoefte"] = @"verbindend";
	labels[@"doelgroep"] = @"kind";
	labels[@"marketing"] = @"none";
	labels[@"mediatype"] = @"tv";
	labels[@"productiehuis"] = @"Small Town Heroes";
	labels[@"waar"] = @"app";

	[CSComScore setLabels:labels];

	streamSense = [[CSStreamSense alloc] init];
	[streamSense setLabels:@{
		 @"ns_st_mp": streamSenseMediaPlayer,
		 @"ns_st_mv": streamSenseVersion,
		 @"ns_st_st": streamSenseChannel
	 }];
}

RCT_EXPORT_METHOD(trackView:(NSString *) view)
{
	// slashes become dots
	NSString *dottedView = [view stringByReplacingOccurrencesOfString:@"/" withString:@"."];
	// all lowercase
	//NSString *comscoreViewName = [[CSAppName stringByAppendingString:dottedView ] lowercaseString];
	NSString *comscoreViewName = [comScoreAppName stringByAppendingString:dottedView ];
	[CSComScore viewWithLabels:@{@"name":comscoreViewName }];
}

RCT_EXPORT_METHOD(trackEvent:(NSString *)action category:(NSString *)category)
{
	NSString *eventName = [NSString stringWithFormat:@"%@.%@",category,action];
	[CSComScore hiddenWithLabels:@{@"name": eventName}];
}

RCT_EXPORT_METHOD(trackVideoStreaming:(NSDictionary*)videoInfo category:(NSString *)videoAction)
{
	NSLog( @"Video Action: '%@'", videoAction );
	long position = videoInfo[@"position"] ? [videoInfo[@"position"] longValue] : 0L;
	long length =  videoInfo[@"length"] ? [videoInfo[@"length"] longValue] : 0L;

	NSString *publicationDate = videoInfo[@"publication_date"] ? videoInfo[@"publication_date"] : @"";

	NSString *formattedPublicationDate;
	if (![publicationDate isEqualToString:@""]) {
		NSDateFormatter *formatter = [NSDateFormatter new];
		[formatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss'Z'"];
		NSDate *date = [formatter dateFromString:publicationDate];
		[formatter setDateFormat:@"YYYYMMdd"];
		formattedPublicationDate = [formatter stringFromDate:date];
	}

	[streamSense setClip:@ {
	   @"ns_st_cl": @(length),
	   @"ns_st_el": @(length),
	   @"ns_st_tp": videoInfo[@"parts"] ? videoInfo[@"parts"] : @"",
	   @"ns_st_ci": videoInfo[@"whatson"] ? videoInfo[@"whatson"] : @"",
	   @"vrt_vid_id": videoInfo[@"whatson"] ? videoInfo[@"whatson"] : @"",
	   @"ns_st_pr": videoInfo[@"program"] ? videoInfo[@"program"] : @"",
	   @"ns_st_ep": videoInfo[@"episode"] ? videoInfo[@"episode"] : @"",
	   @"ns_st_ty": videoInfo[@"type_stream"] ? videoInfo[@"type_stream"] : @"",
	   @"vrt_dat_id": formattedPublicationDate ? formattedPublicationDate : @""
	}];
	if ([videoAction isEqualToString:@"start"]) {
		// NSLog( @"notifyPlay: '%@'", videoAction );
		[streamSense notify:CSStreamSensePlay position:position];
	} else if ([videoAction isEqualToString:@"resume"]) {
		// NSLog( @"notifyPlay: '%@'", videoAction );
		[streamSense notify:CSStreamSensePlay position:position];
	} else if ([videoAction isEqualToString:@"stop"]) {
		// NSLog( @"notifyStop: '%@'", videoAction );
		[streamSense notify:CSStreamSenseEnd position:position];
	} else if ([videoAction isEqualToString:@"pause"]) {
		// NSLog( @"notifyPause: '%@'", videoAction );
		[streamSense notify:CSStreamSensePause position:position];
	}
}

@end
