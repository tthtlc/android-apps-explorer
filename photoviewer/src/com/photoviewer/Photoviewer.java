package com.photoviewer;

import com.photoviewer.R;

import android.app.Activity;
import android.os.Bundle;
//import android.widget.ListView;

import android.widget.ViewSwitcher.ViewFactory;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * This sample application shows how to use layout animation and various
 * transformations on views. The result is a 3D transition between a
 * ListView and an ImageView. When the user clicks the list, it flips to
 * show the picture. When the user clicks the picture, it flips to show the
 * list. The animation is made of two smaller animations: the first half
 * rotates the list by 90 degrees on the Y axis and the second half rotates
 * the picture by 90 degrees on the Y axis. When the first half finishes, the
 * list is made invisible and the picture is set visible.
 */

public class Photoviewer extends Activity implements 
    /*AdapterView.OnItemClickListener,*/ View.OnClickListener {
    //private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;
    private int mPosition = 0;

    // Names of the photos we show in the list
    private static final String[] PHOTOS_NAMES = new String[] {
            "Lyon",
            "Livermore",
            "Tahoe Pier",
            "Lake Tahoe",
            "Grand Canyon",
            "Bodie"
    };

    // Resource identifiers for the photos we want to display
    private static final int[] PHOTOS_RESOURCES = new int[] {
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
            R.drawable.photo6,
	    0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animations_main_screen);

        //mPhotosList = (ListView) findViewById(android.R.id.list);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);

        // Prepare the ListView
/*
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PHOTOS_NAMES);
*/

        //mPhotosList.setAdapter(adapter);
        //mPhotosList.setOnItemClickListener(this);

        // Prepare the ImageView
        //mImageView.setClickable(true);
        //mImageView.setFocusable(true);
        mImageView.setOnClickListener(this);
Log.w(this.getClass().getName(), "onCreate!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        mImageView.setImageResource(R.drawable.photo1);
        mImageView.setVisibility(View.VISIBLE);
        //mImageView.setImageDrawable(R.drawable.photo1);

        // Since we are caching large views, we want to keep their cache
        // between each animation
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

/*
      @Override
      public View makeView() {
            ImageView iView = new ImageView(this);
            iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(
                                    LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            iView.setBackgroundColor(0xFF000000);
            return iView;
      }
*/

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(int position, float start, float end) {
        // Find the center of the container
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }

/*
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        // Pre-load the image then start the animation
Log.w(this.getClass().getName(), "onItemclick!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        mImageView.setImageResource(PHOTOS_RESOURCES[position]);
        applyRotation(position, 0, 90);
    }
*/

    public void onClick(View v) {
Log.w(this.getClass().getName(), "onclick!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        applyRotation(mPosition, 0, 90);
	mPosition++;
	if (PHOTOS_RESOURCES[mPosition] == 0) mPosition = 0;
Log.w(this.getClass().getName(), "now setimage!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        mImageView.setImageResource(PHOTOS_RESOURCES[mPosition]);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.requestFocus();
/*
	else 
        applyRotation(-1, 180, 90);
*/
    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        /*private final int mPosition;*/

        private DisplayNextView(int position) {
            mPosition = position;
Log.w(this.getClass().getName(), "displaynextview!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        }

        public void onAnimationStart(Animation animation) {
Log.w(this.getClass().getName(), "onanimate!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        }

        public void onAnimationEnd(Animation animation) {
Log.w(this.getClass().getName(), "onanimate end !!!!!!!!!!!!!!!!!!!!!!!!!1 ");
            mContainer.post(new SwapViews(mPosition));
Log.w(this.getClass().getName(), "onanimate end post n!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        }

        public void onAnimationRepeat(Animation animation) {
Log.w(this.getClass().getName(), "onanimaterepeat!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {
        /* private final int mPosition; */

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
Log.w(this.getClass().getName(), "run!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            
            if (mPosition > -1) {
                //mPhotosList.setVisibility(View.GONE);
Log.w(this.getClass().getName(), "set visible!!!!!!!!!!!!!!\n");
                mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();

Log.w(this.getClass().getName(), "before rotate!!!!!!!!!!!!!!!!!!!!!!!!!1 ");
                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
Log.w(this.getClass().getName(), "set invisible!!!!!!!!!!\n");
                mImageView.setVisibility(View.GONE);
                //mPhotosList.setVisibility(View.VISIBLE);
                //mPhotosList.requestFocus();

                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }

}
