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

import android.view.GestureDetector;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.hackmtl.phonegap.gestures.TestWebView;
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
        		this.registerGesture( args.getString(0) );
			} catch(GestureInvalidExpection e) {
				callbackContext.error(e.getMessage());
				return false;
			}

			JSONObject obj = new JSONObject();
            callbackContext.success(obj);

        } else if( action.equals("disableEvents") ) {
           this.disableTouchEvents( args.getBoolean(0) );
        } else {
            return false;
        }

        return true;
    }

	public void disableTouchEvents(boolean yesNo)
	{
		TestWebView view = (TestWebView) webView;
		view.disableTouchEvents(yesNo);
	}

	/**
     * Register a standardized gesture handler.
	 * ('tap', 'swipe', 'handgrab', ...?) which basic gestures to standardize?
	 *
     * @param name The name of the gesture handler
     * @param config Allow passing a JSON array of configuration parameters that we pass to the GestureHandler?
     */
   public void registerGesture(String name) throws GestureInvalidExpection
   {
	   TestWebView view = (TestWebView) webView;
	   GestureDetector gesture;

		if( name.equals("tap") ) {
			TestWebViewGesture listener = new TapListener();
			listener.setWebView(view);

			gesture = new GestureDetector(cordova.getActivity(), listener);
		} else {
			throw new GestureInvalidExpection("The gesture "+name+" is not available");
		}

		// Register touch handler...
		view.registerGestureHandler(name, gesture);

		LOG.d(LOG_TAG, "Registered event handler " + name);
	}
}

class GestureInvalidExpection extends Exception {

	private static final long serialVersionUID = 1L;

	public GestureInvalidExpection(String message) {
		 super(message);
	}
}

interface TestWebViewGesture extends GestureDetector.OnGestureListener {
	public void setWebView(TestWebView view);
}
