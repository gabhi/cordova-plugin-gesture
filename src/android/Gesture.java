/*
       Licensed to the Apache Software Foundation (ASF) under one
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
*/
package org.apache.cordova.core; // use core?

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Build;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.apache.cordova.FileHelper;
import org.apache.cordova.DirectoryManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Gesture extends CordovaPlugin {

    private static final String LOG_TAG = "Gesture";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		// allow registration if gesture from javascript? could be nice if this fails, means missing plugin or not support
		// could fallback on something else
        if (action.equals("registerGesture")) {
			this.registerGesture(args.getString(0));
			// @todo: error callback on failure
			
			JSONObject obj = new JSONObject();
			obj.put("foo", "bar"); // need anything here?
            callbackContext.success(obj);

        } else if (action.equals("disableEvents")) {
           this.activateNativeEvents(false);
        } else if (action.equals("enableEvents")) {
           this.activateNativeEvents(true);
        } else {
            return false;
        }

        return true;
    }

	public void activateNativeEvents(boolean yesNo) {
	
		// TODO: perf optimization to disable any native handling, take over control of touch screen!
		// Good for only passing events directly to the DOM
		if(yesNo) {
			cordova.webview.disableNativeEvents(true);
		} else {
			cordova.webview.disableNativeEvents(false);
		}
		
		return true;
		
	}
	
	/**
     * Register a standardized gesture handler.
	 * ('tap', 'swipe', 'handgrab', ...?) which basic gestures to standardize?
	 *
     * @param name The name of the gesture handler
     * @param config Allow passing a JSON array of configuration parameters that we pass to the GestureHandler? (e.g. ms in between touch start & end?)
	 *
     * @throws GestureInvalidExpection
     */
   public void registerGestureHandler(String name) {
		// TODO: array of valid gesture handlers available
		GestureDetector gesture;
		
		// if(!exists)
		//   throw new GestureInvalidExpection("The gesture "+name+" is not available");
		
		// extend cordova webview, latest version?
		// cordova.webview.registerGestureHandler(name, gesture);
		
		LOG.d(LOG_TAG, "Registered event handler " + name);
		
		return true;
		
	}
}

class GestureInvalidExpection extends Exception {}
