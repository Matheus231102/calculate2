package matheus.github.calculate.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExceptionResponse {
	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
}
