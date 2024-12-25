package tobias.moreno.financial.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseTickerDto {
	private String symbol;
	private BigDecimal price;
}
