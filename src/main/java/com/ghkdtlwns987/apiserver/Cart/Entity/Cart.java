package com.ghkdtlwns987.apiserver.Cart.Entity;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "CartDto")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "userId", nullable = false)
    private String userId;


    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "unitPrice", nullable = false)
    private Integer unitPrice;

    @Column(name = "createAt", nullable = false, updatable = false)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;

    @Column(name = "modifiedAt", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime expiredAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private Member member;

    @Builder
    public Cart(Long Id, String productId, String productName, Integer qty, Integer unitPrice, LocalDateTime createAt, Member member){
        this.Id = Id;
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.expiredAt = createAt.plusMinutes(30);
        this.member = member;
    }
}