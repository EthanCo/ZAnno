package com.ethanco.zanno;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description TODO
 * Created by YOLANDA on 2016-01-21.
 */
public class ZAnnoInjector {

    static final Map<Class<?>, AbstractInjector<Object>> INJECTORS = new LinkedHashMap<Class<?>, AbstractInjector<Object>>();

    public static <T> void inject(T obj) {
        AbstractInjector<Object> injector = findInjector(obj);
        if (injector == null) {
            System.out.println("injector == null");
        } else {
            injector.inject(Finder.SUBSCRIPTION, obj, obj);
        }
    }

    private static AbstractInjector<Object> findInjector(Object obj) {
        Class<?> clazz = obj.getClass();
        AbstractInjector<Object> injector = INJECTORS.get(clazz);
        if (injector == null) {
            try {
                Class injectorClazz = Class.forName(clazz.getName() + "$$" + ProxyInfo.PROXY_SUFFIX);
                injector = (AbstractInjector<Object>) injectorClazz.newInstance();
                INJECTORS.put(clazz, injector);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return injector;
    }
}