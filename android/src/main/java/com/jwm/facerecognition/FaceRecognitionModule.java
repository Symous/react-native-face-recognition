
package com.jwm.facerecognition;

import android.content.Intent;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class FaceRecognitionModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public FaceRecognitionModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "FaceRecognitionManager";
  }

  @ReactMethod
  public void callMe() {
    System.out.println("aaaaaa");
  }

  @ReactMethod
  public void init() {
    FaceSDKManager.getInstance().initialize(reactContext,Config.licenseID, Config.licenseFileName);

    FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
    // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
    config.setLivenessTypeList(ExampleApplication.livenessList);
    config.setLivenessRandom(ExampleApplication.isLivenessRandom);
    config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
    config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
    config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
    config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
    config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
    config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
    config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
    config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
    config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
    config.setCheckFaceQuality(true);
    config.setFaceDecodeNumberOfThreads(2);

    FaceSDKManager.getInstance().setFaceConfig(config);
  }

  @ReactMethod
  public void startCollectFace() {
    getCurrentActivity().startActivity(new Intent(reactContext, FaceLivenessExpActivity.class));
  }
}