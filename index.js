import { NativeModules, NativeEventEmitter } from 'react-native';

import { formatLivenessResult } from './LivenessActions';

const { FaceRecognitionManager } = NativeModules;
const eventEmitter = new NativeEventEmitter(FaceRecognitionManager);

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
        const listener = eventEmitter.addListener('onFaceLivenessDetectFinished', data => {
            // if (!data.error) resolve(data);
            if (!data.error) resolve(formatLivenessResult(data.images));
            else reject(data.error);
            listener.remove();
        });
    });
}

export default FaceRecognitionManager;
