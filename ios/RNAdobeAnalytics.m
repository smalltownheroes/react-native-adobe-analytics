//
//  RNAdobeAnalytics.m
//  RNAdobeAnalytics
//
//  Created by gregoire van der auwermeulen on 02/03/2018.
//  Copyright © 2018 VRT. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ADBMobile.h"
#import "RNAdobeAnalytics.h"

#if __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import <React/RCTConvert.h>
#endif

@implementation RNAdobeAnalytics {
    
}

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
    return dispatch_queue_create("com.facebook.React.AsyncLocalStorageQueue", DISPATCH_QUEUE_SERIAL);
}


// init Adobe mobile

// track event
RCT_EXPORT_METHOD(trackEventAdobe:(NSString *)action category:(NSString *)category) {
    NSString *eventName = [NSString stringWithFormat:@"%@.%@",category,action];
    NSMutableDictionary *contextData = [NSMutableDictionary dictionary];
    [contextData setObject:category forKey:eventName];
    [ADBMobile trackAction:action data:contextData];
}

// track view
RCT_EXPORT_METHOD(trackViewAdobe:(NSString *)view) {
    NSString *dottedView = [view stringByReplacingOccurrencesOfString:@"/" withString:@"."];
    [ADBMobile trackState:dottedView data:nil];
}

@end



