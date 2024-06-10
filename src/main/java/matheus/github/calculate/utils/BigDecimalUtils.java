package matheus.github.calculate.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalUtils {

	public boolean isGreaterThan(BigDecimal bd1, BigDecimal bd2) {
		return bd1.compareTo(bd2) > 0;
	}

	public boolean isLessThan(BigDecimal bd1, BigDecimal bd2) {
		return bd1.compareTo(bd2) < 0;
	}

	public boolean isEqual(BigDecimal bd1, BigDecimal bd2) {
		return bd1.compareTo(bd2) == 0;
	}

}
