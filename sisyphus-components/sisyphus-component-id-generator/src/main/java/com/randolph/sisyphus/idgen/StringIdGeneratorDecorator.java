package com.randolph.sisyphus.idgen;

/**
 * @author : randolph
 * date : 2024/10/10 18:19
 */
public class StringIdGeneratorDecorator implements IdGeneratorDecorator {
    protected final IdGenerator idGenerator;
    protected final IdConvert idConvert;

    StringIdGeneratorDecorator(IdGenerator idGenerator, IdConvert idConvert){
        this.idGenerator = idGenerator;
        this.idConvert = idConvert;
    }


    @Override
    public IdGenerator getInstance() {
        return idGenerator;
    }

    @Override
    public IdConvert getIdConvert() {
        return idConvert;
    }
}
