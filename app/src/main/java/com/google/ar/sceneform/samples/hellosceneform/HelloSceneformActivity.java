/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.sceneform.samples.hellosceneform;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gcssloop.widget.RockerView;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import com.gcssloop.widget.RockerView;

import java.util.Random;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class HelloSceneformActivity extends AppCompatActivity {
  private static final String TAG = HelloSceneformActivity.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private ArFragment arFragment;
  private ModelRenderable andyRenderable; // 0
  private ModelRenderable twobRenderable; // 4
  private ModelRenderable barRenderable;  // 2
  private ModelRenderable houseRenderable; //3
  private ModelRenderable oakRenderable; // 1
  private ModelRenderable treeRenderable; // 1
  private ModelRenderable coneRenderable; // 3
  private ModelRenderable lightRenderable; // 3

  private int state = 0;

  private Button andyBut; // 0
  private Button trafficBut; // 2
  private Button treeBut; // 1
  private Button iibBut; // 4
  private Button houseBut; // 3

  private MyControler controler;
  private RockerView rocker;
  private Thread cThread;
  public TextView clientStatus;

  @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

    setContentView(R.layout.activity_ux);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
    rocker = (RockerView) findViewById(R.id.rock);
    clientStatus = (Button) findViewById(R.id.blue_connect);
    andyBut = findViewById(R.id.andy);
    treeBut = findViewById(R.id.tree);
    trafficBut = findViewById(R.id.traffic);
    houseBut = findViewById(R.id.house);
    iibBut = findViewById(R.id.iib);
    controler = new MyControler();

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.andy)
        .build()
        .thenAccept(renderable -> andyRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

      ModelRenderable.builder()
              .setSource(this, R.raw.iib)
              .build()
              .thenAccept(renderable -> twobRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.barriers)
              .build()
              .thenAccept(renderable -> barRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.house)
              .build()
              .thenAccept(renderable -> houseRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.oaktree)
              .build()
              .thenAccept(renderable -> oakRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.poplartree)
              .build()
              .thenAccept(renderable -> treeRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.tfcone2)
              .build()
              .thenAccept(renderable -> coneRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });
      ModelRenderable.builder()
              .setSource(this, R.raw.tflight)
              .build()
              .thenAccept(renderable -> lightRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });


      rocker.setListener(new RockerView.RockerListener() {
          @Override
          public void callback(int eventType, int currentAngle, float currentDistance) {
              switch (eventType) {
                  case RockerView.EVENT_ACTION:
                      if (currentAngle > 315 || 0 < currentAngle && currentAngle <= 45) {
                          MyControler.bluetoothmsg = "d";
                      } else if (45 < currentAngle && currentAngle < 135) {
                          MyControler.bluetoothmsg = "w";
                      } else if (135 <= currentAngle && currentAngle < 225) {
                          MyControler.bluetoothmsg = "a";
                      } else if (225 <= currentAngle && currentAngle <= 315) {
                          MyControler.bluetoothmsg = "s";
                      } else {
                          MyControler.bluetoothmsg = "x";
                      }
                      break;
                  case RockerView.EVENT_CLOCK:
                      break;
              }
          }
      });

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (andyRenderable == null) {
            return;
          }

          // Create the Anchor.
          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());

          // Create the transformable andy and add it to the anchor.
          TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
          node.setParent(anchorNode);
          if (state==0)
              node.setRenderable(andyRenderable);
          else if ( state==1 ) {
              final int rand = new Random().nextInt(2);
              if (rand==0)
                  node.setRenderable(treeRenderable);
              else
                  node.setRenderable(oakRenderable);
          }
          else if ( state==2){
              final int rand = new Random().nextInt(3);
              if (rand==0)
                  node.setRenderable(coneRenderable);
              else if(rand==1)
                  node.setRenderable(lightRenderable);
              else
                  node.setRenderable(barRenderable);
          }
          else if (state==3)
              node.setRenderable(houseRenderable);
          else if (state==4)
              node.setRenderable(twobRenderable);

          node.select();
        });

      cThread = new Thread(controler);
//        cThread.start();

      clientStatus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (controler.isConnected()) {
                  clientStatus.setText("Connected");
                  if (!cThread.isAlive())
                      cThread.start();
              } else {
                  controler.Connect();
                  clientStatus.setText("Unconnected");
//                    cThread.start();
              }
          }
      });
      andyBut.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              state = 0;
          }
      });
      treeBut.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              state = 1;
          }
      });
      trafficBut.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              state = 2;
          }
      });
      houseBut.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              state = 3;
          }
      });
      iibBut.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              state = 4;
          }
      });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }
}
