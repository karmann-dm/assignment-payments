package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.Currency;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CurrencyMapper {
    @Select("select exists (select 1 from currencies where currencies.code = #{code})")
    boolean existsByCode(@Param("code") String code);

    @Insert("insert into currencies values (#{id}, #{code}, #{fullName}, #{minorUnits})")
    @Options(keyColumn = "id", useGeneratedKeys = true)
    int insertNewCurrency(Currency currency);

    @Select("select * from currencies where currencies.code = #{code}")
    @Results({
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "minorUnits", column = "minor_units")
    })
    Currency selectByCode(@Param("code") String code);

    @Select("select * from currencies where currencies.id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "minorUnits", column = "minor_units")
    })
    Currency selectById(@Param("id") Integer id);
}
