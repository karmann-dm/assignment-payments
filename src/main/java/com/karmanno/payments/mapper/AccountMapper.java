package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {
    @Insert("insert into accounts values (#{id}, #{number}, #{userId}, #{currencyId}, #{balance})")
    @Options(keyColumn = "id", useGeneratedKeys = true)
    int insertNewAccount(Account account);

    @Select("select * from accounts where accounts.number = #{number}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "currency_id", property = "currencyId"),
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.karmanno.payments.mapper.UserMapper.selectById")),
            @Result(column = "currency_id", property = "currency",
                    one = @One(select = "com.karmanno.payments.mapper.CurrencyMapper.selectById"))
    })
    Account selectByNumber(@Param("number") String number);

    @Update("update accounts set balance = #{price} where number = #{number}")
    int updateAccount(@Param("price") Integer price, @Param("number") String number);

    @Select("select * from accounts where accounts.user_id = #{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "currency_id", property = "currencyId"),
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.karmanno.payments.mapper.UserMapper.selectById")),
            @Result(column = "currency_id", property = "currency",
                    one = @One(select = "com.karmanno.payments.mapper.CurrencyMapper.selectById"))
    })
    List<Account> selectByUserId(@Param("userId") Integer userId);
}
