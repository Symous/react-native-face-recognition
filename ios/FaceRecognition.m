#import "FaceRecognition.h"
#import "IDLFaceSDK/IDLFaceSDK.h"
#import "FaceParameterConfig.h"
#import "LivenessViewController.h"
#import "LivingConfigModel.h"
#import <React/RCTUtils.h>


@implementation FaceRecognition

-(NSArray*)supportedEvents {
    return@[@"onFaceLivenessDetectFinished"];
}

RCT_EXPORT_MODULE(FaceRecognitionManager)

RCT_EXPORT_METHOD(init: (RCTResponseSenderBlock)callback) {
    dispatch_async(dispatch_get_main_queue(), ^{
        @try {
            NSString* licensePath = [[NSBundle mainBundle] pathForResource:FACE_LICENSE_NAME ofType:FACE_LICENSE_SUFFIX];
            NSAssert([[NSFileManager defaultManager] fileExistsAtPath:licensePath], @"license文件路径不对，请仔细查看文档");
            [[FaceSDKManager sharedInstance] setLicenseID:FACE_LICENSE_ID andLocalLicenceFile:licensePath];
            NSLog(@"canWork = %d",[[FaceSDKManager sharedInstance] canWork]);
            NSLog(@"version = %@",[[FaceVerifier sharedInstance] getVersion]);
            callback(@[@YES]);
        } @catch (NSException *exception) {
            callback(@[@NO, exception.reason]);
        }
    });
}

RCT_EXPORT_METHOD(configLivenessTypes: (NSArray *)types isRandom: (BOOL)random ) {
    dispatch_async(dispatch_get_main_queue(), ^{
        LivingConfigModel* model = [LivingConfigModel sharedInstance];
        [model.liveActionArray removeAllObjects];
        if([types count] == 0) {
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveEye)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveMouth)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYawRight)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYawLeft)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLivePitchUp)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLivePitchDown)];
            [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYaw)];
        }
        else [model.liveActionArray addObjectsFromArray:types];
        model.isByOrder = !random;
    });
}

RCT_EXPORT_METHOD(detectFaceLiveness) {
    dispatch_async(dispatch_get_main_queue(), ^{
        if([[FaceSDKManager sharedInstance] canWork]){
            NSString *licensePath = [[NSBundle mainBundle] pathForResource:FACE_LICENSE_NAME ofType:FACE_LICENSE_SUFFIX];
            [[FaceSDKManager sharedInstance] setLicenseID:FACE_LICENSE_ID andLocalLicenceFile:licensePath];
        }
        LivenessViewController* lvc = [[LivenessViewController alloc] init];
        LivingConfigModel* model = [LivingConfigModel sharedInstance];
    //    [model.liveActionArray removeAllObjects];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveEye)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveMouth)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYawRight)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYawLeft)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLivePitchUp)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLivePitchDown)];
    //    [model.liveActionArray addObject:@(FaceLivenessActionTypeLiveYaw)];
    //
    //    //默认检测顺序是随机的
    //    model.isByOrder = YES;
        
        [lvc livenesswithList:model.liveActionArray order:model.isByOrder numberOfLiveness:model.numOfLiveness];
        lvc.delegate = self;
        UINavigationController *navi = [[UINavigationController alloc] initWithRootViewController:lvc];
        navi.navigationBarHidden = true;
        UIWindow *window = RCTSharedApplication().delegate.window;
        [[window rootViewController] presentViewController:navi animated:true completion:nil];
    });
}

- (void)livenessDetectionResult:(Boolean)isSucceed images:(NSDictionary *)images error:(NSString *)errorMsg {
    NSMutableDictionary *result = [[NSMutableDictionary alloc] init];
    if(isSucceed) {
        NSMutableArray *resultImageArray = [[NSMutableArray alloc] init];
        NSArray *keysArray = [images allKeys];
        for(NSString *key in keysArray) {
            NSMutableDictionary *resultImage = [[NSMutableDictionary alloc] init];
            [resultImage setObject:key forKey:@"type"];
            [resultImage setObject:images[key] forKey:@"image"];
            [resultImageArray addObject:resultImage];
        }
        [result setObject:resultImageArray forKey:@"images"];
    } else {
        [result setObject:errorMsg forKey:@"error"];
    }
    [self sendEventWithName:@"onFaceLivenessDetectFinished" body:result];
}

@end
