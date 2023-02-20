package com.capstone.payment.controller;

import com.amazonaws.Response;
import com.capstone.payment.dto.CallbackXenditDTO;
import com.capstone.payment.dto.ResponseDto;
import com.capstone.payment.entity.PaymentEntity;
import com.capstone.payment.entity.ResponseMessage;
import com.capstone.payment.service.PaymentService;
import com.xendit.model.EWalletCharge;
import com.xendit.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class PaymentController {
    @Autowired()
    PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<ResponseDto> checkout(@RequestBody PaymentEntity request) {
        try {
            Invoice charge = paymentService.checkout(request);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully Checkout!", charge), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseDto(403, "failed checkout", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/statusPayment/{paymentId}")
    public ResponseEntity<ResponseDto> statusPayment(@PathVariable("paymentId") int id) {
        try {
            PaymentEntity statusPayment = paymentService.statusPayment(id);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully get status payment", statusPayment), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseDto(403, "failed get status Payment", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/xendit")
    public ResponseEntity<ResponseDto> xenditCallback(@RequestBody CallbackXenditDTO request) {
        try {
            paymentService.callbackXendit(request);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully Callback", new ResponseMessage("Success Callback")), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ini error "+e);
            return new ResponseEntity<>(new ResponseDto(403, "failed callback xendit", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

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
