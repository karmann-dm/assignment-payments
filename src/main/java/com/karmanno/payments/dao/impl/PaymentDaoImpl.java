package com.karmanno.payments.dao.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.PaymentDao;
import com.karmanno.payments.domain.Payment;
import com.karmanno.payments.domain.PaymentStatus;
import com.karmanno.payments.mapper.PaymentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PaymentDaoImpl implements PaymentDao {
    private final SqlSessionFactory sqlSessionFactory;
    private final PaymentMapper paymentMapper;

    @Inject
    public PaymentDaoImpl(SqlSessionFactory sqlSessionFactory, PaymentMapper paymentMapper) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment create(String from, String to, Integer calculated) {
        SqlSession sqlSession = sqlSessionFactory.openSession(TransactionIsolationLevel.SERIALIZABLE);
        String pid = new Random().ints(0, 10)
                .limit(16)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""));
        int inserted = paymentMapper.insertNewPayment(
                new Payment()
                    .setFrom(from)
                    .setTo(to)
                    .setPid(pid)
                    .setPrice(calculated)
                    .setStatus(PaymentStatus.PROCESSING.getValue())
        );
        if (inserted == 1) {
            return paymentMapper.selectByPid(pid);
        }
        sqlSession.close();
        throw new RuntimeException("Could not save payment");
    }

    @Override
    public Payment updateWithStatus(Integer status, Integer paymentId) {
        SqlSession sqlSession = sqlSessionFactory.openSession(TransactionIsolationLevel.SERIALIZABLE);
        int updated = paymentMapper.updatePayment(status, paymentId);
        if (updated == 1) {
            return paymentMapper.selectById(paymentId);
        }
        sqlSession.close();
        throw new RuntimeException("Could not save payment");
    }

    @Override
    public Payment getById(Integer id) {
        return paymentMapper.selectById(id);
    }

    @Override
    public List<Payment> getAllPayments(String account) {
        return paymentMapper.selectAllPayments(account);
    }

    @Override
    public Payment getLast() {
        return paymentMapper.selectLast();
    }
}
