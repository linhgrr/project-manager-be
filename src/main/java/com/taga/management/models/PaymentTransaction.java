package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "payment_transaction")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "txn_ref")
    String txnRef;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @CreatedDate
    Date createdDate;

    @UpdateTimestamp
    Date updatedDate;
}
