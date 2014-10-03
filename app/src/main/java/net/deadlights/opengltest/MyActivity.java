package net.deadlights.opengltest;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


public class MyActivity extends Activity implements TextureView.SurfaceTextureListener{

    TextureView cView;
    GLSurfaceView sView;
    Camera _camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetCameraInstance();
        FrameLayout fl = new FrameLayout(this);
        setContentView(fl);

        cView = new TextureView(this);
        cView.setSurfaceTextureListener(this);

        sView = new GLSurfaceView(this);
        sView.setEGLContextClientVersion(1);
        sView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        sView.setRenderer(new SquareRender());
        sView.getHolder().setFormat(PixelFormat.RGBA_8888);
        sView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        sView.setZOrderOnTop(true);

        fl.addView(cView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        fl.addView(sView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        Button b = new Button(this);
        b.setText("Hello");
        b.setGravity(Gravity.CENTER);
        fl.addView(b, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
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

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            _camera.setPreviewTexture(surface);
        }
        catch (Exception e){}
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        _camera.stopPreview();
        _camera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /** A safe way to get an instance of the Camera object. */
    public void GetCameraInstance()
    {
        _camera = null;
        try
        {
            _camera = Camera.open(); // attempt to get a Camera instance
            Camera.Parameters param = _camera.getParameters();
            Camera.Size pSize = param.getPreviewSize();
            param.setPreviewSize(pSize.width, pSize.height);
            param.setFocusMode("continuous-video");
            //Log.i("mr","ssize: "+psize.get(i).width+", "+psize.get(i).height);
            param.set("orientation", "landscape");
            _camera.setParameters(param);
            _camera.startPreview();
        }
        catch (Exception e)
        {
            // Camera is not available (in use or does not exist)
        }
    }
}
