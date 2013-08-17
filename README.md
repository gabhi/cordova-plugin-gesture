cordova-plugin-gesture
----------------------------

### Problem

A typical 'first dev experience' with HTML 5 is negative for 1 main reason: performance.

That's still going to a challenge for mobile until browsers get better. 
The fun part about 'hybrid' development is you can explore combining native code (java, objective c, ...) and JavaScript & HTML5.

The problem we'll try to solve is how user 'touch responsiveness' can be quite slow and more importantly iconsistent across different devices & platforms.

Listening for the 'onclick' event for example has a delay 300ms (hardcoded). You need to listen to 'ontouchstart' and 'ontouchend' and implement your own 'tap' event. Lots of frameworks in JavaScript do this for you but it's still sloow and thanks to Android fragmentation and bugs, you can get 'missed' events.

### Solution

The objective of this 'hack' is to standardize a 'tap' event natively using gesture recognizers:
http://msdn.microsoft.com/library/windows/apps/BR241937
http://developer.apple.com/library/ios/#documentation/EventHandling/Conceptual/EventHandlingiPhoneOS/GestureRecognizer_basics/GestureRecognizer_basics.html
http://developer.android.com/reference/android/view/GestureDetector.html

The idea is to register the gesture handler from javascript:
window.plugins.gesture.register('tap');

The native side would execute JavaScript like this ~
javascript:window.plugins.gesture.onGesture('tap', 400, 300); 
javascript:window.plugins.gesture.onGesture('slide', 400, 300);
 
onGesture() would use a combination of document.elementFromPoint() and document.createEvent() to initiate events on the DOM node:
https://developer.mozilla.org/en-US/docs/DOM/document.createEvent

The purpose of this would be to have a consistent set of gesture recognition across different devices instead of having javascript code in the 'WebView' trying to do gesture detection.


