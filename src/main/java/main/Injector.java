package main;

import com.google.inject.AbstractModule;

import static com.google.inject.Guice.createInjector;

/**
 * Created by Phong on 11/02/2017.
 */
public enum Injector {

    INJECTOR;

    private com.google.inject.Injector injector;

    public final <T> T getInstance(AbstractModule module, Class<T> instance) {
        if (this.injector == null) {
            this.injector = createInjector(module);
        }
        return this.injector.getInstance(instance);
    }

    public final <T> T getInstance(Class<T> instance) {
        return this.injector.getInstance(instance);
    }

}
