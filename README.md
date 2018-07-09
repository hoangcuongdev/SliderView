#  SliderView
[![](https://jitpack.io/v/markizdeviler/SliderView.svg)](https://jitpack.io/#markizdeviler/SliderView)

 Image Slider library

## Usage	

### Step 1 
```android
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.markizdeviler:SliderView:0.0.3'
}
``` 

### Step 2
``` xml

<carousel.uz.mukhammadakbar.SliderView
            android:id="@+id/sliderView"
            android:layout_width="match_parent"
     	    android:layout_height="230dp"
	    app:sliderHeight="200dp"  //optional dots layout stays after sliderView
            app:dotsDefaultColor="@color/default"
            app:dotsSelectedColor="@color/selected"
            app:dotsRadius="4dp"
            app:dotsPadding="3dp"/>
```

#### Any Issues & contributions appreciated
