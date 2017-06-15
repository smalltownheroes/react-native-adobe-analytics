#import <Foundation/Foundation.h>
#import "CSComScore.h"
#import "RNComscore.h"
#if __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import <React/RCTConvert.h>
#endif

@implementation RNComScore {

}

NSString *comScoreAppName;
NSString *comScorePublisherSecret;
NSString *comScorePixelUrl;

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

    //  SELogDebug(@"%@", labels);
    [CSComScore setLabels:labels];
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


@end
