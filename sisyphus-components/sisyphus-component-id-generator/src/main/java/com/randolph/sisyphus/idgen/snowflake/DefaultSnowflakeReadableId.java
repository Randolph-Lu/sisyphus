package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.IdConvert;
import com.randolph.sisyphus.idgen.convert.ReadableSnowflakeIdConvert;

import java.time.ZoneId;

/**
 * @author : randolph
 * date : 2024/10/20 15:08
 */
public class DefaultSnowflakeReadableId extends StringSnowflakeId implements SnowflakeReadableId{

    private final SnowflakeIdAttributeParser parser;

    public DefaultSnowflakeReadableId(SnowflakeId idGenerator){
        this(idGenerator, ZoneId.systemDefault());
    }

    public DefaultSnowflakeReadableId(SnowflakeId idGenerator, ZoneId zoneId){
        this(idGenerator, SnowflakeIdAttributeParser.create(idGenerator, zoneId));
    }

    public DefaultSnowflakeReadableId(SnowflakeId idGenerator, SnowflakeIdAttributeParser parser){
        this(idGenerator, new ReadableSnowflakeIdConvert(parser), parser);
    }

    public DefaultSnowflakeReadableId(SnowflakeId idGenerator, IdConvert idConvert, SnowflakeIdAttributeParser parser) {
        super(idGenerator, idConvert);
        this.parser = parser;
    }

    @Override
    public SnowflakeIdAttributeParser getParser() {
        return parser;
    }
}
