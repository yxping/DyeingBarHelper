# DyeingBarHelper
a android project for tinting the system bar
一个可以进行自定义订制StatusBar和NavigationBar的工具，同时还可以通过TintingHelper去获取一个view的颜色来填充自定义的Bar。

### Demo
![demo](https://github.com/yxping/DyeingBarHelper/raw/master/demo.gif)
![demo_immersive](https://github.com/yxping/DyeingBarHelper/raw/master/demo_immersive.gif)

## Dependency
1.Add it in your root build.gradle at the end of repositories:
``` gradle
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
2.Add the dependency in your module
``` gradle
dependencies {
	        compile 'com.github.yxping:DyeingBarHelper:v0.0.1'
	}
```
### Usage
``` java
// 设置状态栏和导航栏为透明,否则无法达到效果
DyeingBarHelper.setBarTranslucent(this);
// DyeingBarHelper的初始化
DyeingBarHelper helper = new DyeingBarHelper(this);
// 提供自定义navigation bar和status bar
// DyeingBarHelper helper = new DyeingBarHelper(this, statusView, navigationView);

// 设置颜色
helper.setStatusBarColor(Color.BLUE);
helper.setNavigationBarColor(Color.BLUE);
// 设置透明度
helper.setStatusBarViewAlpha(0);
helper.setNavigationBarViewAlpha(0);
// 设置可见
helper.setStatusBarViewVisibility(View.VISIBLE);
helper.setNavigationBarViewVisibility(View.VISIBLE);
// 设置背景
helper.setStatusBarViewBackground(drawable);
helper.setNavigationBarViewBackground(drawable);

// 获取一个view的平均的颜色值
TintingUtil.getInstance().getAverColorFromView(view, dis);
// 获取一个view的占据面积最多的颜色值
TintingUtil.getInstance().getMaxColorFromView(view, dis);
```

#### reference
[android粒子爆炸效果](http://blog.csdn.net/crazy__chen/article/details/50149619) 
[android systembarTint](https://github.com/jgilfelt/SystemBarTint)
