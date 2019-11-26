package com.jwm.facerecognition;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FaceLivenessExpActivity extends FaceLivenessActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            WritableArray resultImageArray = Arguments.createArray();
            Iterator iterator = base64ImageMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                WritableMap resultImage = Arguments.createMap();
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                resultImage.putString("type", key);
                resultImage.putString("code", value);
                resultImageArray.pushMap(resultImage);
            }
            sendEvent("onFaceLivenessDetected", resultImageArray);
            finish();

        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            sendEvent("onFaceLivenessTimeout", null);
            finish();
        }
    }


    @Override
    public void finish() {
        super.finish();
    }

    private void sendEvent(String event, @Nullable Object params) {
        FaceRecognitionModule.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event, params);
    }

}
