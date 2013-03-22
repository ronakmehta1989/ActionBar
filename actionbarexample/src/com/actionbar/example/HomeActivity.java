package com.actionbar.example;

import com.actionbar.ActionBar;
import com.actionbar.ActionBar.*;
import com.actionbar.*;
import com.actionbar.Menu.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.actionbar.Toast;
import android.app.*;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import android.content.*;

public class HomeActivity extends Activity implements OnActionClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		
        actionBar.setTitle("Title: Home");

        final Action shareAction = new IntentAction(this, createShareIntent(), R.drawable.ic_share);
        actionBar.addAction(shareAction);
        final Action otherAction = new IntentAction(this, new Intent(this, OtherActivity.class), R.drawable.ic_forward);
        actionBar.addAction(otherAction);
        actionBar.getMenu();
		/*actionBar.getMenu().addItem(new MenuItemButton() {
				public void onClick(View w)
				{
					Toast.makeText(getApplicationContext(),"Clicked!",Toast.LENGTH_SHORT).show();
				}
				public String getName()
				{
					return "Test";
				}
		});*/

        Button startProgress = (Button) findViewById(R.id.start_progress);
        startProgress.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					actionBar.setProgressBarVisibility(View.VISIBLE);
				}
			});

        Button stopProgress = (Button) findViewById(R.id.stop_progress);
        stopProgress.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					actionBar.setProgressBarVisibility(View.GONE);
				}
			});

        Button removeActions = (Button) findViewById(R.id.remove_actions);
        removeActions.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.removeAllActions();
				}
			});

        Button addAction = (Button) findViewById(R.id.add_action);
        addAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.addAction(new Action() {
							@Override
							public void performAction(View view)
							{
								Toast.makeText(HomeActivity.this, "Added action", Toast.LENGTH_SHORT).show();
							}
							@Override
							public int getDrawable()
							{
								return R.drawable.ic_settings;
							}
						});
				}
			});

        Button removeAction = (Button) findViewById(R.id.remove_action);
        removeAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					int actionCount = actionBar.getActionCount();
					actionBar.removeActionAt(actionCount - 1);
				}
			});

        Button removeShareAction = (Button) findViewById(R.id.remove_share_action);
        removeShareAction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.removeAction(shareAction);
				}
			});
		Button addSAction = (Button) findViewById(R. id . add_search_action);
		addSAction. setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.addAction(new SearchAction(HomeActivity.this, android.R.drawable.ic_menu_search));
				}
			});
		Button addDAction = (Button) findViewById(R. id . add_dialog_action);
		addDAction. setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.addAction(new DialogAction(HomeActivity.this, 0, android.R.drawable.ic_menu_info_details));
				}
			});
		Button addIAction = (Button) findViewById(R. id . add_inter_action);
		addIAction. setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					actionBar.addAction(new InterfaceAction(HomeActivity.this, android.R.drawable.ic_menu_view));
				}
			});
		Button setTitle = (Button) findViewById(R. id . set_title);
		setTitle. setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					AlertDialog.Builder build = new AlertDialog.Builder(HomeActivity.this);
					build.setTitle("Enter:");
					LayoutInflater inflater = getLayoutInflater();
					View dialoglayout = inflater.inflate(R.layout.title_alert, (ViewGroup) getCurrentFocus());
					final EditText input = (EditText)dialoglayout.findViewById(R.id.new_title);
					build.setView(dialoglayout);
					build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface p1, int p2)
							{
								actionBar.setTitle(input.getText().toString());
							}
					});
					build.show();
				}
			});
		Button setSubTitle = (Button) findViewById(R. id . set_subtitle);
		setSubTitle. setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view)
				{
					AlertDialog.Builder build = new AlertDialog.Builder(HomeActivity.this);
					build.setTitle("Enter:");
					LayoutInflater inflater = getLayoutInflater();
					View dialoglayout = inflater.inflate(R.layout.title_alert, (ViewGroup) getCurrentFocus());
					final EditText input = (EditText)dialoglayout.findViewById(R.id.new_title);
					build.setView(dialoglayout);
					build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface p1, int p2)
							{
								actionBar.setSubTitle(input.getText().toString());
							}
						});
					build.show();
				}
			});
    }

    public static Intent createIntent(Context context)
	{
        Intent i = new Intent(context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    private Intent createShareIntent()
	{
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "ActionBarExtended - small and easy framework for new design of Android apps! github.com/STALKER2010/ActionBar");
        return Intent.createChooser(intent, "Share");
    }
	@Override
	public void onActionClick(Action a)
	{
		Toast. makeText(this, "The InterfaceAction has been received" , Toast. LENGTH_SHORT). show();
	}
	@Override
	public Dialog onCreateDialog(int id)
	{
		AlertDialog. Builder b = new AlertDialog. Builder(this);
		b. setMessage("The DialogAction has been received. Hit back to hide this dialog.")
			. setTitle("DialogAction");
		return b. create();
	}
}
