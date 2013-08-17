---
license: Licensed to the Apache Software Foundation (ASF) under one
         or more contributor license agreements.  See the NOTICE file
         distributed with this work for additional information
         regarding copyright ownership.  The ASF licenses this file
         to you under the Apache License, Version 2.0 (the
         "License"); you may not use this file except in compliance
         with the License.  You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

         Unless required by applicable law or agreed to in writing,
         software distributed under the License is distributed on an
         "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
         KIND, either express or implied.  See the License for the
         specific language governing permissions and limitations
         under the License.
---

Register a gesture handler 
=====

The idea here is to allow to register gesture handlers from javascript. 
Only standardized gestures (tap, swipe, ...?) would be supported to provide a consistent experience across many platforms.

A custom gesture handler (non-standard) could also be registered on the native side to more advanced use cases.

// Registering gesture from JavaScript
window.plugin.gesture.register('tap'); // standard gesture
document.addEventListener('tap', function(){ alert('tap') }, false);

// Making a custom gesture available in Android 
webView.registerGesture('grab', new CustomGestureHandGrab);

// In JavaScript
window.plugin.gesture.register('grab');

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

Java

### Windows 8

http://msdn.microsoft.com/library/windows/apps/BR241937

Use javascript api, if enough time do a simple 'tap' event.

var gestureRecognizer = new Windows.UI.Input.GestureRecognizer();

### iOS

Find developer to write code for this

http://developer.apple.com/library/ios/documentation/EventHandling/Conceptual/EventHandlingiPhoneOS/GestureRecognizer_basics/GestureRecognizer_basics.html

### Blackberry 10

Find developer to write code for this

http://developer.blackberry.com/native/reference/cascades/bb__cascades__gesturehandler.html?f=gesture

Notes
-------------------

Would be nice to look at performance with pointer events & replacing with native gesture detection benchmarks:

http://www.w3.org/TR/pointerevents/
