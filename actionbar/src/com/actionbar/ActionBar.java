/*
 * Copyright (C) 2010 Johan Nilsson <http://markupartist.com>
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

package com.actionbar;

import java.util.LinkedList;

import com.actionbar.R;
import com.actionbar.Toast;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.app.Activity;
import android.util.*;
import android.view.*;

public class ActionBar extends RelativeLayout implements OnClickListener,View.OnLongClickListener
{

	private LayoutInflater mInflater;
    private RelativeLayout mBarView;
    private ImageView mLogoView;
    private View mBackIndicator;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private LinearLayout mActionsView;
    private ImageButton mHomeBtn;
    private RelativeLayout mHomeLayout;
    private ProgressBar mProgress;
	private LinearLayout mTitlesLayout;
	private Menu mMenu;
	/** ActionBar Extended version by STALKER_2010
     */
    public ActionBar(Context context, AttributeSet attrs)
	{
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBarView = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);
        addView(mBarView);
        mLogoView = (ImageView) mBarView.findViewById(R.id.actionbar_home_logo);
        mHomeLayout = (RelativeLayout) mBarView.findViewById(R.id.actionbar_home_bg);
        mHomeBtn = (ImageButton) mBarView.findViewById(R.id.actionbar_home_btn);
        mBackIndicator = mBarView.findViewById(R.id.actionbar_home_is_back);
        mTitleView = (TextView) mBarView.findViewById(R.id.actionbar_title);
        mSubTitleView = (TextView) mBarView.findViewById(R.id.actionbar_subtitle);
        mTitlesLayout = (LinearLayout) mBarView.findViewById(R.id.actionbar_titles);
        mActionsView = (LinearLayout) mBarView.findViewById(R.id.actionbar_actions);
        mProgress = (ProgressBar) mBarView.findViewById(R.id.actionbar_progress);
		mMenu = (com.actionbar.Menu)mBarView.findViewById(R.id.actionbar_more);
        TypedArray a = context.obtainStyledAttributes(attrs,
													  R.styleable.ActionBar);
        CharSequence title = a.getString(R.styleable.ActionBar_actionbar_title);
        if (title != null)
		{
            setTitle(title);
        }
        a.recycle();
		mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 0);
		mSubTitleView.setHeight(0);
    }
	/** Set home button action
     *
     * @param action Action to do onClick
     */
    public void setHomeAction(Action action)
	{
        mHomeBtn.setOnClickListener(this);
        mHomeBtn.setTag(action);
        mHomeBtn.setImageResource(action.getDrawable());
        mHomeLayout.setVisibility(View.VISIBLE);
    }
	/**
     * Hide home button
     */
    public void clearHomeAction()
	{
        mHomeLayout.setVisibility(View.GONE);
    }
    /**
     * Shows the provided logo to the left in the action bar.
     *
     * This is ment to be used instead of the setHomeAction and does not draw
     * a divider to the left of the provided logo.
     *
     * @param resId The drawable resource id
     */
    public void setHomeLogo(int resId)
	{
        // TODO: Add possibility to add an IntentAction as well.
        mLogoView.setImageResource(resId);
        mLogoView.setVisibility(View.VISIBLE);
        mHomeLayout.setVisibility(View.GONE);
    }
    /* Emulating Honeycomb, setdisplayHomeAsUpEnabled takes a boolean
     * and toggles whether the "home" view should have a little triangle
     * indicating "up" */
    public void setDisplayHomeAsUpEnabled(boolean show)
	{
        mBackIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }
	/** Set Action Bar theme
     *
     * @param title New theme
     */
    public void setTheme(int theme)
	{
        //mBarView.setTheme(theme);
    }
	/** Set Action Bar title
     *
     * @param title New title
     */
    public void setTitle(CharSequence title)
	{
        mTitleView.setText(title);
    }
	/** Set Action Bar title
     *
     * @param resId Resource ID of new title
     */
    public void setTitle(int resid)
	{
        mTitleView.setText(resid);
    }

	/** Set Action Bar subtitle
     *
     * @param title New title
     */
    public void setSubTitle(CharSequence title)
	{
		if (title.equals(""))
		{
			mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 0);
			mSubTitleView.setText(" ");
			mSubTitleView.setHeight(0);
		}
		else
		{
			mSubTitleView.setText(title);
			mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
			mSubTitleView.setHeight(28);
		}
    }
	/** Set Action Bar subtitle
     *
     * @param resId Resource ID of new title
     */
    public void setSubTitle(int resid)
	{
		if (resid == 0)
		{
			mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 0);
			mSubTitleView.setText(" ");
			mSubTitleView.setHeight(0);
		}
		else
		{
			mSubTitleView.setText(resid);
			mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
			mSubTitleView.setHeight(28);
		}
    }
    /**
     * Set the enabled state of the progress bar.
     *
     * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
     *   or {@link View#GONE}.
     */
    public void setProgressBarVisibility(int visibility)
	{
        mProgress.setVisibility(visibility);
    }
    /**
     * Returns the visibility status for the progress bar.
     *
     * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
     *   or {@link View#GONE}.
     */
    public int getProgressBarVisibility()
	{
        return mProgress.getVisibility();
    }
    /**
     * Function to set a click listener for Title TextView
     *
     * @param listener the onClickListener
     */
    public void setOnTitleClickListener(OnClickListener listener)
	{
        mTitlesLayout.setOnClickListener(listener);
    }
	/** OnClick listener
     *
     * @param view Clicked view
     */
    @Override
    public void onClick(View view)
	{
        final Object tag = view.getTag();
        if (tag instanceof Action)
		{
            final Action action = (Action) tag;
            action.performAction(view);
        } else if (tag instanceof com.actionbar.Menu) {
			
		}
    }
    /**
     * Adds a list of {@link Action}s.
     * @param actionList the actions to add
     */
    public void addActions(ActionList actionList)
	{
        int actions = actionList.size();
        for (int i = 0; i < actions; i++)
		{
            addAction(actionList.get(i));
        }
    }
    /**
     * Adds a new {@link Action}.
     * @param action the action to add
     */
    public void addAction(Action action)
	{
        final int index = mActionsView.getChildCount()-1;
		Integer size = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth()/105;
		if (index < size - 1)
		{
			addAction(action, index);
		} else {
			final Action thisAction = action;
			mMenu.addItem(new Menu.MenuItem() {
					public void onClick(View w)
					{
						thisAction.performAction(w);
					}
					public String getName()
					{
						return "Menu Item";
					}
					public void setName(String name)
					{
						// TODO: Implement this method
					}
			});
		}
    }
	public Menu getMenu()
	{
        return mMenu;
    }
    /**
     * Adds a new {@link Action} at the specified index.
     * @param action the action to add
     * @param index the position at which to add the action
     */
    public void addAction(Action action, int index)
	{
        mActionsView.addView(inflateAction(action), index);
    }
    /**
     * Removes all action views from this action bar
     */
    public void removeAllActions()
	{
        mActionsView.removeAllViews();
    }
    /**
     * Remove a action from the action bar.
     * @param index position of action to remove
     */
    public void removeActionAt(int index)
	{
		try
		{
			mActionsView.removeViewAt(index-1);
		}
		catch (Exception e)
		{
			Toast.makeText(getContext(), "Can't remove action!", Toast.LENGTH_SHORT).show();
		}
    }
	/**
     * Change the visibility of an action
     * @param action
     * @param visibility
     */
    public void setActionVisibility(Action action, int visibility)
	{
		int childCount = mActionsView.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			View view = mActionsView.getChildAt(i);
			if (view != null)
			{
				final Object tag = view.getTag();
				if (tag instanceof Action && tag.equals(action))
				{
					view.setVisibility(visibility);
					break;
				}
			}
		}
	}
    /**
     * Remove a action from the action bar.
     * @param action The action to remove
     */
    public void removeAction(Action action)
	{
        int childCount = mActionsView.getChildCount();
        for (int i = 0; i < childCount; i++)
		{
            View view = mActionsView.getChildAt(i);
            if (view != null)
			{
                final Object tag = view.getTag();
                if (tag instanceof Action && tag.equals(action))
				{
                    mActionsView.removeView(view);
                }
            }
        }
    }
    /**
     * Returns the number of actions currently registered with the action bar.
     * @return action count
     */
    public int getActionCount()
	{
        return mActionsView.getChildCount();
    }
	/** On Long Click listener
     *
     * @param view Clicked view
     */
	@Override
	public boolean onLongClick(View view)
	{
		final Object tag = view.getTag();
		if (tag instanceof ActionPlus)
		{
			final ActionPlus action = (ActionPlus) tag;
			action.performLongClickAction(view);
			return true;
		}
		return false;
	}
    /**
     * Inflates a {@link View} with the given {@link Action}.
     * @param action the action to inflate
     * @return a view
     */
	private View inflateAction(Action action)
	{
		//Toast.makeText(getContext(),action.toString(),Toast.LENGTH_SHORT).show();
		View view = mInflater.inflate(R.layout.actionbar_item, mActionsView, false);
		ImageButton labelView =
			(ImageButton) view.findViewById(R.id.actionbar_item);
		labelView.setImageResource(action.getDrawable());
		if (action instanceof ActionPlus)
		{
			labelView.setLongClickable(true);
			labelView.setOnLongClickListener(this);
		}
		view.setTag(action);
		view.setOnClickListener(this);
		return view;
	}
    /**
     * A {@link LinkedList} that holds a list of {@link Action}s.
     */
    public static class ActionList extends LinkedList<Action>
	{
		private static final long serialVersionUID = -7639253919045641775L;
    }
    /**
     * Definition of an action that could be performed, along with a icon to
     * show.
     */
    public interface Action
	{
        public int getDrawable();
        public void performAction(View view);
    }
	/** Action with long press listener
     */
	public interface ActionPlus extends Action
	{
		public void performLongClickAction(View
										   pView);
	}
	/** Abstract action
     */
    public static abstract class AbstractAction implements Action
	{
        final private int mDrawable;
		final private String mName;
        public AbstractAction(int drawable, String name)
		{
            mDrawable = drawable;
			mName = name;
        }
        public AbstractAction(int drawable)
		{
            mDrawable = drawable;
			mName = "";
        }
        @Override
        public int getDrawable()
		{
            return mDrawable;
        }
    }
	/** Action with intent
     */
    public static class IntentAction extends AbstractAction
	{
        private Context mContext;
        private Intent mIntent;
		private String mName;
        public IntentAction(Context context, Intent intent, int drawable)
		{
            super(drawable);
            mContext = context;
            mIntent = intent;
        }
        public IntentAction(Context context, Intent intent, int drawable, String name)
		{
            super(drawable);
            mContext = context;
            mIntent = intent;
			mName = name;
        }
        @Override
        public void performAction(View view)
		{
            try
			{
				mContext.startActivity(mIntent);
            }
			catch (ActivityNotFoundException e)
			{
                Toast.makeText(mContext,
							   mContext.getText(R.string.actionbar_activity_not_found),
							   Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * An Action which calls up a dialog from the host {@link Activity}, using {@link Activity.onCreateDialog(int id)}
     * @author Espiandev
     *
     */
    public static class DialogAction extends AbstractAction
	{
        private Activity mHost;
        private int dialogId;
        /**
         *
         * @param context the context to invoke a dialog in
         * @param dialogId an int id to determine which dialog to invoke
         * @param drawable the drawable to use for the action
         */
        public DialogAction(Activity host, int dialogId, int drawable)
		{
            super(drawable);
            mHost = host;
            this.dialogId = dialogId;
        }
        @Override
        public void performAction(View view)
		{
            try
			{
				mHost.showDialog(dialogId);
			}
			catch (Exception e)
			{
				Toast.makeText(mHost,
							   e.getMessage(),
							   Toast.LENGTH_SHORT).show();
			}
		}
	}
	/**
	 * An Action which utilises an interface, allowing the host activity to handle the click.
	 * @author Espiandev
	 *
	 */
	public static class InterfaceAction extends AbstractAction
	{
		private OnActionClickListener mListener = null;
		private Activity mHost;
		/**
		 * Create an InterfaceAction. <code>host</code> must implement {@link OnActionClickListener}.
		 * @param host the {@link Activity} which will handle the click
		 * @param drawable the icon for the {@link Action}
		 */
		public InterfaceAction(Activity host, int drawable)
		{
			super(drawable);
			mHost = host;
			try
			{
				mListener = (OnActionClickListener) mHost;
			}
			catch (ClassCastException cce)
			{
				Toast.makeText(host,
							   mHost.getText(R.string.actionbar_listener_unimplemented),
							   Toast.LENGTH_SHORT).show();
			}
		}
		/**
		 * Set the OnActionClickListener
		 * @param listener The listener to bind to this InterfaceAction
		 * @return this InterfaceAction
		 */
		public InterfaceAction setOnActionListener(OnActionClickListener listener)
		{
			this.mListener = listener;
			return this;
		}
		@Override
		public void performAction(View view)
		{
			try
			{
				mListener.onActionClick(this);
			}
			catch (NullPointerException npe)
			{
				Toast.makeText(mHost,
							   mHost.getText(R.string.actionbar_listener_unimplemented),
							   Toast.LENGTH_SHORT).show();
			}
			catch (Exception ex)
			{
				Toast.makeText(mHost,
							   mHost.getText(R.string.actionbar_listener_error),
							   Toast.LENGTH_SHORT).show();
			}
		}
	}
	/**
	 * An Action which invokes onSearchRequested() in the hosting Activity
	 * @author Espiandev
	 *
	 */
	public static class SearchAction extends AbstractAction
	{
		private Activity mHost;
		public SearchAction(Activity host, int drawable)
		{
			super(drawable);
			mHost = host;
		}
		@Override
		public void performAction(View view)
		{
			try
			{
				mHost.onSearchRequested();
				mHost.startSearch(null, false, null, false);
			}
			catch (Exception mnfe)
			{
				Toast.makeText(mHost,
							   mHost.getText(R.string.actionbar_search_error),
							   Toast.LENGTH_SHORT).show();
			}
		}
	}
	/** Interface used to define the listener of an onClickAction */
	public static interface OnClickActionListener
	{
		/** Called when an onClickAction gets fired, flag will be the same as the actions flag */
		public void onClickAction(int flag);
	}
	/**
	 * Simple action that just performs an onClick when the user taps the action button
	 * @author Moritz "Moss" Wundke (b.thax.dcg@gmail.com)
	 *
	 */
	public static class OnClickAction extends AbstractAction
	{
		private OnClickActionListener mClickListener;
		private int mFlag;
		public OnClickAction(Context context, OnClickActionListener clickListener, int flag, int drawable)
		{
			super(drawable);
			mClickListener = clickListener;
			mFlag = flag;
		}
		@Override
		public void performAction(View view)
		{
			if (mClickListener != null)
			{
				mClickListener. onClickAction(mFlag);
			}
		}
	}
	/**
	 * Animated action with start/stop methods.
	 * @author Moritz "Moss" Wundke (b.thax.dcg@gmail.com)
	 *
	 */
	public static class AnimatedAction extends AbstractAction
	{
		private Animation mAnimation;
		private ImageButton mAnimatedView;
		private OnClickListener mClickListener;
		public AnimatedAction(Context context, OnClickListener clickListener, int drawable)
		{
			super(drawable);
			initAction(context, clickListener, R. anim. rotate);
		}
		public AnimatedAction(Context context, OnClickListener clickListener, int drawable, int animResId)
		{
			super(drawable);
			initAction(context, clickListener, animResId);
		}
		private void initAction(Context context, OnClickListener clickListener, int animResId)
		{
			mAnimation = AnimationUtils. loadAnimation(context, animResId);
			mAnimation. setRepeatCount(Animation. INFINITE);
			mClickListener = clickListener;
		}
		/** Start the infinite animation for this action */
		public void startAnimation()
		{
			if (mAnimatedView != null)
			{
				mAnimatedView. startAnimation(mAnimation);
			}
		}
		/** Stop the infinite animation for this action */
		public void stopAnimation()
		{
			if (mAnimatedView != null)
			{
				mAnimatedView. clearAnimation();
			}
		}
		@Override
		public View onInflateView(ActionBar actionBar, View view)
		{
			mAnimatedView = (ImageButton) view. findViewById(R. id . actionbar_item);
			mAnimatedView. setImageResource(this. getDrawable());
			view. setTag(this);
			mAnimatedView. setTag(this);
			mAnimatedView. setOnClickListener(actionBar);
			return view;
		}
		@Override
		public void performAction(View view)
		{
			if (mClickListener != null)
			{
				mClickListener. onClick(view);
			}
		}
		@Override
		public int getLayoutResId()
		{
			return R. layout. animated_actionbar_item ;
		}
	}
}
