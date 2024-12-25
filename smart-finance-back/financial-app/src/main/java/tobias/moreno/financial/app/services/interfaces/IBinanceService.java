package tobias.moreno.financial.app.services.interfaces;

import tobias.moreno.financial.app.dto.ResponseKlineDataDto;
import tobias.moreno.financial.app.dto.ResponseTickerDto;

import java.util.List;

public interface IBinanceService {
	String getSymbol(String symbol);

	List<ResponseTickerDto> getTickers();

	List<ResponseTickerDto> searchTickers(String query);
	List<ResponseKlineDataDto> getKlineData(String symbol,String interval,String limit);
}
