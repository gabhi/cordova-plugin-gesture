cordova-plugin-gesture
----------------------------

### Problem

With 'hybrid' development, you can explore combining native code (java, objective c, ...) and JavaScript & HTML5.

When using [Cordova/PhoneGap][1], we'll try to hack how user 'touch responsiveness' can be slow and more importantly inconsistent across different devices & platforms.

Listening for the 'onclick' event for example has a delay 300ms (hardcoded). You need to listen to 'ontouchstart', 'ontouchend' and implement your own 'tap' event. Lots of frameworks in JavaScript do this for you but it's still sloow and thanks to Android fragmentation and bugs, you can get 'missed' events.

### Solution

The objective of this 'hack' is to standardize a 'tap' event natively using gesture recognizers:
http://msdn.microsoft.com/library/windows/apps/BR241937
http://developer.apple.com/library/ios/#documentation/EventHandling/Conceptual/EventHandlingiPhoneOS/GestureRecognizer_basics/GestureRecognizer_basics.html
http://developer.android.com/reference/android/view/GestureDetector.html

The idea is to register the gesture handler from javascript:

	cordova.gesture.register('tap');

The native side would execute JavaScript like this ~

	javascript:cordova.gesture.onGesture('tap', {coords: {x: 400, y: 300}}); 
	javascript:cordova.gesture.onGesture('slide', {coords: {x: 400, y: 300}});
 
onGesture() would use a combination of document.elementFromPoint() and document.createEvent() to initiate events on the DOM node:
https://developer.mozilla.org/en-US/docs/DOM/document.createEvent

The purpose of this would be to have a consistent set of gesture recognition across different devices instead of having JavaScript code in the 'WebView' trying to do gesture detection.

A sample cordova 3.0 app to experiment with this plugin is available here:
https://github.com/jbondc/mtlhack-PhoneGap-gesture

[1]: http://cordova.apache.org/  "Cordova/PhoneGap"
