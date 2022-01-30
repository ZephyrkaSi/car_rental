package by.itacademy.javaenterprise.carrental.silina.repository.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPAIR_INVOICE")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "INVOICE_DATE")
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    @ToString.Exclude
    private User client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID")
    @ToString.Exclude
    private Car car;
    @Column(name = "DAMAGE_INFO")
    private String damageInfo;
    @Column(name = "COST")
    private BigDecimal cost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    @ToString.Exclude
    private User admin;
    @Column(name = "ADD_INFO")
    private String addInfo;
    //TODO ?
    @Column(name = "IS_PAID")
    private boolean isPaid;
}
