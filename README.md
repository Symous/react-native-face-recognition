
# react-native-face-recognition

## Getting started

`$ npm install -react-native-face-recognition --save`

### Mostly automatic installation

`$ react-native link -react-native-face-recognition`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `-react-native-face-recognition` and add `RNReactNativeFaceRecognition.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeFaceRecognition.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.jwm.facerecognition.RNReactNativeFaceRecognitionPackage;` to the imports at the top of the file
  - Add `new RNReactNativeFaceRecognitionPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':-react-native-face-recognition'
  	project(':-react-native-face-recognition').projectDir = new File(rootProject.projectDir, 	'../node_modules/-react-native-face-recognition/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':-react-native-face-recognition')
  	```


## Usage
```javascript
import RNReactNativeFaceRecognition from '-react-native-face-recognition';

// TODO: What to do with the module?
RNReactNativeFaceRecognition;
```
  