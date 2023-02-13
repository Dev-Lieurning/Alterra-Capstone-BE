package com.capstone.payment.service;

import com.capstone.payment.entity.PaymentEntity;
import com.capstone.payment.entity.ResponseMessage;
import com.capstone.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired(required = false)
    PaymentRepository paymentRepository;

    public List<PaymentEntity> getAllPayments() {
        List<PaymentEntity> paymentList = paymentRepository.findAll();
        return paymentList;
    }

    public PaymentEntity getPaymentById(Integer id) {
        PaymentEntity payment = paymentRepository.findById(id).get();
        return payment;
    }

    public PaymentEntity addPayment(PaymentEntity payment) {
        PaymentEntity addPayment = paymentRepository.save(payment);
        return addPayment;
    }

    public PaymentEntity updatePayment(PaymentEntity payment) {
        PaymentEntity updatePayment = paymentRepository.save(payment);
        return updatePayment;
    }

    public ResponseMessage deletePayment(int id) {
        paymentRepository.deleteById(id);
        return new ResponseMessage("Payment Deleted!!");
    }
}
