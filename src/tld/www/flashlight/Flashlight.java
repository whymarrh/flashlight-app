package tld.www.flashlight;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Flashlight extends Activity {

  private Window window = null;
  private WindowManager.LayoutParams wmlp = null;
  private Camera camera = null;
  private Camera.Parameters camera_parameters = null;

  private void setScreenBrightness(float brightness) {
    window = getWindow();
    wmlp = window.getAttributes();
    wmlp.screenBrightness = brightness / 100.0f;
    window.setAttributes(wmlp);
  }
  private void activateCameraTorch() {
    boolean hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    camera = Camera.open();
    if (camera == null || !hasFlash) return;
    camera_parameters = camera.getParameters();
    camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
    camera.setParameters(camera_parameters);
    camera.startPreview();
  }
  private void releaseCameraTorch() {
    if (camera == null) return;
    camera.stopPreview();
    camera.release();
  }
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    activateCameraTorch();
    setScreenBrightness(100.0f);
  }
  @Override public void onPause() {
    super.onPause();
    releaseCameraTorch();
  }

}
