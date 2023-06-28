# RxJava Android Disposables

[![jitpack](https://jitpack.io/v/liu-wanshun/rxandroid-disposables.svg)](https://jitpack.io/#liu-wanshun/rxandroid-disposables)
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

2. 添加`rxandroid-disposables`
   依赖最新版（将Tag替换为[![jitpack](https://jitpack.io/v/liu-wanshun/rxandroid-disposables.svg)](https://jitpack.io/#liu-wanshun/rxandroid-disposables)
   后面的数字）

```groovy
dependencies {
   implementation "com.github.liu-wanshun:rxandroid-disposables:Tag"
   // 您应该使用Rxjava3.1.0或以后的版本 和 RxAndroid
   // 如果您的项目中使用Kotlin,建议您使用Kotlin协程而不是Rxjava
}
```

## 示例

获取`CompositeDisposable`

```java
// 对于 LifecycleOwner
CompositeDisposable composite = LifecycleDisposable.from(lifecycleOwner);

// 在Fragment中应尽可能使用ViewLifecycleOwner,例如：
CompositeDisposable composite = LifecycleDisposable.from(getViewLifecycleOwner());

// 对于 Lifecycle
CompositeDisposable composite = LifecycleDisposable.from(lifecycle);

// 对于 ViewModel
CompositeDisposable composite = ViewModelDisposable.from(viewModel);

// 对于 View
CompositeDisposable composite = ViewAttachDisposable.from(view);

```

你可以像这样使用：

```java
Single.just(1)
        .subscribe(s->{
        Log.e("sss","成功结果: "+s);
        },throwable->{
        Log.e("sss","失败: ",throwable);
        },LifecycleDisposable.from(lifecycleOwner) /*CompositeDisposable*/);

```