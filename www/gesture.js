/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var exec = require('cordova/exec');

function Gesture() {
}

/**
 * Register a standardized gesture handler.
 *
 * @param {String} name
 * @param {Function} successCB
 * @param {Function} errorCB
 */
Gesture.register = function(name, successCB, errorCB){
    var cb = function(result){
		console.log("GOT result!");
		console.log(result);
		successCB();
	};

	// TODO: allow passing 'options' for the gesture handler?
	// Gesture.register({name: 'tap', 'ms-delay': 30}, successCB, errorCB);
	
	var pluginOptions = [name];
	exec(cb, errorCB, "Gesture", "register", pluginOptions);
};

Gesture.disableEvents = function(yesNo, successCB, errorCB){
    var cb = function(result){
		console.log("GOT result!");
		console.log(result);
		successCB(); // what do we return?
	};

	var pluginOptions = [yesNo];
	exec(cb, errorCB, "Gesture", "disableEvents", pluginOptions);
};


Gesture.onGesture = function(type, info){

	App.log("GOT gesture " + type + " at x: "+ info.coords.x + " y: "+ info.coords.y);
	
	
};

/* 
Need to call this from the native gesture handler...

cordova.gesture.onGesture("tap", x, y);

Use code from:
https://github.com/purplecabbage/cordova-wp7/blob/master/templates/standalone/cordovalib/BrowserMouseHelper.cs

Notes: 
In the code snippet: screen.logicalXDPI is Microsoft only

window.devicePixelRatio is a 'standards-ish' but isn't really reliable. Should we pass pixel density from native to the javascript?
This should probably be a cordova 'core' feature.

http://www.quirksmode.org/blog/archives/2012/06/devicepixelrati.html

(function(win,doc){
   

            var mPro = MouseEvent.prototype;
            var def = Object.defineProperty;
            def( mPro, 'pageX', {
               configurable: true,
               get: function(){ return this.clientX }
            });
            def( mPro, 'pageY', {
               configurable: true,
               get: function(){ return this.clientY }
            });

            win.onNativeMouseEvent = function(type,x,y){
                try {
                    var xMod = screen.logicalXDPI / screen.deviceXDPI;
                    var yMod = screen.logicalYDPI / screen.deviceYDPI;
                    var evt = doc.createEvent('MouseEvents');
                    var xPos =  doc.body.scrollLeft + Math.round(xMod * x);
                    var yPos =  doc.body.scrollTop + Math.round(yMod * y);
                    var element = doc.elementFromPoint(xPos,yPos);
                    
                    evt.initMouseEvent(type, true, true, win, 1, xPos, yPos, xPos, yPos, false, false, false, false, 0, element);
                    evt.timeStamp = +new Date;
                    evt.isCordovaEvent = true;
                    
                    var canceled = element ? !element.dispatchEvent(evt) : !doc.dispatchEvent(evt);
                    return canceled ? 'true' : 'false';
                }
                catch(e) { return e;}
            }
        })(window,document);"
*/



module.exports = new Gesture();
