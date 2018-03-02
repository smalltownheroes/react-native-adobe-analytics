# react-native-comscore

## Getting started

`$ npm install react-native-comscore --save`

### Mostly automatic installation

`$ react-native link react-native-comscore`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-comscore` and add `RNComscore.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNComscore.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import be.smalltownheroes.ketnet.karrewiet.RNComscorePackage;` to the imports at the top of the file
  - Add `new RNComscorePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-comscore'
  	project(':react-native-comscore').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-comscore/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-comscore')
  	```


## Usage
```javascript
import RNComscore from 'react-native-comscore';

# react-native-adobe-analytics
