package com.randolph.sisyphus.idgen.snowflake;

/**
 * @author : randolph
 * date : 2024/10/6 14:51
 */
public interface SnowflakeReadableId extends SnowflakeId{

    SnowflakeIdAttributeParser getParser();

    default SnowflakeIdAttribute readableId() {
        long id = nextId();
        return getParser().parse(id);
    }

    default SnowflakeIdAttribute readableId(long id){
        return getParser().parse(id);
    }

    default SnowflakeIdAttribute ofReadableId(String idString){
        return getParser().parse(idString);
    }
}
