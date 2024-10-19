package com.randolph.sisyphus.idgen;

/**
 * @author : randolph
 * date : 2024/10/10 18:27
 */
public interface IdGeneratorDecorator extends IdGenerator, Decorator<IdGenerator>{
    IdGenerator getInstance();

    static <T extends IdGenerator> T getInstance(T idGenerator){
        return Decorator.getInstance(idGenerator);
    }

    @Override
    default long nextId(){
        return getInstance().nextId();
    }


}
