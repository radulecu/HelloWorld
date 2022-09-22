package ro.rasel.java.exceptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class RethrowException {
    public static void main(String[] args) throws Exception {
        try {
            createException();
        } catch (Exception e) {
            wrapException(e);
        }
    }

    private static <T extends Throwable> void wrapException(T ex) throws T {
        Class<T> wrapperCls = (Class<T>) ex.getClass();
        wrapException(ex, wrapperCls);
    }

    private static <T extends Throwable> void wrapException(T ex, Class<T> wrapperCls) throws T {
        final T wrapper = Optional.ofNullable(ex)
                .map(t -> {
                    try {
                        Constructor<T> noArgConstructor = null;
                        Constructor<T>[] constructors = (Constructor<T>[]) wrapperCls.getConstructors();
                        for (Constructor<T> c : constructors) {
                            Class<?>[] parameters = c.getParameterTypes();
                            if (parameters.length == 0) {
                                noArgConstructor = c;
                            } else if (parameters.length == 1 && parameters[0] == Throwable.class) {
                                return c.newInstance(t);
                            }
                        }
                        if (noArgConstructor != null) {
                            T wx = (noArgConstructor.newInstance());
                            wx.initCause(t);
                            return wx;
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("failed to recreate exception", e);
                    }
                    return null;
                }).orElse(null);
        if (wrapper!=null){
            throw wrapper;
        }
    }

    private static void createException() {
        int i = 1 + (Integer) null;
    }


}
