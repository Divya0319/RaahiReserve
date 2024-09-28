package com.fastturtle.raahiReserve.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastturtle.raahiReserve.enums.PaymentMethod;
import com.fastturtle.raahiReserve.enums.PaymentRefType;
import com.fastturtle.raahiReserve.enums.PaymentStatus;
import com.fastturtle.raahiReserve.helpers.PaymentMethodConverter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_status", columnList = "paymentStatus")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private int paymentId;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonIgnore
    private Booking booking;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Convert(converter = PaymentMethodConverter.class)
    @Column(name = "paymentMethod")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "paymentReferenceId")
    private Integer paymentReferenceId;    //  The ID of the related entity (CardDetails, BankDetails, etc.)

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentReferenceType")
    private PaymentRefType paymentReferenceType;    //  The type of the related entity  ('CARD', 'BANK', 'USER')

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(Integer paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public PaymentRefType getPaymentReferenceType() {
        return paymentReferenceType;
    }

    public void setPaymentReferenceType(PaymentRefType paymentReferenceType) {
        this.paymentReferenceType = paymentReferenceType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
