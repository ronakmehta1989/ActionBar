# Action Bar Extended version for Android

This projects aims to provide a reusable action bar component. The action bar pattern is well documented at [Android Patterns](http://www.androidpatterns.com/uap_pattern/action-bar).

The action bar component is an [Library Project](http://developer.android.com/guide/developing/eclipse-adt.html#libraryProject). This means that there's no need to copy-paste resources into your own project, simply add the action bar component as a reference to any project.

Need icons to your action bar? [Official Android icons library](http://developer.android.com/design/downloads/index.html).

## Usage

### In your layout

    <com.actionbar.ActionBar
	    android:id="@+id/actionbar"
	    style="@style/ActionBar" />

### In your activity

    ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
    // You can assign the title programmatically by passing a
    // CharSequence or resource id.
    actionBar.setTitle("Test title");
    actionBar.setHomeAction(new IntentAction(this, HomeActivity.createIntent(this), R.drawable.ic_title_home_default));
    actionBar.addAction(new IntentAction(this, createShareIntent(), R.drawable.ic_title_share_default));
    actionBar.addAction(new ToastAction());

### Custom actions

ActionBar comes with a convenient IntentAction that makes it easy to create action out of Intents. To create custom actions simply implement the Action interface and build your own like the Toast example below.

    private class ToastAction implements Action {

        @Override
        public int getDrawable() {
            return R.drawable.ic_title_export_default;
        }

        @Override
        public void performAction(View view) {
            Toast.makeText(OtherActivity.this,
                    "Example action", Toast.LENGTH_SHORT).show();
        }

    }

### Handle on click on the title

To handle on clicks on the title pass a `android.view.View.OnClickListener` to the method `setOnTitleClickListener` on the action bar. The `View` that is passed in `onClick` is the `TextView` that the title is assigned to.

    actionBar.setOnTitleClickListener(new OnClickListener() {
        public void onClick(View v) {
            // Your code here
        }
    });

### Customization

You can change everything in this project.

## Is it stable?

Yes it is, but there's no guarantees. The api however is still not stable so please check all commits since the last pull. It might also be an good idea to depend on your own fork instead of this component directly. Eventually there will be more controlled releases but until then.

## Contributions

This widget wouldn't be the same without the excellent contributions by;

* ohhorob, <https://github.com/ohhorob>
* denravonska, <https://github.com/denravonska>
* rpdillon, <https://github.com/rpdillon>
* RickardPettersson, <https://github.com/RickardPettersson>
* Jake Wharton, <https://github.com/JakeWharton>
* Jesse Vincent, <http://blog.fsck.com>
* Gyuri Grell, <http://gyurigrell.com>

### Want to contribute?

GitHub has some great articles on [how to get started with Git and GitHub](http://help.github.com/) and how to [fork a project](http://help.github.com/forking/).

Contributers are recommended to fork the app on GitHub (but don't have too). Create a feature branch, push the branch to git hub, press Pull Request and write a simple explanation.

One fix per commit. If say a a commit closes the open issue 12. Just add `closes #12` in your commit message to close that issue automagically.

All code that is contributed must be compliant with [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

## Code Style Guidelines

Contributers are recommended to follow the Android [Code Style Guidelines](http://source.android.com/source/code-style.html) with exception for line length that I try to hold to 80 columns where possible.

In short that is;

* Indentation: 4 spaces, no tabs.
* Line length: 80 columns
* Field names: Non-public, non-static fields start with m.
* Braces: Opening braces don't go on their own line.
* Acronyms are words: Treat acronyms as words in names, yielding XmlHttpRequest, getUrl(), etc.
* Consistency: Look at what's around you!

Have fun and remember we do this in our spare time so don't be too serious :)

## License
Copyright (c) 2012 STALKER_2010

Copyright (c) 2010 [Johan Nilsson](http://markupartist.com)

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
