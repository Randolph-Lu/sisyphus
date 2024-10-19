package com.randolph.sisyphus.idgen.snowflake;

/**
 * @author : randolph
 * date : 2024/10/6 14:51
 */
public interface ReadableSnowflakeId extends SnowflakeId{

    // 此处直接调用 SnowflakeIdAttributeParse 的 parse 方法对生成的ID进行转换
}
