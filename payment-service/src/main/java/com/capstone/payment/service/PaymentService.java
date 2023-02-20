package com.capstone.payment.service;

import com.capstone.payment.dto.CallbackXenditDTO;
import com.capstone.payment.entity.PaymentEntity;
import com.capstone.payment.entity.ResponseMessage;
import com.capstone.payment.repository.PaymentRepository;
import com.xendit.Xendit;
import com.xendit.exception.XenditException;
import com.xendit.model.AvailableBank;
import com.xendit.model.Disbursement;
import com.xendit.model.EWalletCharge;
import com.xendit.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired()
    PaymentRepository paymentRepository;

    @Value("${XenditKey}")
    private String xenditKey;

    public Invoice checkout(PaymentEntity payment) {
        try {
            List<PaymentEntity> listPayment = paymentRepository.findByIdReservation(payment.getId_reservation());
            if(listPayment.size() < 1) {
                PaymentEntity paymentSave = paymentRepository.save(payment);
                Xendit.Opt.setApiKey(xenditKey);
                Map<String, Object> params = new HashMap<>();
                params.put("external_id", ""+paymentSave.getId());
                params.put("amount", payment.getAmount());


                Invoice invoice = Invoice.create(params);
                paymentSave.setStatus(invoice.getStatus());
                paymentSave.setReceipe(invoice.getInvoiceUrl());
                paymentRepository.save(paymentSave);

                return invoice;
            }
            return null;
        } catch (XenditException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void callbackXendit(CallbackXenditDTO callbackXenditDTO) {
        paymentRepository.updateStatusById(callbackXenditDTO.getStatus(), callbackXenditDTO.getExternal_id());
    }

    public PaymentEntity statusPayment(int id) {
        try {
            Xendit.Opt.setApiKey(xenditKey);
            PaymentEntity payment = paymentRepository.findById(id).get();
            // EWalletCharge statusPayment = EWalletCharge.getEWalletChargeStatus(payment.getId_xendit());

            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
