package org.apache.cordova.core;

import org.apache.cordova.LOG;
import org.hackmtl.phonegap.gestures.TestWebView;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class TapListener extends GestureDetector.SimpleOnGestureListener implements TestWebViewGesture {
	    private static String DEBUG_TAG = "Gesture:tap"; 
		private TestWebView view;

		public void setWebView(TestWebView view){
			this.view = view;
		}

	    @Override
	    public boolean onSingleTapUp(MotionEvent event)
	    {
			JSONObject json = new JSONObject();
			this._sendEvent("tap", event, json);

	        return true;
	    }

	    @Override
	    public boolean onFling(MotionEvent event1, MotionEvent event2, 
	            float velocityX, float velocityY)
	    {
	        LOG.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
	        return true;
	    }
	    
	    protected void _sendEvent(final String name, MotionEvent event, JSONObject json)
	    {
	    	JSONObject coords = new JSONObject();
			try {
				coords.put("x", event.getX());
				coords.put("y", event.getY());
				json.put("coords", coords);

				final String data = json.toString();

				view.sendJavascript("cordova.gesture.onGesture('"+ name +"', "+ data +")");				

	    	} catch (JSONException e) {
	    	}
	    } 
	}

