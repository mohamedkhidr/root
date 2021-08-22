package com.gokul.root;
import androidx.annotation.NonNull;
import java.util.List;
import com.stericson.RootTools.RootTools;
import com.topjohnwu.superuser.Shell;
import android.os.Handler;
import com.topjohnwu.superuser.ShellUtils;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** RootPlugin */
public class RootPlugin implements FlutterPlugin, MethodCallHandler {

  private MethodChannel methodChannel;
  private List<String> resultText;
  private String command;
  private StringBuilder stringBuilder;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "root");
    methodChannel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call,@NonNull Result result) {
    new Handler().post(new Runnable(){

            @Override
            public void run() {
    if (call.method.equals("ExecuteCommand")) {
       command=call.argument("cmd");
       resultText=Shell.sh(command).exec().getOut();
       stringBuilder=new StringBuilder();
      for(String data : resultText){
        stringBuilder.append(data);
        stringBuilder.append("\n");
       }
      result.success(String.format("%s",stringBuilder));
    } else if(call.method.equals("isRooted")){
      result.success(isRooted());
    } else{
      result.notImplemented();
    }
                }
        });


  }
  private boolean isRooted() {
    return RootTools.isAccessGiven();
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }
}
