#import <Foundation/Foundation.h>
#import "RNAdobeAnalytics.h"
#import "ADBMobile.h"

// import RCTBridge
#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#elif __has_include(“RCTBridge.h”)
#import “RCTBridge.h”
#else
#import “React/RCTBridge.h” // Required when used as a Pod in a Swift project
#endif

// import RCTEventDispatcher
#if __has_include(<React/RCTEventDispatcher.h>)
#import <React/RCTEventDispatcher.h>
#elif __has_include(“RCTEventDispatcher.h”)
#import “RCTEventDispatcher.h”
#else
#import “React/RCTEventDispatcher.h” // Required when used as a Pod in a Swift project
#endif

@implementation RNAdobeAnalytics

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(init: (NSDictionary *)options)
{
    NSString *config = [[NSBundle mainBundle] pathForResource:@"ADBMobileConfig" ofType:@"json"];
    
    [ADBMobile collectLifecycleData];
    [ADBMobile overrideConfigPath:config];
    
    BOOL debug = options[@"debug"];
    
    if (debug) {
        [ADBMobile setDebugLogging:debug];
    }
}

RCT_REMAP_METHOD(urlWithAdobeVisitorInfo,
                 url:(NSString *)url
                 urlWithAdobeVisitorInfoWithResolver:(RCTPromiseResolveBlock)resolve
                 rejector:(RCTPromiseRejectBlock)reject) {
    
    NSURL *myUrl = [NSURL URLWithString:url];
    NSURL *urlWithVisitorData = [ADBMobile visitorAppendToURL: myUrl];
    NSLog(@"Input url: %@", url);
    NSLog(@"Visitor url: %@", urlWithVisitorData.absoluteString);
    if(urlWithVisitorData) {
        resolve(urlWithVisitorData.absoluteString);
    } else {
        NSError *error = [NSError errorWithDomain:NSURLErrorDomain
                                             code:1000
                                         userInfo:@{@"URL": @"is broken"}];
        reject(@"no_url",@"There is no URL",error);
    }
    
}

RCT_EXPORT_METHOD(marketingCloudId:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *mcid = [ADBMobile visitorMarketingCloudID];
    NSLog(@"Visitor Marketing Cloud ID: %@", mcid);
    if (mcid) {
        resolve(mcid);
    } else {
        reject(mcid);
    }
}

RCT_EXPORT_METHOD(trackAction: (NSString *)action contextData:(NSDictionary *)contextData) {
    [ADBMobile trackAction:action data:contextData];
}

RCT_EXPORT_METHOD(trackState: (NSString *)state contextData:(NSDictionary *)contextData) {
    [ADBMobile trackState:state data:contextData];
}

//Extending the framework to include timed actions and tracking marketing cloud ID for user

RCT_EXPORT_METHOD(trackTimedActionStart: (NSString *)action contextData:(NSDictionary *)contextData) {
    [ADBMobile trackTimedActionStart:action data:contextData];
}

RCT_EXPORT_METHOD(trackTimedActionUpdate: (NSString *)action contextData:(NSDictionary *)contextData) {
    [ADBMobile trackTimedActionUpdate:action data:contextData];
}


RCT_EXPORT_METHOD(trackTimedActionEnd: (NSString *)action) {
    [ADBMobile trackTimedActionEnd:action logic: nil];
}




@end
