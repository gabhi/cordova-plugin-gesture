package org.apache.cordova.core;

import org.apache.cordova.LOG;
import org.hackmtl.phonegap.gestures.TestWebView;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class TapListener extends GestureDetector.SimpleOnGestureListener {
	    private static String DEBUG_TAG = "Gesture:tap"; 
	    
	    private static String name = "tap";
	    
		public TestWebView webView;
		
	    @Override
	    public boolean onSingleTapConfirmed(MotionEvent event) { 

	    	// get coords from event
	    	int x = 0;
			int y = 0;
			
	    	String jsonInfo = "{\"coords\":{\"x\":"+x+", \"y\":"+y+"}}";
	    	
	        LOG.d(DEBUG_TAG,"tap: " + event.toString()); 
	        
			// Pass this to JavaScript, will create a DOM event
	        webView.loadUrl("javascript:cordova.gesture.onGesture('"+ name +"', "+ jsonInfo +")");
	        
	        return true;
	    }

	    @Override
	    public boolean onFling(MotionEvent event1, MotionEvent event2, 
	            float velocityX, float velocityY) {
	        LOG.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
	        return true;
	    }
	}

