package com.talentengine.pocketpixelpets.database.typeConverter;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Since date is not a type Room can handle (String, int, decimal), it needs top be converted
 * @author Jessica Sandoval
 * @since 12/08/2025
 */
public class LocalDateTypeConverter {
    @TypeConverter
    public static long convertDateToLong(LocalDateTime created_at) {
        ZonedDateTime zdt = ZonedDateTime.of(created_at, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    @TypeConverter
    public static LocalDateTime convertLongToDate(Long epochMilli){
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}

