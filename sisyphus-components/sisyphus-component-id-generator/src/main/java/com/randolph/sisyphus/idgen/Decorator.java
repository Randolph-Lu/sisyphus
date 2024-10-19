package com.randolph.sisyphus.idgen;

/**
 * @author : randolph
 * date : 2024/10/9 20:34
 */
public interface Decorator<D> {

    D getInstance();

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <D> D getInstance(D origin){
        if (origin instanceof Decorator decorator){
            return getInstance((D) decorator.getInstance());
        }
        return origin;
    }
}
