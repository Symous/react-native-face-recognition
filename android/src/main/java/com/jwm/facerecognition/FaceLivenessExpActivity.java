package com.jwm.facerecognition;

import android.os.Bundle;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;

import java.util.HashMap;

public class FaceLivenessExpActivity extends FaceLivenessActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {

//            Intent i = new Intent();

//            i.putExtra("images", base64ImageMap);

//            SerializableHashMap serializableHashMap = new SerializableHashMap();
//            serializableHashMap.setMap(base64ImageMap);

//            i.putExtra("images", serializableHashMap);

//            Bundle bundle = new Bundle();
//            bundle.putSerializable("images", serializableHashMap);
//            i.putExtras(bundle);
//            setResult(1, i);

//            原本想直接将获取的采集结果，通过setResult传递回Module
//            经过测试发现，当数据量较大时（超过1MB），此处会导致当前活动无法结束
//            https://stackoverflow.com/questions/12819617/issue-passing-large-data-to-second-activity#comment49410631_12848199
//            所以采用静态变量的处理方式
            FaceRecognitionModule.base64ImageMap = base64ImageMap;
            setResult(1);
            finish();

        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            setResult(0);
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
