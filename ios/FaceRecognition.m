#import "FaceRecognition.h"
#import "IDLFaceSDK/IDLFaceSDK.h"
#import "FaceParameterConfig.h"
#import "LivenessViewController.h"
#import "LivingConfigModel.h"
#import <React/RCTUtils.h>


@implementation FaceRecognition

RCT_EXPORT_MODULE(FaceRecognitionManager)

RCT_EXPORT_METHOD(sampleMethod:(NSString *)stringArgument numberParameter:(nonnull NSNumber *)numberArgument callback:(RCTResponseSenderBlock)callback)
{
    // TODO: Implement some actually useful functionality
    callback(@[[NSString stringWithFormat: @"numberArgument: %@ stringArgument: %@", numberArgument, stringArgument]]);
}

RCT_EXPORT_METHOD(init: (RCTResponseSenderBlock)callback) {
    NSString* licensePath = [[NSBundle mainBundle] pathForResource:FACE_LICENSE_NAME ofType:FACE_LICENSE_SUFFIX];
    NSAssert([[NSFileManager defaultManager] fileExistsAtPath:licensePath], @"license文件路径不对，请仔细查看文档");
    [[FaceSDKManager sharedInstance] setLicenseID:FACE_LICENSE_ID andLocalLicenceFile:licensePath];
    NSLog(@"canWork = %d",[[FaceSDKManager sharedInstance] canWork]);
    NSLog(@"version = %@",[[FaceVerifier sharedInstance] getVersion]);
    callback(@[@YES]);
}

RCT_EXPORT_METHOD(detectFaceLiveness) {
    if([[FaceSDKManager sharedInstance] canWork]){
        NSString *licensePath = [[NSBundle mainBundle] pathForResource:FACE_LICENSE_NAME ofType:FACE_LICENSE_SUFFIX];
        [[FaceSDKManager sharedInstance] setLicenseID:FACE_LICENSE_ID andLocalLicenceFile:licensePath];
    }
    LivenessViewController* lvc = [[LivenessViewController alloc] init];
    LivingConfigModel* model = [LivingConfigModel sharedInstance];
    [lvc livenesswithList:model.liveActionArray order:model.isByOrder numberOfLiveness:model.numOfLiveness];
    UINavigationController *navi = [[UINavigationController alloc] initWithRootViewController:lvc];
    navi.navigationBarHidden = true;
    UIWindow *window = RCTSharedApplication().delegate.window;
    [[window rootViewController] presentViewController:navi animated:true completion:nil];
}


@end
