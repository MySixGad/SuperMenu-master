package supermenu.cmbt.net.supermenu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import supermenu.cmbt.net.supermenu.R;


@SuppressLint("AppCompatCustomView")
public class ReverseMenu extends TextView {
    Context context;
    private int width;
    private int heights;
    private TranslateAnimation down;
    private boolean isLeft = true;
    private TVlisten listens;
    private int height;
    private float scroX;
    private boolean scrolIsLeft = true;
    private int lastX, lastY;
    private int l;
    private int b;
    private int r;
    private int t;
    private int l_ok;
    private int t_ok;
    private int r_ok;
    private int b_ok;
    private int soushiX;
    private float mReX;
    private int returns_left;
    private int returns_right;
    private boolean layout_;

    public ReverseMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        // TODO Auto-generated constructor stub
        inits();
    }

    public ReverseMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
        this.context = context;
        inits();
    }

    public ReverseMenu(Context context) {
        this(context, null);
        this.context = context;
        // TODO Auto-generated constructor stub
        inits();
    }


    @Override
    public void layout(int l1, int t1, int r1, int b1) {
        if (!layout_) {
            super.layout(l1, t1, r1, b1);
        } else {
            //用于home 灭屏返回上次移动位置
            super.layout(l_ok, t_ok, r_ok, b_ok);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus)
            layout_ = true; //设置标识，用于home 灭屏返回上次移动位置
    }

    /**
     * 初始化公用参数
     */
    private void inits() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        heights = dm.heightPixels;
        height = getHeight();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  //按下手势
                layout_ = false;
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                scroX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:  //移动手势
                layout_ = false;
                if (isLeft) {
                    if (event.getRawX() < mReX) {
                        //回划手势了
                        returns_left = 1;
                    }
                } else {
                    if (event.getRawX() > mReX) {
                        //回划手势了
                        returns_right = 1;
                    }
                }
                mReX = event.getRawX();

                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                soushiX = dx;
                l = getLeft() + dx;
                b = getBottom() + dy;
                r = getRight() + dx;
                t = getTop() + dy;

                if (isLeft) {
                    if (l >= 0) {
                        l = 0;
                        r = l + getWidth();
                    }
                } else {
                    if (r <= width + 50) {
                        r = width + 50;
                        l = r - getWidth();
                    }
                }

                if (t < 0) {
                    t = 0;
                    b = t + getHeight();
                }

                if (b > heights - 500) {
                    b = heights - 500;
                    t = b - getHeight();
                }
                layout(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                postInvalidate();
                break;


            case MotionEvent.ACTION_UP:  //放开手势
                layout_ = false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x1 = event.getX();
                    if (x1 - scroX > 200) {
                        scrolIsLeft = false;
                    } else if (scroX - x1 > 200) {
                        scrolIsLeft = true;
                    }

                    if (scrolIsLeft) {
                        //左反弹
                        layout(-110, t, 200, b);
                        l_ok = -110;
                        t_ok = t;
                        r_ok = 200;
                        b_ok = b;
                        setBackgroundResource(R.drawable.runfast_float_bg);
                        setText("圈子");
                        setGravity(Gravity.CENTER | Gravity.RIGHT);
                        startInAnmin(1);
                        if (!isLeft) {
                            listens.TvChangeListener(0);
                        }
                        isLeft = true;
                    } else {
                        //右反弹
                        layout(width - 200, t, width + 160, b);
                        l_ok = width - 200;
                        t_ok = t;
                        r_ok = width + 160;
                        b_ok = b;
                        setBackgroundResource(R.drawable.runfast_float_bg_right);
                        setText("    发现");
                        setGravity(Gravity.CENTER | Gravity.LEFT);
                        startInAnmin(2);
                        if (isLeft) {
                            listens.TvChangeListener(1);
                        }
                        isLeft = false;
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 执行动画
     *
     * @param i
     */
    private void startInAnmin(int i) {
        if (i == 1) {
            down = new TranslateAnimation(0, -50, 0, 0);
        } else if (i == 2) {
            down = new TranslateAnimation(0, 50, 0, 0);
        }
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());
        down.setDuration(700);
        startAnimation(down);
    }


    public interface TVlisten {
        /**
         * 滑动至左或者右边的监听
         *
         * @param type 1 左边   2 左边
         */
        void TvChangeListener(int type);
    }

    /**
     * 设置监听 tvListener
     *
     * @param tvListener
     */
    public void setTVListener(TVlisten tvListener) {
        listens = tvListener;
    }
}
