package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PaymentMapper {
    @Insert("insert into payments values ( #{id}, #{pid}, #{from}, #{to}, #{price}, #{status} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insertNewPayment(Payment payment);

    @Update("update payments set status = #{status} where id = #{id}")
    int updatePayment(@Param("status") Integer status, @Param("id") Integer id);

    @Select("select * from payments where id = #{id}")
    @Results({
            @Result(column = "account_from", property = "accountFrom", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_to", property = "accountTo", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_from", property = "from"),
            @Result(column = "account_to", property = "to")
    })
    Payment selectById(@Param("id") Integer id);

    @Select("select * from payments where pid = #{pid}")
    @Results({
            @Result(column = "account_from", property = "accountFrom", one =
                @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_to", property = "accountTo", one =
                @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_from", property = "from"),
            @Result(column = "account_to", property = "to")
    })
    Payment selectByPid(@Param("pid") String pid);

    @Select("select * from payments where account_from = #{account} or account_to = #{account}")
    @Results({
            @Result(column = "account_from", property = "accountFrom", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_to", property = "accountTo", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_from", property = "from"),
            @Result(column = "account_to", property = "to")
    })
    List<Payment> selectAllPayments(@Param("account") String account);

    @Select("select * from payments where status = 0 order by id desc limit 1")
    @Results({
            @Result(column = "account_from", property = "accountFrom", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_to", property = "accountTo", one =
            @One(select = "com.karmanno.payments.mapper.AccountMapper.selectByNumber")),
            @Result(column = "account_from", property = "from"),
            @Result(column = "account_to", property = "to")
    })
    Payment selectLast();
}
