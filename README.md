# Color Changeable ViewPager

[![](https://jitpack.io/v/giangpham96/color-changeable-viewpager.svg)](https://jitpack.io/#giangpham96/color-changeable-viewpager)

This library helps Android developers achieve the
[WHIM](https://play.google.com/store/apps/details?id=global.maas.whim) Plan ViewPager

## Demonstration
![Demo](/arts/demo.gif?raw=true)

## Installation
Project level `build.gradle`
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

App level `build.gradle`
```
dependencies {
	implementation 'com.github.giangpham96:color-changeable-viewpager:0.1.0'
}
```

## Usage

### ColorChangeableViewPager
Declare it in `layout.xml` file. For example:

```
    ...
    <leo.me.la.colorchangeableviewpager.ColorChangeableViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        />
        ...
```

To customize the color of the view pager background, in `Activity/Fragment`

```
    viewPager.indicatorColors = listOf(
        Color.parseColor("#9945E482"),
        Color.parseColor("#FFF9B72B"),
        Color.parseColor("#554311C5")
    )
```

If you don't set the colors, by default it will be 3 colors from the above demonstration.

### ColorChangeableIndicator
1. Declare it in `layout.xml` file. For example:

```
    ...
    <leo.me.la.colorchangeableviewpager.ColorChangeableIndicator
        android:id="@+id/indicatorBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:text="Show more details"
        app:textSize="14sp"
        app:tabTextSize="11sp"
        app:indicatorTriangleWidth="30dp"
        app:indicatorTriangleHeight="20dp"
        app:selectedColor="android.R.color.black"
        />
        ...
```


`app:text` Text that appears on the upper button of the indicator bar

`app:textSize` Size of text that appears on the upper button of the indicator bar

`app:tabTextSize` Size of text that appears on the each tab of indicator bar

`app:indicatorTriangleWidth`

`app:indicatorTriangleHeight`

`app:selectedColor` Color of the text and circle in the selected tab

2. Create an adapter for the viewpager that integrates with the indicator bar. It must extend
`ColorChangeablePagerAdapter`.
```
    private inner class ScreenSlidePagerAdapter(
            private val data: List<String>,
            fm: FragmentManager
        ) : ColorChangeablePagerAdapter(fm) {
            override fun getCircleIndicatorSize(position: Int): Int {
                // size of the circle in the tab at [postion]
                return 15
            }

            override fun getTitle(position: Int): String {
                // title of the tab at [position]
                return data[position]
            }

            override fun getCount(): Int = data.size

            override fun getItem(position: Int): Fragment = YourCustomFragment()
        }
```
3. Use the adapter and integrate the indicator bar with viewPager
```
    viewPager.adapter = ScreenSlidePagerAdapter(fm = supportFragmentManager)
    indicatorBar.integrateWithViewPager(viewPager)
```
