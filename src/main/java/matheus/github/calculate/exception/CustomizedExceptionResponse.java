package matheus.github.calculate.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomizedExceptionResponse {

	private static final String CONTENT_TYPE = "application/json";

	@Autowired
	private ObjectMapper objectMapper;

	public void modifyResponse(HttpServletResponse response, ResponseEntity<ExceptionResponse> responseEntity) throws IOException {
		response.setContentType(CONTENT_TYPE);
		response.setStatus(responseEntity.getStatusCode().value());
		response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
	}
}
