package com.gmail.kiyoonko.virms;

import com.google.atap.tangoservice.Tango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Application's entry point where the user gets to select a certain configuration and start the
 * next activity.
 */
public class StartActivity extends Activity implements View.OnClickListener {
    public static final String KEY_MOTIONTRACKING_AUTORECOVER =
            "com.projecttango.experiments.javamotiontracking.useautorecover";
    private ToggleButton mAutoResetButton;
    private Button mStartButton;
    private boolean mUseAutoReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForResult(
                Tango.getRequestPermissionIntent(Tango.PERMISSIONTYPE_MOTION_TRACKING),
                Tango.TANGO_INTENT_ACTIVITYCODE);
        setContentView(R.layout.start);
        this.setTitle(R.string.app_name);
        mAutoResetButton = (ToggleButton) findViewById(R.id.autoresetbutton);
        mStartButton = (Button) findViewById(R.id.startbutton);
        mAutoResetButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mUseAutoReset = mAutoResetButton.isChecked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startbutton:
                startMotionTracking();
                break;
            case R.id.autoresetbutton:
                mUseAutoReset = mAutoResetButton.isChecked();
                break;
        }
    }

    private void startMotionTracking() {
        Intent startmotiontracking = new Intent(this, MotionTrackingActivity.class);
        startmotiontracking.putExtra(KEY_MOTIONTRACKING_AUTORECOVER, mUseAutoReset);
        startActivity(startmotiontracking);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Tango.TANGO_INTENT_ACTIVITYCODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.motiontrackingpermission, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
