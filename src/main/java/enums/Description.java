package enums;

/**
 * Created by Huy on 21/01/2017.
 */
@FunctionalInterface
public interface Description<X, T> {

    T getDescription(X target);

}
