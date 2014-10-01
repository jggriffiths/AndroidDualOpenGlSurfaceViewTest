package net.deadlights.opengltest;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.ViewGroup;


public class MyActivity extends Activity {

    CameraView cView;
    GLSurfaceView sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);
        cView = new CameraView(this);
        sView = new GLSurfaceView(this);
        sView.setEGLContextClientVersion(1);
        sView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        sView.setRenderer(new SquareRender());
        sView.getHolder().setFormat(PixelFormat.RGBA_8888);
        sView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        sView.setZOrderOnTop(true);
        setContentView(cView);
        //this.addContentView(sView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addContentView(sView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // View
    class CameraView extends GLSurfaceView {
        CameraRender mRenderer;

        CameraView(Context context) {
            super(context);
            mRenderer = new CameraRender(this);
            setEGLContextClientVersion(2);
            setRenderer(mRenderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        public void surfaceCreated(SurfaceHolder holder) {
            super.surfaceCreated(holder);
        }
        public void surfaceDestroyed(SurfaceHolder holder) {
            mRenderer.close();
            super.surfaceDestroyed(holder);
        }
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            super.surfaceChanged(holder, format, w, h);
        }
    }
}
