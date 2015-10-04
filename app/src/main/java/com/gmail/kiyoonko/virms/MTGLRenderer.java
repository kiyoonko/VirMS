package com.gmail.kiyoonko.virms;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.gmail.kiyoonko.virms.TangoUtils.app.src.main.java.com.projecttango.tangoutils.Renderer;
import com.gmail.kiyoonko.virms.TangoUtils.app.src.main.java.com.projecttango.tangoutils.renderables.CameraFrustum;
import com.gmail.kiyoonko.virms.TangoUtils.app.src.main.java.com.projecttango.tangoutils.renderables.CameraFrustumAndAxis;
import com.gmail.kiyoonko.virms.TangoUtils.app.src.main.java.com.projecttango.tangoutils.renderables.Grid;
import com.gmail.kiyoonko.virms.TangoUtils.app.src.main.java.com.projecttango.tangoutils.renderables.Trajectory;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiyoon on 2015-10-03.
 */

public class MTGLRenderer extends Renderer implements GLSurfaceView.Renderer {
    private Trajectory mTrajectory;
    private CameraFrustum mCameraFrustum;
    private CameraFrustumAndAxis mCameraFrustumAndAxis;
    private Grid mFloorGrid;
    private boolean mIsValid = false;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set background color and enable depth testing
        GLES20.glClearColor(1f, 1f, 1f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // resetModelMatCalculator();
        mCameraFrustum = new CameraFrustum();
        mFloorGrid = new Grid();
        mCameraFrustumAndAxis = new CameraFrustumAndAxis();
        mTrajectory = new Trajectory(3);

        // Construct the initial view matrix
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(mViewMatrix, 0, 5f, 5f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);
        mCameraFrustumAndAxis.setModelMatrix(getModelMatCalculator().getModelMatrix());
        mIsValid = true;

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mCameraAspect = (float) width / height;
        Matrix.perspectiveM(mProjectionMatrix, 0, THIRD_PERSON_FOV, mCameraAspect, CAMERA_NEAR,
                CAMERA_FAR);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (MotionTrackingActivity.sharedLock) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            mTrajectory.draw(getViewMatrix(), mProjectionMatrix);
            mFloorGrid.draw(getViewMatrix(), mProjectionMatrix);
            mCameraFrustumAndAxis.draw(getViewMatrix(), mProjectionMatrix);
        }
        updateViewMatrix();
    }

    public CameraFrustum getCameraFrustum() {
        return mCameraFrustum;
    }

    public CameraFrustumAndAxis getCameraFrustumAndAxis() {
        return mCameraFrustumAndAxis;
    }

    public Trajectory getTrajectory() {
        return mTrajectory;
    }

    public boolean isValid(){
        return mIsValid;
    }
}
