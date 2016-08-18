package com.example.dell.demo0816;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dell.demo0816.fragment.outlayer.LoginAnimFragment;
import com.example.dell.demo0816.fragment.outlayer.WelcomAnimFragment;
import com.example.dell.demo0816.utils.DisplayUtil;
import com.example.dell.demo0816.view.ParentViewPager;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public final int FRAGMENT_WELCOMEANIM = 0;
    public final int FRAGMENT_LOGINANIM = 1;

    private ImageView iv_logo;
    private ParentViewPager vp_parent;

    private float mLogoY;
    private AnimatorSet mAnimatorSet;

    private WelcomAnimFragment mWelcomAnimFragment;
    private LoginAnimFragment mLoginAnimFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setClick();
        init();
    }

    private void findView() {
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        vp_parent = (ParentViewPager) findViewById(R.id.vp_parent);
    }

    private void setClick() {
        vp_parent.addOnPageChangeListener(this);
    }

    private void init() {
        ParentFragmentStatePagerAdapter adapter = new ParentFragmentStatePagerAdapter(getSupportFragmentManager());
        vp_parent.setAdapter(adapter);
    }

    private void playLogoInAnim() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_logo, "scaleX", 1.0f, 0.5f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_logo, "scaleY", 1.0f, 0.5f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv_logo, "y", mLogoY, DisplayUtil.dip2px(MainActivity.this, 15));

        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim1).with(anim2);
        mAnimatorSet.play(anim2).with(anim3);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case FRAGMENT_WELCOMEANIM:
                break;
            case FRAGMENT_LOGINANIM:
                vp_parent.mLoginPageLock = true;
                iv_logo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mLogoY == 0) {
                            mLogoY = ViewHelper.getY(iv_logo);
                        }
                        playLogoInAnim();
                    }
                }, 500);
                vp_parent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoginAnimFragment.playInAnim();
                    }
                }, 300);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ParentFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public ParentFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_WELCOMEANIM:
                    mWelcomAnimFragment = new WelcomAnimFragment();
                    vp_parent.setWelcomAnimFragment(mWelcomAnimFragment);
                    mWelcomAnimFragment.setWelcomAnimFragmentInterface(new WelcomAnimFragment.WelcomAnimFragmentInterface() {
                        @Override
                        public void onSkip() {
                            vp_parent.setCurrentItem(1);
                        }
                    });
                    return mWelcomAnimFragment;
                case FRAGMENT_LOGINANIM:
                    mLoginAnimFragment = new LoginAnimFragment();
                    return mLoginAnimFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
