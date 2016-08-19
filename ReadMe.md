# ZAnno #
为了使编程更简单，开发的一个注解框架

## 使用 ##
在根build.gradle中添加  

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}  

在你的build.gradle (Module:app) 中添加  

	dependencies {
	        compile 'com.github.EthanCo:ZAnno:1.0.2'
	}

即可

## 注解说明 ##

### @AutoDestory ###
作用:自动销毁、回收、置空 成员变量  
使用范围: 成员变量

#### 作用一 ####

在使用RxJava的时候，如果Activity已经销毁，而Subscription任然存在，此时Subscription进行该Activity的UI操作，即会报错。 

故我们需要在Activity生命周期结束时进行Subscription的unsubscribe(取消订阅)。

但是在每个onDestroy时都进行unsubscribe，显得相当繁琐。  

而如果放置在父类中进行统一的unsubscribe，又由于subscription的个数是未知的，就需要增加一个List进行统一的存储了，那么就会有很多的add操作，代码显得非常的不美观和怪异。  

故出现了@AutoDestory，它将inject的地方自动调用unsubscribe，无需再手动调用。同时因为是编译时注解，几乎不使用反射，所以不影响性能

#### 使用 ####
在需要进行unsubscribe的地方 进行注入

	ZAnnoInjector.inject(this);

对subscription添加@AutoDestory注解即可

	@AutoDestory
    Subscription subscription;

> 注意: Subscription的修饰符不能是private


### 常见错误 ###

如果由于与其他第三方库冲突(butterknife)而出现如下错误

	Duplicate files copied in APK META-INF/services/javax.annotation.processing.Processor  

则在build.gradle中添加

	packagingOptions {
        exclude 'META-INF/services/javax.annotation.processing.Processor'
    }

即可  

