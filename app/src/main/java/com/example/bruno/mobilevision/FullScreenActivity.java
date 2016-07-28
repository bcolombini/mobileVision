package com.example.bruno.mobilevision;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class FullScreenActivity extends AppCompatActivity {

    private SurfaceView cameraView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        //Start Mobile Vision
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .build();

        CameraSource.Builder cmSrc = new CameraSource.Builder(getApplicationContext(), barcodeDetector);
        cmSrc = builder.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
                    barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                        @Override
                        public void release() {
                        }

                        @Override
                        public void receiveDetections(Detector.Detections<Barcode> detections) {
                            final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                            if (barcodes.size() != 0) {
//                                Intent rs = new Intent();
//                                rs.putExtra("Codigo",barcodes.valueAt(0).displayValue);
//                                setResult(Activity.RESULT_OK,rs);
//                                finish();
                                Log.d("Tamanho: ", String.valueOf(barcodes.valueAt(0).displayValue.length()));
                            }
                        }
                    });
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.stop();
    }
}
