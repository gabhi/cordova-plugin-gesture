Register a gesture handler 
=====

Allows to register gesture handlers from JavaScript. 
Only standardized gestures (tap, swipe, ...?) would be supported to provide a consistent experience across many platforms.

A custom gesture handler (non-standard) could also be registered on the native side for more advanced use cases.

	// Registering gesture from JavaScript
	cordova.gesture.register('tap'); // standard gesture
	document.addEventListener('tap', function(){ alert('tap') }, false);

	// Making a custom gesture available in Android (java)
	// webView.registerGestureHandler('grab', new GestureDetectorHandGrab());

	// In JavaScript
	cordova.gesture.register('grab');

	var button = document.getElementById('button');
	button.addEventListener('grabstart', function(){ console.log('hand grab!?') }, false);
	button.addEventListener('grabmove', function(){ console.log('hand grab: move') }, false);
	button.addEventListener('grabend', function(){ console.log('hand grab: end') }, false);

Supported Platforms
-------------------

- Android
- iOS
- Windows 8
- Blackberry 10

### Android

http://developer.android.com/reference/android/view/GestureDetector.html

### Windows 8

http://msdn.microsoft.com/library/windows/apps/BR241937

var gestureRecognizer = new Windows.UI.Input.GestureRecognizer();

### iOS

http://developer.apple.com/library/ios/documentation/EventHandling/Conceptual/EventHandlingiPhoneOS/GestureRecognizer_basics/GestureRecognizer_basics.html

### Blackberry 10

http://developer.blackberry.com/native/reference/cascades/bb__cascades__gesturehandler.html?f=gesture

Notes
-------------------

Would be nice to look at performance with pointer events & replacing with native gesture detection benchmarks:

http://www.w3.org/TR/pointerevents/
