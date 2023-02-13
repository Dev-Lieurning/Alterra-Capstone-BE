package com.capstone.payment.controller;

import com.amazonaws.Response;
import com.capstone.payment.dto.ResponseDto;
import com.capstone.payment.entity.PaymentEntity;
import com.capstone.payment.entity.ResponseMessage;
import com.capstone.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/getAllPayments")
    public ResponseEntity<ResponseDto> getAllPayments() {
        try {
            List<PaymentEntity> listPayments = paymentService.getAllPayments();
            if(listPayments.size() < 1) {
                throw new Exception();
            } else {
                ResponseDto response = new ResponseDto(200, "Successfully get all Payments!!", listPayments);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getPayment/{paymentId}")
    public ResponseEntity<ResponseDto> getPaymentById(@PathVariable("paymentId") int id) {
        try {
            PaymentEntity payment = paymentService.getPaymentById(id);
            ResponseDto response = new ResponseDto(200, "Successfully get payment!!", payment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addPayment")
    public ResponseEntity<ResponseDto> addPayment(@RequestBody PaymentEntity payment) {
        try {
            PaymentEntity response = paymentService.addPayment(payment);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully add Payment!", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed add Payment", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deletePayment/{paymentId}")
    public ResponseEntity<ResponseDto> deletePayment(@PathVariable("paymentId") int id) {
        try {
            ResponseMessage response = paymentService.deletePayment(id);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully delete Payment!", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed delete Payment", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updatePayment")
    public ResponseEntity<ResponseDto> updatePayment(@RequestBody PaymentEntity payment) {
        try {
            PaymentEntity response = paymentService.updatePayment(payment);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully update Payment!", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed update Payment", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }
}
