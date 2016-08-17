package com.ethanco.zanno;

public interface AbstractInjector<T>
{
	void inject(Finder finder, T target, Object source);
}
