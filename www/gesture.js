
var exec = require('cordova/exec');

function Gesture() {
	this.disabledEvents = false;
}

/**
 * Register a standardized gesture handler.
 *
 * @param {String} name
 * @param {Function} successCB
 * @param {Function} errorCB
 */
Gesture.prototype.register = function(name, successCB, errorCB){
	var pluginOptions = [name];
	exec(successCB, errorCB, "Gesture", "register", pluginOptions);
};

Gesture.prototype.disableEvents = function(yesNo, successCB, errorCB){
	var pluginOptions = [yesNo];
	this.disabledEvents = yesNo;

	exec(successCB, errorCB, "Gesture", "disableEvents", pluginOptions);
};


Gesture.prototype.onGesture = function(type, info){

	//console.log("GOT gesture " + type + " at x: "+ info.coords.x + " y: "+ info.coords.y);

	try {
		function createEvent(type, xPos, yPos, elm){
			var evt  = doc.createEvent('MouseEvents');
			evt.initMouseEvent(type, true, true, window, 1, xPos, yPos, xPos, yPos, false, false, false, false, 0, elm);
			evt.isCordovaEvent = true;

			return evt;
		};

		var doc = window.document;
		var xMod = 1;
		var yMod = 1;

		/* @TODO: screen.logicalXDPI is Microsoft only

		var xMod = screen.logicalXDPI / screen.deviceXDPI;
		var yMod = screen.logicalYDPI / screen.deviceYDPI;		

		window.devicePixelRatio is a 'standards-ish' but isn't really reliable. Should we pass pixel density from native to the javascript?
		This should probably be a cordova 'core' feature.
		
		http://www.quirksmode.org/blog/archives/2012/06/devicepixelrati.html
		*/

		var xPos = doc.body.scrollLeft + Math.round(xMod * info.coords.x);
		var yPos = doc.body.scrollTop + Math.round(yMod * info.coords.y);
		var elm  = doc.elementFromPoint(xPos, yPos);
		if(elm === null)
			elm = doc;

		var evt  = createEvent(type, xPos, yPos, elm);
		var canceled = elm.dispatchEvent(evt);

		if(this.disabledEvents) {
			// If tap event, create 'click' event
			// Shoudn't this be done natively? perform click? can this done on all platforms?
			if(type === 'tap') {
				elm.dispatchEvent( createEvent('click', xPos, yPos, elm) );
			}
		}

	} catch(e) { 
		console.log(e);
	}
	
};

module.exports = new Gesture();
