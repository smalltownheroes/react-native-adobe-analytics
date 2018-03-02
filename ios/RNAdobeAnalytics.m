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
#import <React/RCTConvert.h>

@implementation RNAdobeAnalytics

RCT_EXPORT_MODULE();

// init Adobe mobile

- (void)startTrackerAdobe {
    
    NSString *adbMobileConfig = [[[NSBundle mainBundle] infoDictionary] valueForKey:@"ADOBE_MOBILE_CONFIG"];
    
    [ADBMobile collectLifecycleData];
    [ADBMobile overrideConfigPath:[[NSBundle mainBundle] pathForResource:adbMobileConfig ofType:@"json"]];

}

// track event

- (void)trackEventAdobe:(NSString *)action category:(NSString *)category {
    
    NSString *eventName = [NSString stringWithFormat:@"%@.%@",category,action];
    NSMutableDictionary *contextData = [NSMutableDictionary dictionary];
    [contextData setObject:category forKey:eventName];
    [ADBMobile trackAction:action data:contextData];
}

// track view

- (void)trackViewAdobe:(NSString *)view {
    NSString *dottedView = [view stringByReplacingOccurrencesOfString:@"/" withString:@"."];
    [ADBMobile trackState:dottedView data:nil];
}

@end

