package com.fruit.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {


  private static final String TAG = "MainActivity";
  private static final int RC_ACCESS_COARSE_LOCATION = 1;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button btnSimple = (Button) findViewById(R.id.btnSimple);
    btnSimple.setOnClickListener(this);

    Button btnListener = (Button) findViewById(R.id.btnListener);
    btnListener.setOnClickListener(this);

    Button btnAutoConnect = (Button) findViewById(R.id.btnAutoConnect);
    btnAutoConnect.setOnClickListener(this);

    Button btnDeviceList = (Button) findViewById(R.id.btnDeviceList);
    btnDeviceList.setOnClickListener(this);

    Button btnTerminal = (Button) findViewById(R.id.btnTerminal);
    btnTerminal.setOnClickListener(this);

    String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
    if (!EasyPermissions.hasPermissions(this, perms)) {
      EasyPermissions.requestPermissions(this, "连接蓝牙设备需要扫描蓝牙设备权限", RC_ACCESS_COARSE_LOCATION, perms);
    }

  }

  public void onClick(View v) {
    int id = v.getId();
    Intent intent;
    switch (id) {
      case R.id.btnSimple:
        intent = new Intent(getApplicationContext(), SimpleActivity.class);
        startActivity(intent);
        break;
      case R.id.btnListener:
        intent = new Intent(getApplicationContext(), ListenerActivity.class);
        startActivity(intent);
        break;
      case R.id.btnAutoConnect:
        intent = new Intent(getApplicationContext(), AutoConnectActivity.class);
        startActivity(intent);
        break;
      case R.id.btnDeviceList:
        intent = new Intent(getApplicationContext(), DeviceListActivity.class);
        startActivity(intent);
        break;
      case R.id.btnTerminal:
        intent = new Intent(getApplicationContext(), TerminalActivity.class);
        startActivity(intent);
        break;
    }
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {

  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Toast.makeText(this, "onPermissionsDenied", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // Forward results to EasyPermissions
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
      // Do something after user returned from app settings screen, like showing a Toast.
      Toast.makeText(this, "returned from app settings", Toast.LENGTH_SHORT)
          .show();
    }
  }

}
