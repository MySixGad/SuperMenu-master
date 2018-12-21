package net.cmbt.supermenu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import net.cmbt.supermenu.R;

@SuppressLint("AppCompatCustomView")
public class ReverseMenu extends TextView {
    private int l;
    private int b;
    private int r;
    private int t;
    private int soushiX;
    private float mReX;
    private int returns_left;
    private int returns_right;
    Context context;
    private int width;
    private int heights;
    private TranslateAnimation down;
    private boolean isLeft = true;
    private SlideListener listens;
    private int height;
    private float scroX;
    private boolean scrolIsLeft = true;
    private int lastX, lastY;
    private boolean layout_;
    private int l_ok = -113;
    private int t_ok = 1065;
    private int r_ok = 196;
    private int b_ok = 1185;
    private GradientDrawable background;
    private boolean isFristDreaw = true; //第一次绘制
    private String cmbt_leftText = "圈子"; //左侧展现文字
    private String cmbt_rightText = "发现"; //右侧展现文字
    private String cmbt_textColor = "#ffffff"; //文字颜色
    private String cmbt_bgColor = "#000000"; //背景颜色
    private int cmbt_textSize = 15; //文字大小
    private int cmbt_dampMultiple = 2; //滑动阻尼度
    private int clickX;


    public ReverseMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        // TODO Auto-generated constructor stub
        inits(attrs);
    }

    public ReverseMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
        this.context = context;
        inits(attrs);
    }

    public ReverseMenu(Context context) {
        this(context, null);
        this.context = context;
        // TODO Auto-generated constructor stub
//        inits(attrs);
    }


    @Override
    public void layout(int l1, int t1, int r1, int b1) {
        if (!layout_) {
            if (isFristDreaw) {
                super.layout(l_ok, t_ok, r_ok, b_ok);
            } else {
                super.layout(l1, t1, r1, b1);
            }
        } else {
            //用于home 灭屏返回上次移动位置
            super.layout(l_ok, t_ok, r_ok, b_ok);
        }
        isFristDreaw = false;
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
    private void inits(AttributeSet attrs) {

        //获取属性
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.cmbt);
        cmbt_leftText = typeArray.getString(R.styleable.cmbt_rm_leftText) == null ? "圈子" : typeArray.getString(R.styleable.cmbt_rm_leftText);
        cmbt_rightText = typeArray.getString(R.styleable.cmbt_rm_rightText) == null ? "发现" : typeArray.getString(R.styleable.cmbt_rm_rightText);
        cmbt_textColor = typeArray.getString(R.styleable.cmbt_rm_textColor) == null ? "#ffffff" : typeArray.getString(R.styleable.cmbt_rm_textColor);
        cmbt_bgColor = typeArray.getString(R.styleable.cmbt_rm_bgColor) == null ? "#000000" : typeArray.getString(R.styleable.cmbt_rm_bgColor);
        cmbt_textSize = typeArray.getInteger(R.styleable.cmbt_rm_textSize, 15);
        cmbt_dampMultiple = typeArray.getInteger(R.styleable.cmbt_rm_dampMultiple, 2);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        heights = dm.heightPixels;
        height = getHeight();
        setGravity(Gravity.CENTER | Gravity.RIGHT);
        setBackgroundResource(R.drawable.runfast_float_bg);
        setPadding(0, 0, 35, 0);


        //设置背景颜色api
        background = (GradientDrawable) getBackground();
        background.setColor(Color.parseColor(cmbt_bgColor));
        setTextColor(Color.parseColor(cmbt_textColor));
        setTextSize(cmbt_textSize);
        setText(cmbt_leftText);
        setTextAppearance(context, R.style.cmbt_reversemenu_font_style);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  //按下手势
                layout_ = false;
                lastX = (int) event.getRawX();
                clickX = (int) event.getRawX();
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
                l = getLeft() + dx / cmbt_dampMultiple;
                b = getBottom() + dy;
                r = getRight() + dx / cmbt_dampMultiple;
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

                if (b >= heights - 270) {
                    b = heights - 270;
                    t = b - getHeight();
                }

                layout(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:  //放开手势
                layout_ = false;

                if (Math.abs((int) event.getRawX() - clickX) > 10) { //点击并且移动
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        float x1 = event.getX();
                        if (x1 / 2 - scroX > 100) {
                            scrolIsLeft = false;
                        } else if (scroX - x1 / 2 > 220) {
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
                            background = (GradientDrawable) getBackground();
                            background.setColor(Color.parseColor(cmbt_bgColor));
                            setText(cmbt_leftText);
                            setGravity(Gravity.CENTER | Gravity.RIGHT);
                            startInAnmin(1);
                            if (!isLeft) {
                                listens.slideDirection(1);
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
                            background = (GradientDrawable) getBackground();
                            background.setColor(Color.parseColor(cmbt_bgColor));
                            setText("    " + cmbt_rightText);
                            setGravity(Gravity.CENTER | Gravity.LEFT);
                            startInAnmin(2);
                            if (isLeft) {
                                listens.slideDirection(2);
                            }
                            isLeft = false;
                        }
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


    public interface SlideListener {
        /**
         * 滑动至左或者右边的监听
         *
         * @param type 1 左边   2 左边
         */
        void slideDirection(int type);
    }

    /**
     * 设置监听 tvListener
     *
     * @param slideListener
     */
    public void SlideListener(SlideListener slideListener) {
        listens = slideListener;
    }
}
