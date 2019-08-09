package com.wenchao.livedatabus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenchao
 * @date 2019/8/1.
 * @time 21:49
 * description：
 */
public class LiveDataBus {

    private static LiveDataBus liveDataBus = new LiveDataBus();

    private Map<String, BusMutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return liveDataBus;
    }

    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }

    public static class BusMutableLiveData<T> extends MutableLiveData {

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hook(Observer observer) throws Exception {
            //获取liveData的类对象
            Class<LiveData> liveDataClass = LiveData.class;
            final Field mObserversFiled = liveDataClass.getDeclaredField("mObservers");
            //设置权限
            mObserversFiled.setAccessible(true);
            final Object mObservers = mObserversFiled.get(this);
            //获取到这个成员变量的类型 SafeIterableMap.class
            final Class<?> aClass = mObservers.getClass();
            final Method get = aClass.getDeclaredMethod("get", Object.class);
            get.setAccessible(true);
            final Object invokeEntry = get.invoke(mObservers, observer);
            Object observerWrapper = null;
            if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                observerWrapper = ((Map.Entry) invokeEntry).getValue();
            }
            if (observerWrapper == null) {
                throw new NullPointerException("observerWrapper不能为空");
            }
            final Class<?> superclass = observerWrapper.getClass().getSuperclass();
            final Field mLastVersionField = superclass.getDeclaredField("mLastVersion");
            mLastVersionField.setAccessible(true);
            final Field mVersionField = liveDataClass.getDeclaredField("mVersion");
            mVersionField.setAccessible(true);
            final Object o = mVersionField.get(this);
            mLastVersionField.set(observerWrapper, o);
        }
    }
}
