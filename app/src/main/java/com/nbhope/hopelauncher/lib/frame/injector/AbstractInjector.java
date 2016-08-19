package com.nbhope.hopelauncher.lib.frame.injector;

public interface AbstractInjector<T>
{
	void inject(T target, Object source);
}
