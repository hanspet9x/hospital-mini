package org.oze.hospital.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public class Helper {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Data
	@RequiredArgsConstructor
	@JsonInclude(Include.NON_NULL)
	public class ResponseMessage {
		private boolean hasError;
		private int statusCode;
		private Object message, description;
		private Object data, otherData;

		public ResponseMessage(boolean hasError, Object message) {
			this.hasError = hasError;
			this.message = message;
		}

		public ResponseMessage(int statusCode, Boolean hasError, Object description) {
			this.statusCode = statusCode;
			this.hasError = hasError;
			this.description = description;
		}

		public ResponseMessage(Object message, Object data) {
			this.message = message;
			this.data = data;
			this.hasError = false;
		}

		public ResponseMessage(boolean hasError, Object message, Object description, Object... otherData) {
			this.hasError = hasError;
			this.message = message;
			this.description = description;
			this.otherData = otherData;
		}
	}

	public static ResponseEntity<?> ServerResponse(HttpStatus status, Boolean hasError, Object message,
			Object description) {
		ResponseMessage body = new Helper().new ResponseMessage(hasError, message, description);
		return new ResponseEntity<ResponseMessage>(body, status);
	}

	public static ResponseEntity<ResponseMessage> HealthyServerResponse(Object message) {
		ResponseMessage body = new Helper().new ResponseMessage(false, message);
		return new ResponseEntity<ResponseMessage>(body, HttpStatus.OK);
	}

	public static ResponseEntity<?> HealthyServerResponse(Object message, Object data) {
		ResponseMessage body = new Helper().new ResponseMessage(message, data);
		return new ResponseEntity<ResponseMessage>(body, HttpStatus.OK);
	}

	public static ResponseEntity<?> UnhealthyServerResponse(HttpStatus status, Object message, Object otherData) {
		ResponseMessage body = new Helper().new ResponseMessage(true, message, otherData);
		return new ResponseEntity<ResponseMessage>(body, status);
	}

	public static ResponseEntity<ResponseMessage> ConflictResponse(Object message) {
		ResponseMessage body = new Helper().new ResponseMessage(true, message);
		return new ResponseEntity<ResponseMessage>(body, HttpStatus.CONFLICT);
	}

	public static ResponseEntity<ResponseMessage> ServerResponse(Exception e) {
		ResponseMessage body = new Helper().new ResponseMessage(true, e.getMessage());
		return new ResponseEntity<ResponseMessage>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static String anyToString(Object arg) throws Exception {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arg);
	}

	public static <T> T stringToAny(String arg, Class<T> cl) {
		try {
			return mapper.readValue(arg, cl);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
