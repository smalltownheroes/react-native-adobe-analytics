
#import "RNComscore.h"
#if __has_include("RCTLog.h")
#import "RCTLog.h"
#else
#import <React/RCTLog.h>
#endif

@implementation RNComscore

- (dispatch_queue_t)methodQueue
{
  return dispatch_queue_create("com.facebook.React.AsyncLocalStorageQueue", DISPATCH_QUEUE_SERIAL);
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(show:(NSString *)type location:(NSString *)message)
{
  RCTLogInfo(@"Pretending to show %@ at %@", type, message);
}

@end
