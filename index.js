import { NativeModules, DeviceEventEmitter } from 'react-native';

const { FaceRecognitionManager } = NativeModules;

let faceLivenessSuccessListener = null;
let faceLivenessFailListener = null;
export function init() {
    return new Promise((resolve, reject) => {
        FaceRecognitionManager.init((status, error) => {
            if (status) resolve();
            else reject(error);
        });
    });
}

export function detectFaceLiveness() {
    FaceRecognitionManager.detectFaceLiveness();
    return new Promise((resolve, reject) => {
        faceLivenessSuccessListener = DeviceEventEmitter.addListener(
            'onFaceLivenessDetected',
            data => {
                resolve(data);
                faceLivenessSuccessListener.remove();
            }
        );
        faceLivenessFailListener = DeviceEventEmitter.addListener(
            'onFaceLivenessTimeout',
            error => {
                reject(error);
                faceLivenessFailListener.remve();
            }
        );
    });
}

export default FaceRecognitionManager;
