# Floating Action Button（悬浮按钮）的常用属性 以及图片不居中的解决方案

## 简介：   

    Floating Action Button（悬浮按钮）是Android官方推出的一种特殊的按钮，形状
    默认为圆形，它可以悬浮于其他的UI之上，形成一个较为独立的按钮。

## 常用属性：
 * fabSize ： 这个用于设置悬浮按钮的大小，有三个可选值  

      - auto：表示 FAB 的大小由系统自动设置，通常是标准大小。   
      - normal：表示 FAB 的大小为标准大小。标准大小是指 FAB 直径为 56dp。   
      - mini： 表示 FAB 直径为 40dp，相比标准大小的 FAB 小了很多。

 * backgroundTint ： 这个属性用于设置悬浮按钮的底色，一般悬浮按钮的底色由     Theme的colorSecondary决定。设置backgroundTint也可以改变按钮的底色。   

 * elevation : 设置按钮投影下来的阴影大小。   

 * maxImageSize ：设置指示图标的最大尺寸。 

 * rippleColor ：设置按钮被点击后的涟漪效果的颜色。

 * customSize ：设置自定义的按钮尺寸。**特别提醒**，如果你的图片没有居中显示  
 那么你需要设置你的customSize大小与按钮的尺寸完全一致，比如：   
    
        android:layout_width="80dp"
        android:layout_height="80dp"
        app:fabCustomSize="80dp"
    这样图片就会居中显示了。


