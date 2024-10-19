package com.randolph.sisyphus.idgen.convert;

import com.randolph.sisyphus.idgen.IdConvert;
import com.randolph.sisyphus.idgen.snowflake.SnowflakeIdAttributeParser;

/**
 * @author : randolph
 * date : 2024/10/6 21:29
 */
public class ReadableSnowflakeIdConvert implements IdConvert {

    private final SnowflakeIdAttributeParser snowflakeIdAttributeParser;

    public ReadableSnowflakeIdConvert(SnowflakeIdAttributeParser parser) {
        this.snowflakeIdAttributeParser = parser;
    }

    public SnowflakeIdAttributeParser getSnowflakeIdAttributeParser() {
        return snowflakeIdAttributeParser;
    }

    @Override
    public String asString(long id) {
        return getSnowflakeIdAttributeParser().parse(id).getReadableSnowflakeId();
    }

    @Override
    public Long asLong(String idStr) {
        return getSnowflakeIdAttributeParser().parse(idStr).getId();
    }
}
