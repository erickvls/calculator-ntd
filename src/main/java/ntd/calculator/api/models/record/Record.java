package ntd.calculator.api.models.record;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.user.User;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal userBalance;
    @Column(nullable = false)
    private String operationResponse;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private boolean deleted;
}
