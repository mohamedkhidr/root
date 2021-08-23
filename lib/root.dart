import 'package:flutter/services.dart';

class Root {
  static const MethodChannel _channel = const MethodChannel('root');

// Function to Run shell commands
  static Future<String> exec({String cmd})  {
    return  _channel.invokeMethod('ExecuteCommand', {"cmd": cmd});
  }

// Function to check Root status
  static Future<bool> isRooted()  {
    return _channel.invokeMethod("isRooted");

  }
}
