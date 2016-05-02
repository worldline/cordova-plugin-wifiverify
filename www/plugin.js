function Plugin(){}
Plugin.wifiverify = function(){
  var onSuccess = function(){};
  var onFail = function(){};
  var content="";
  
  cordova.exec(onSuccess, onFail, 'AlertPlugin', 'alert', [content]);
};
module.exports = Plugin;