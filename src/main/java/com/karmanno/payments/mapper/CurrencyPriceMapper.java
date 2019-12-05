package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.CurrencyPrice;
import org.apache.ibatis.annotations.*;

public interface CurrencyPriceMapper {
    @Insert("insert into currency_prices values ( #{idFrom}, #{idTo}, #{price} )")
    int insertNewPrice(@Param("idFrom") Integer idFrom, @Param("idTo") Integer idTo, @Param("price") Integer price);

    @Update("update currency_prices set price = #{price} where cur_from = #{idFrom} and cur_to = #{idTo}")
    int updatePrice(@Param("idFrom") Integer idFrom, @Param("idTo") Integer idTo, @Param("price") Integer price);

    @Select("select * from currency_prices where cur_from = #{idFrom} and cur_to = #{idTo}")
    @Results({
            @Result(column = "cur_from", property = "from"),
            @Result(column = "cur_to", property = "to"),
            @Result(column = "cur_from", property = "currencyFrom", one = @One(select = "com.karmanno.payments.mapper.CurrencyMapper.selectById")),
            @Result(column = "cur_to", property = "currencyTo", one = @One(select = "com.karmanno.payments.mapper.CurrencyMapper.selectById"))
    })
    CurrencyPrice selectByCurrencies(@Param("idFrom") Integer idFrom, @Param("idTo") Integer idTo);
}
