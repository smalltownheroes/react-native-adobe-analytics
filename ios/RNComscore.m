#import <Foundation/Foundation.h>
#import "CSComScore.h"
#import "RNComscore.h"
#if __has_include("RCTLog.h")
#import "RCTLog.h"
#else
#import <React/RCTLog.h>
#endif

@implementation RNComscore {

}


NSString *comScoreAppName;
NSString *comScoreCustomerC2;
NSString *comScorePublisherSecret;
NSString *comScorePixelUrl;

- (instancetype)init
{
    if ((self = [super init])) {
        
        
        // Start ComScore
        // SELogDebug(@"[ComScore]:\nCSAppName:%@\nCSCustomerC2:%@\nCSPublisherSecret:%@\nCSPixelUrl:%@\n",
                   //CSAppName, CSCustomerC2, CSPublisherSecret, CSPixelUrl);
        
        // SELogDebug(@"[ComScore] startTracker for CustomerC2: %@", CSCustomerC2);
        
      
        
        comScoreAppName = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"ComScoreAppName"];
        comScorePublisherSecret = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"ComScorePublisherSecret"];
        comScorePixelUrl = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"ComScorePixelUrl"];
        
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
    return self;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_queue_create("com.facebook.React.AsyncLocalStorageQueue", DISPATCH_QUEUE_SERIAL);
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(trackView:(NSString *) view)
{
    
    // slashes become dots
    NSString *dottedView = [view stringByReplacingOccurrencesOfString:@"/" withString:@"."];
    // all lowercase
    //NSString *comscoreViewName = [[CSAppName stringByAppendingString:dottedView ] lowercaseString];
    NSString *comscoreViewName = [CSAppName stringByAppendingString:dottedView ];
    
    [CSComScore viewWithLabels:@{@"name":comscoreViewName }];
}

RCT_EXPORT_METHOD(trackEvent:(NSString *)action category:(NSString *)category)
{
    NSString *eventName = [NSString stringWithFormat:@"%@.%@",category,action];
    [CSComScore hiddenWithLabels:@{@"name": eventName}];
}

RCT_EXPORT_METHOD(show:(NSString *)type location:(NSString *)message)
{
    RCTLogInfo(@"Pretending to show %@ at %@", type, message);
}

@end
