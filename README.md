# SuperMenu-master
超级菜单menu


用法：

依赖：
allprojects {
    repositories {

        maven { url "https://jitpack.io" }
    }
}

  compile 'com.github.MySixGad:SuperMenu-master:V1.0.2'


xml:

    <net.cmbt.supermenu.widget.ReverseMenu
        android:id="@+id/float_circle"
        android:layout_width="103dp"
        android:layout_height="40dp"
        cmbt:rm_bgColor="#FE671E"
        cmbt:rm_dampMultiple="3"
        cmbt:rm_leftText="圈子"
        cmbt:rm_rightText="发现"
        cmbt:rm_textColor="#ffffff"
        cmbt:rm_textSize="15" />
        
        
        
java：

       ReverseMenu reverseMenu = (ReverseMenu) findViewById(R.id.float_circle);

        reverseMenu.SlideListener(new ReverseMenu.SlideListener() {
            @Override
            public void slideDirection(int type) {
                /**
                 * @param type 1 左边   2 左边
                 */
            }
        });



