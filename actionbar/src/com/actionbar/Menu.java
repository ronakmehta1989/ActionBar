package com.actionbar;

import android.view.*;
import android.view.animation.Animation.*;
import android.widget.*;
import android.util.*;
import android.content.*;
import android.view.animation.*;
import java.util.*;

public class Menu extends RelativeLayout implements View.OnClickListener
{
	private LinearLayout mItemsView;
	private LayoutInflater mInflater;
	@SuppressWarnings("unused")
	private Context mCon;

	public Menu(Context con,AttributeSet attr)
	{
		super(con,attr);
		mCon = con;
        mInflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemsView = (LinearLayout)findViewById(R.id.dropdown_foldout_menu);
	}

	@Override
	public void onClick(View v)
	{
		final Object obj = v.getTag();
		if (obj instanceof MenuItem)
		{
			final MenuItem item = (MenuItem)v;
			final TextView item_label = (TextView)v;
			item.onClick(v);
			closeDropdown();
			item_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_dropdown_checked, 0);
		}
		else if (obj instanceof Menu)
		{
			if (mItemsView.getVisibility() == View.GONE)
			{
				openDropdown();
			}
			else
			{
				closeDropdown();
			}
		}
	}
    /**
     * Animates in the dropdown list
     */
    private void openDropdown()
	{
        if (mItemsView.getVisibility() != View.VISIBLE)
		{
            ScaleAnimation anim = new ScaleAnimation(1, 1, 0, 1);
            anim.setDuration(getResources().getInteger(R.integer.dropdown_amination_time));
            mItemsView.startAnimation(anim);
            mItemsView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Animates out the dropdown list
     */
    private void closeDropdown()
	{
        if (mItemsView.getVisibility() == View.VISIBLE)
		{
            ScaleAnimation anim = new ScaleAnimation(1, 1, 1, 0);
            anim.setDuration(getResources().getInteger(R.integer.dropdown_amination_time));
            anim.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation)
					{
					}

					@Override
					public void onAnimationRepeat(Animation animation)
					{
					}

					@Override
					public void onAnimationEnd(Animation animation)
					{
						mItemsView.setVisibility(View.GONE);
					}
				});
            mItemsView.startAnimation(anim);
        }
    } 
	public interface MenuItem
	{
		public void onClick(View w);
		public String getName();
		public void setName(String name);
	}
	public static class MenuItemButton implements MenuItem
	{
		private String mName;
		public String getName()
		{
			return mName;
		}
		public void setName(String name)
		{
			mName = name;
		}
		public void onClick(View w)
		{
			// Implement this!
		}
	}
	/**
     * Adds a list of {@link Action}s.
     * @param actionList the actions to add
     */
    public void addItems(ItemsList itemsList)
	{
        int items = itemsList.size();
        for (int i = 0; i < items; i++)
		{
            addItem(itemsList.get(i));
        }
    }
    /**
     * Adds a new {@link Action}.
     * @param action the action to add
     */
    public void addItem(MenuItem item)
	{
        final int index = mItemsView.getChildCount();
		addItem(item, index);
    }
    /**
     * Adds a new {@link Action} at the specified index.
     * @param action the action to add
     * @param index the position at which to add the action
     */
    public void addItem(MenuItem item, int index)
	{
        mItemsView.addView(inflateAction(item), index);
    }
    /**
     * Removes all action views from this action bar
     */
    public void removeAllItems()
	{
        mItemsView.removeAllViews();
    }
    /**
     * Remove a action from the action bar.
     * @param index position of action to remove
     */
    public void removeItemAt(int index)
	{
		try
		{
			mItemsView.removeViewAt(index);
		}
		catch (Exception e)
		{
			Toast.makeText(getContext(), "Can't remove item!", Toast.LENGTH_SHORT).show();
		}
    }
	/**
     * Change the visibility of an action
     * @param action
     * @param visibility
     */
    public void setItemVisibility(MenuItem item, int visibility)
	{
		int childCount = mItemsView.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			View view = mItemsView.getChildAt(i);
			if (view != null)
			{
				final Object tag = view.getTag();
				if (tag instanceof MenuItem && tag.equals(item))
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
    public void removeItem(MenuItem item)
	{
        int childCount = mItemsView.getChildCount();
        for (int i = 0; i < childCount; i++)
		{
            View view = mItemsView.getChildAt(i);
            if (view != null)
			{
                final Object tag = view.getTag();
                if (tag instanceof MenuItem && tag.equals(item))
				{
                    mItemsView.removeView(view);
                }
            }
        }
    }
    /**
     * Returns the number of actions currently registered with the action bar.
     * @return action count
     */
    public int getItemsCount()
	{
        return mItemsView.getChildCount();
    }
    /**
     * Inflates a {@link View} with the given {@link Action}.
     * @param action the action to inflate
     * @return a view
     */
	private View inflateAction(MenuItem item)
	{
		//Toast.makeText(getContext(),action.toString(),Toast.LENGTH_SHORT).show();
		View view = mInflater.inflate(R.layout.actionbar_dropdown_item, mItemsView, false);
		TextView labelView = (TextView) view.findViewById(R.id.ab_dropdown_item);
		labelView.setText(item.getName());
		view.setTag(item);
		view.setOnClickListener(this);
		return view;
	}
    /**
     * A {@link LinkedList} that holds a list of {@link Action}s.
     */
    public static class ItemsList extends LinkedList<MenuItem>
	{
		private static final long serialVersionUID = -7639253919045641775L;
    }
}
