package com.trigger.openglfirst;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.rajawali3d.math.Quaternion;
import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mySurfaceView;
    private Torus torus;

    Renderer renderer;
    private TextView tvStatus;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.b_log_1);
        Button button2 = findViewById(R.id.b_log_2);
        Button button3 = findViewById(R.id.b_log_3);
        tvStatus = findViewById(R.id.tv_status);

        button1.setOnClickListener(v -> loadLog("log1"));
        button2.setOnClickListener(v -> loadLog("log2"));
        button3.setOnClickListener(v -> loadLog("log3"));

        final RajawaliSurfaceView surface = findViewById(R.id.my_surface_view);//new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_CONTINUOUSLY);

        // Add mSurface to your root view
        //addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        renderer = new Renderer(this);
        surface.setSurfaceRenderer(renderer);



//        mySurfaceView = findViewById(R.id.my_surface_view);
//
//        mySurfaceView.setEGLContextClientVersion(2);
//
//        mySurfaceView.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//                mySurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//                try {
//                    torus = new Torus(getApplicationContext());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0,0, width, height);
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                torus.draw();
//            }
//        });
    }

    private void loadLog(String logFile) {
        if(timer != null) {
            timer.cancel();
        }
        renderer.createModel();

        try {
            InputStream in_s = getResources().openRawResource(
                    getResources().getIdentifier(logFile,"raw", getPackageName()));

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            final int[] angle = {0};
            String json = new String(b);
            json = json.replace("}{", "},{");
            json = "[" + json + "]";
            final Orientation[] values = new Gson().fromJson(json, Orientation[].class);

            final int[] counter = {1};
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(counter[0] >= values.length - 1) return;

                    tvStatus.post(() -> tvStatus.setText(counter[0] + ""));

                    counter[0]++;
                    Quaternion quaternion = new Quaternion();
                    quaternion.fromEuler(values[counter[0]].getYAW() - values[counter[0] - 1].getYAW(),
                            values[counter[0]].getPITCH() - values[counter[0] - 1].getPITCH(),
                            values[counter[0]].getROLL() - values[counter[0] - 1].getROLL()
                    );
                    renderer.pcbBox.rotate(quaternion);
                }
            }, 0, 20);
        } catch (Exception e) {
            // e.printStackTrace();
            Toast.makeText(this, "Couldn't load the file", Toast.LENGTH_SHORT).show();
        }
    }
}
