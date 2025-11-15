package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "stock")
public class Stock{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int id;

    @Column(name = "stock_amount")
    private int amount;


    @ManyToOne
    @JoinColumn(
            name = "stock_kio_id",
            referencedColumnName = "kio_id",
            nullable = true
    )
    private Kiosk kiosk;

    @ManyToOne
    @JoinColumn(
            name = "stock_pro_id",
            referencedColumnName = "pro_id",
            nullable = true
    )
    private Product product;
}
