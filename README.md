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
        	//需要 rxjava3 和 rxAndroid
	}
```

(Please replace `Tag`  with the latest version numbers: [![](https://jitpack.io/v/com.gitee.liu_wanshun/AndroidDisposable.svg)](https://jitpack.io/#com.gitee.liu_wanshun/AndroidDisposable)



## 功能介绍

绑定生命周期（防止内存泄漏）

```java
// this可以是 LifecycleOwner / Lifecycle / ViewModel / View
AndroidDisposable.from(this).add(Disposable disposable);

```

## 示例

```java
        
AndroidDisposable.from(this).add(Single.fromSupplier(() -> {
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.e(TAG, "成功结果: " + s);

                }, throwable -> {

                    Log.e(TAG, "失败: ", throwable);

                }));





//Rxjava3.1.0起支持以下写法
Single.fromSupplier(() -> {
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.e(TAG, "成功结果: " + s);

                }, throwable -> {

                    Log.e(TAG, "失败: ", throwable);

                }, AndroidDisposable.from(this));



```
