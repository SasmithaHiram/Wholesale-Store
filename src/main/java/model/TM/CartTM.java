package model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartTM {
    private String code;
    private String discription;
    private int qty;
    private Double unitPrice;
    private Double total;
}
