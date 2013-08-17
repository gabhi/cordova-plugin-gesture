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
package org.apache.cordova.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;

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
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
	{
        if( action.equals("register") ) {
			
			try {
        		this.registerGesture(args.getString(0));
			} catch(GestureInvalidExpection e) {
				// @todo: error callback on failure
			}
			
			JSONObject obj = new JSONObject();
			obj.put("foo", "bar"); // need anything here?
            callbackContext.success(obj);

        } else if( action.equals("disableEvents") ) {
           this.activateNativeEvents(false);
        } else if( action.equals("enableEvents") ) {
           this.activateNativeEvents(true);
        } else {
            return false;
        }

        return true;
    }

	public void activateNativeEvents(boolean yesNo)
	{
		TestWebView view = (TestWebView) webView;

		// Good for only passing events directly to the DOM
		if(yesNo) {
			view.disableNativeEvents(true);
		} else {
			view.disableNativeEvents(false);
		}
		
		return true;
		
	}

	/**
     * Register a standardized gesture handler.
	 * ('tap', 'swipe', 'handgrab', ...?) which basic gestures to standardize?
	 *
     * @param name The name of the gesture handler
     * @param config Allow passing a JSON array of configuration parameters that we pass to the GestureHandler?
	 *
     * @throws GestureInvalidExpection
     */
   public void registerGesture(String name)
   {
		GestureDetector gesture;

		switch(name) {
			case "tap" :
				gesture = new GestureDetector(cordova.getActivity(), TapListener);
				break;
			default:
				throw new GestureInvalidExpection("The gesture "+name+" is not available");
		}

		// TestWebView is our extended cordova webview, this will need to back into Cordova
		TestWebView view = (TestWebView) webView;
		view.registerGestureHandler(name, gesture);
		
		LOG.d(LOG_TAG, "Registered event handler " + name);
	}
}

class GestureInvalidExpection extends Exception {}

class TapListener extends GestureDetector.SimpleOnGestureListener {
    private static String DEBUG_TAG = "Gesture:tap"; 
    
    private static String name = "tap";
    
	public CordovaWebView webView;
	
    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) { 

    	// get coords from event
    	int x = 0;
		int y = 0;
		
    	String jsonInfo = "{\"coords\":{\"x\":"+x+", \"y\":"+y+"}}";
    	
        LOG.d(DEBUG_TAG,"tap: " + event.toString()); 
        
		// Pass this to JavaScript, will create a DOM event
        webView.loadUrl("javascript:cordova.onGesture('"+ name +"', "+ jsonInfo +")");
        
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, 
            float velocityX, float velocityY) {
        LOG.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }
}
