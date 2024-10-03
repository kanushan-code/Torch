package com.example.torch;




import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



public class MainActivity extends AppCompatActivity {
    Drawable drawable;
    ImageView imageView,imageView1;
    Button btnFlashLight, btnBlinkFlashLight;
    boolean hasCameraFlash = false;
    private static final int CAMERA_REQUEST = 123;
    private static final int mCAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.Off);
        imageView1 = findViewById(R.id.Img);
        btnFlashLight = findViewById(R.id.btnFlash);
        btnBlinkFlashLight = findViewById(R.id.btnBlink);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);

        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


       btnFlashLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (hasCameraFlash) {
                    if (btnFlashLight.getText().toString().contains("ON")) {
                        btnFlashLight.setText("FLASHLIGHT OFF");
                        btnBlinkFlashLight.setText("BLINK FLASHLIGHT OFF");

                        drawable = getResources().getDrawable(R.drawable.btn_switch_off);
                        Bitmap bitmap =((BitmapDrawable)drawable).getBitmap();
                        imageView.setImageBitmap(bitmap);



                        flashLightOff();

                        Toast.makeText(MainActivity.this, "SEE CAMERA PREVIEW",
                                Toast.LENGTH_SHORT).show();

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, mCAMERA_REQUEST);





                    } else {
                        btnBlinkFlashLight.setText("BLINK FLASHLIGHT ON");
                        btnFlashLight.setText("FLASHLIGHT ON");

                        drawable = getResources().getDrawable(R.drawable.btn_switch_on);
                        Bitmap bitmap =((BitmapDrawable)drawable).getBitmap();
                        imageView.setImageBitmap(bitmap);


                        flashLightOn();








                    }

                } else {
                    Toast.makeText(MainActivity.this, "Flash Unavailable on your device",
                            Toast.LENGTH_SHORT).show();

                }



            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (hasCameraFlash) {
                    if (btnFlashLight.getText().toString().contains("ON")) {
                        btnFlashLight.setText("FLASHLIGHT OFF");
                        btnBlinkFlashLight.setText("BLINK FLASHLIGHT OFF");

                        drawable = getResources().getDrawable(R.drawable.btn_switch_off);
                        Bitmap bitmap =((BitmapDrawable)drawable).getBitmap();
                        imageView.setImageBitmap(bitmap);



                        flashLightOff();

                        Toast.makeText(MainActivity.this, "SEE CAMERA PREVIEW",
                                Toast.LENGTH_SHORT).show();

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, mCAMERA_REQUEST);





                    } else {
                        btnBlinkFlashLight.setText("BLINK FLASHLIGHT ON");
                        btnFlashLight.setText("FLASHLIGHT ON");

                        drawable = getResources().getDrawable(R.drawable.btn_switch_on);
                        Bitmap bitmap =((BitmapDrawable)drawable).getBitmap();
                        imageView.setImageBitmap(bitmap);


                        flashLightOn();








                    }

                } else {
                    Toast.makeText(MainActivity.this, "Flash Unavailable on your device",
                            Toast.LENGTH_SHORT).show();

                }



            }
        });



        btnBlinkFlashLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (btnFlashLight.getText().toString().contains("ON")) {
                    blinkFlash();
                } else {
                    Toast.makeText(MainActivity.this, "Click FlashLight button",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });






    }

        @RequiresApi(api = Build.VERSION_CODES.M)
           private void flashLightOn() {
               CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

               try {
                   String camerald = cameraManager.getCameraIdList()[0];
                   cameraManager.setTorchMode(camerald, true);

               }
               catch (CameraAccessException e) {

               }
           }




       @RequiresApi(api = Build.VERSION_CODES.M)
           private void flashLightOff() {
           CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                String camerald = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camerald,false);
            }
            catch (CameraAccessException e) {

            }
         }



    @RequiresApi(api = Build.VERSION_CODES.M)
      private void blinkFlash() {
          CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
          String myString = "0101010101";
          long blinkDelay = 50; //Delay in ms
          for (int i = 0; i < myString.length(); i++) {
              if (myString.charAt(i) == '1') {
                  try {
                      String camerald = cameraManager.getCameraIdList()[0];
                      cameraManager.setTorchMode(camerald, true);
                  } catch (CameraAccessException e) {

                  }
              } else {
                  try {
                      String camerald = cameraManager.getCameraIdList()[0];
                      cameraManager.setTorchMode(camerald, false);
                  } catch (CameraAccessException e) {
                  }
              }
              try {
                  Thread.sleep(blinkDelay);
              } catch (InterruptedException e) {
                  e.printStackTrace();

              }
          }
      }






    public void onRequestPermissonsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode,permissions,grantResults);
       switch (requestCode) {
           case CAMERA_REQUEST:
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   hasCameraFlash = getPackageManager().
                           hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

               }
               else {
                   btnFlashLight.setEnabled(false);
                   btnBlinkFlashLight.setEnabled(false);
                   Toast.makeText(MainActivity.this,"Permission Denied For the camera", Toast.LENGTH_SHORT).show();
               }
               break;
       }
    }
}
