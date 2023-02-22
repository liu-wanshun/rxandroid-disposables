# Android Disposable

[![Jitpack](https://jitpack.io/v/com.github.liu-wanshun/AndroidDisposable.svg)](https://jitpack.io/#com.github.liu-wanshun/AndroidDisposable)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

参考`AndroidX-KTX`中的`lifecycleScope`,将RxJava绑定Android周期,减少/防止内存泄漏。

## 添加依赖

1. 添加`jitpack`仓库

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2. 添加`AndroidDisposable`
   依赖最新版（将Tag替换为[![](https://jitpack.io/v/com.github.liu-wanshun/AndroidDisposable.svg)](https://jitpack.io/#com.github.liu-wanshun/AndroidDisposable)
   后面的数字）

```groovy
dependencies {
    implementation "com.github.liu-wanshun:AndroidDisposable:Tag"
    //需要 rxjava3 和 rxAndroid
}
```

## 示例

**[推荐]** Rxjava`3.1.0`起支持以下写法

```java
Single.fromSupplier(()->{
        return"";
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s->{
        Log.e(TAG,"成功结果: "+s);

        },throwable->{

        Log.e(TAG,"失败: ",throwable);

        },AndroidDisposable.from(this));
```

[不推荐]旧版用法

```java
AndroidDisposable.from(this).add(Single.fromSupplier(()->{
        return"";
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s->{
        Log.e(TAG,"成功结果: "+s);

        },throwable->{

        Log.e(TAG,"失败: ",throwable);

        }));
```
