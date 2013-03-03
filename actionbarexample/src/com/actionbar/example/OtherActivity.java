package com.actionbar.example;

import com.actionbar.ActionBar;
import com.actionbar.ActionBar.AbstractAction;
import com.actionbar.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.actionbar.Toast;

public class OtherActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);

        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        // You can assign the title programmatically by passing a
        // CharSequence or resource id.
        actionBar.setTitle("Second activity");
		actionBar.setSubTitle("Subtitle 2");
        actionBar.setHomeAction(new IntentAction(this, HomeActivity.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.addAction(new IntentAction(this, createShareIntent(), R.drawable.ic_share));
        actionBar.addAction(new ExampleAction());
    }

    private Intent createShareIntent() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "ActionBarExtended - small and easy framework for new design of Android apps! github.com/STALKER2010/ActionBar");
        return Intent.createChooser(intent, "Share");
    }

    private class ExampleAction extends AbstractAction {

        public ExampleAction() {
            super(R.drawable.ic_video);
        }

        @Override
        public void performAction(View view) {
            Toast.makeText(OtherActivity.this,
                    "Example action", Toast.LENGTH_SHORT).show();
        }

    }

}
