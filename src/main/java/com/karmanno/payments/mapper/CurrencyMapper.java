package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.Currency;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CurrencyMapper {
    @Select("select exists (select 1 from currencies where currencies.code = #{code})")
    boolean existsByUsername(@Param("code") String code);

    @Insert("insert into currencies values (#{id}, #{code}, #{fullName}, #{minorUnits})")
    @Options(keyColumn = "id", useGeneratedKeys = true)
    int insertNewCurrency(Currency currency);

    @Select("select * from currencies where currencies.username = #{code}")
    Currency selectByCode(@Param("code") String code);
}
