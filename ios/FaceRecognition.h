#import <React/RCTBridgeModule.h>
#import "LivenessViewController.h"
#import <React/RCTEventEmitter.h>

@interface FaceRecognition : RCTEventEmitter <RCTBridgeModule,LivenessViewControllerDelegate>

@end
