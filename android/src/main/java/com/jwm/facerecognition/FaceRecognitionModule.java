
package com.jwm.facerecognition;

import android.content.Intent;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;
import java.util.List;

public class FaceRecognitionModule extends ReactContextBaseJavaModule {

  public static ReactApplicationContext reactContext;

  public static List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
  public static boolean isLivenessRandom = false;

  public FaceRecognitionModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "FaceRecognitionManager";
  }

  @ReactMethod
  public void init(Callback callback) {
    try {
      FaceSDKManager.getInstance().initialize(reactContext, Config.licenseID, Config.licenseFileName);

      FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();

      // 根据需求添加活体动作
      livenessList.clear();
      livenessList.add(LivenessTypeEnum.Eye);
      livenessList.add(LivenessTypeEnum.Mouth);
      livenessList.add(LivenessTypeEnum.HeadUp);
      livenessList.add(LivenessTypeEnum.HeadDown);
      livenessList.add(LivenessTypeEnum.HeadLeft);
      livenessList.add(LivenessTypeEnum.HeadRight);
      livenessList.add(LivenessTypeEnum.HeadLeftOrRight);

      // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
      config.setLivenessTypeList(livenessList);
      config.setLivenessRandom(isLivenessRandom);
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

      callback.invoke(true, null);
    } catch (Exception e) {
      callback.invoke(false, e.getLocalizedMessage());
    }
  }

  @ReactMethod
  public void detectFaceLiveness() {
    Intent intent = new Intent(reactContext, FaceLivenessExpActivity.class);
    getCurrentActivity().startActivity(intent);
  }


}