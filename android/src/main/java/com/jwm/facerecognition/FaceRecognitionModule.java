
package com.jwm.facerecognition;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FaceRecognitionModule extends ReactContextBaseJavaModule {

    public ReactApplicationContext reactContext;

    public List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
    public boolean isLivenessRandom = false;

    public static HashMap<String, String> base64ImageMap = null;

    public final int LIVENESS_CODE = 28;

    public FaceRecognitionModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(activityEventListener);
    }

    @Override
    public String getName() {
        return "FaceRecognitionManager";
    }

    @ReactMethod
    public void init(Callback callback) {
        try {
            FaceSDKManager.getInstance().initialize(reactContext, Config.licenseID, Config.licenseFileName);
            callback.invoke(true, null);
        } catch (Exception e) {
            callback.invoke(false, e.getLocalizedMessage());
        }
    }

    @ReactMethod
    public void detectFaceLiveness() {
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
        Intent intent = new Intent(reactContext, FaceLivenessExpActivity.class);
        //        getCurrentActivity().startActivity(intent);
        getCurrentActivity().startActivityForResult(intent, LIVENESS_CODE);
    }

    private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            reactContext.removeActivityEventListener(activityEventListener);
            if (requestCode == LIVENESS_CODE) {

                WritableMap result = Arguments.createMap();

                if (resultCode == 1) {
//                    HashMap<String, String> base64ImageMap = (HashMap<String, String>) data.getSerializableExtra("images");

                    //HashMap<String,String> base64ImageMap = ((SerializableHashMap)data.getSerializableExtra("images")).getMap();

//                    Bundle bundle = data.getExtras();
//                    HashMap<String, String> base64ImageMap = ((SerializableHashMap) bundle.get("images")).getMap();

                    WritableArray resultImageArray = Arguments.createArray();
                    Iterator iterator = base64ImageMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        WritableMap resultImage = Arguments.createMap();
                        String key = String.valueOf(entry.getKey());
                        String value = String.valueOf(entry.getValue());
                        resultImage.putString("type", key);
                        resultImage.putString("image", value);
                        resultImageArray.pushMap(resultImage);
                    }
                    result.putArray("images", resultImageArray);
//                    result.putString("error", null);

                    // 释放base64ImageMap
                    base64ImageMap = null;
                } else {
//                    result.putArray("images", null);
                    result.putString("error", data.getStringExtra("error"));
                }
                sendEvent("onFaceLivenessDetectFinished", result);
            }
        }
    };

    private void sendEvent(String event, @Nullable Object params) {
        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event, params);
    }

}