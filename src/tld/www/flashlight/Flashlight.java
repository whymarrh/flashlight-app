package tld.www.flashlight;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Flashlight extends Activity {
  private Window window = null;
  private float brightness = 100.0f;
  private WindowManager.LayoutParams wmlp = null;
  private PackageManager pm = null;
  private Camera camera = null;
  private Camera.Parameters camera_parameters = null;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // set the screen brightness to 100%
    window = getWindow();
    wmlp = window.getAttributes();
    wmlp.screenBrightness = brightness / 100.0f;
    window.setAttributes(wmlp);

    // activate the camera's flash
    pm = getPackageManager();
    boolean hasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    if (hasFlash) {
      camera = Camera.open();
      camera_parameters = camera.getParameters();
      camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      camera.setParameters(camera_parameters);
      camera.startPreview();
    }
  }
  @Override public void onPause() {
    super.onPause();
    camera.stopPreview();
    camera.release();
  }
}
