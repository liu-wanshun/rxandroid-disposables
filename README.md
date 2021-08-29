# Android Disposable

[![](https://jitpack.io/v/com.gitee.liu_wanshun/AndroidDisposable.svg)](https://jitpack.io/#com.gitee.liu_wanshun/AndroidDisposable)

**Step 1.** Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```groovy
	dependencies {
	        implementation 'com.gitee.liu_wanshun:AndroidDisposable:Tag'
        	//0.0.2 开始需要 rxjava3 和 rxAndroid
	}
```

(Please replace `Tag`  with the latest version numbers: [![](https://jitpack.io/v/com.gitee.liu_wanshun/AndroidDisposable.svg)](https://jitpack.io/#com.gitee.liu_wanshun/AndroidDisposable)



## 功能介绍

1. 绑定生命周期（防止内存泄漏）

```java
//绑定 Activity/Fragment 的 Lifecycle
LifecycleDisposable.from(this).add(Disposable disposable);

//绑定 Fragment 的 ViewLifecycle（必须在 ViewLifecycle 范围内使用）
ViewLifecycleDisposable.from(this).add(Disposable disposable);

//绑定 Viewmodel
ViewModelDisposable.from(this).add(Disposable disposable);

//绑定 View（必须在 ViewAttachedToWindow ~ ViewDetachedFromWindow 范围内使用）
ViewDisposable.from(this).add(Disposable disposable);


```

2. 对于简单切换线程的操作，减少切换线程模板代码

```java
Disposable disposable = RxCall.io2ui(() -> {
            work(3000);
            return "work over";
        }).subscribe(result -> Log.d(TAG, "accept: " + result))
```

​	等价于

```java
Disposable disposable = Single.fromSupplier(() -> {
    work(3000);
    return "work over";
})
        .compose(RxTransformer.io2uiSingle())
        .subscribe(result -> Log.d(TAG, "accept: " + result));
```

​	等价于

```java
Disposable disposable = Single.fromSupplier(() -> {
            work(3000);
            return "work over";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> Log.d(TAG, "accept: " + result));
```



## 示例

```java
LifecycleDisposable.from(this).add(RxCall.io2ui(() -> {
    work(3000);
    return "work over";
}).subscribe(result -> Log.d(TAG, "accept: " + result)));
```
