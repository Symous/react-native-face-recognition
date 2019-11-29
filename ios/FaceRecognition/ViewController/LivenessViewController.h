//
//  LivenessViewController.h
//  IDLFaceSDKDemoOC
//
//  Created by 阿凡树 on 2017/5/23.
//  Copyright © 2017年 Baidu. All rights reserved.
//

#import "FaceBaseViewController.h"

@protocol LivenessViewControllerDelegate <NSObject>

- (void)livenessDetectionResult:(Boolean)isSucceed images:(NSDictionary *)images error:(NSString *) errorMsg;

@end

@interface LivenessViewController : FaceBaseViewController

@property (nonatomic, weak) id<LivenessViewControllerDelegate>delegate;

- (void)livenesswithList:(NSArray *)livenessArray order:(BOOL)order numberOfLiveness:(NSInteger)numberOfLiveness;

@end
