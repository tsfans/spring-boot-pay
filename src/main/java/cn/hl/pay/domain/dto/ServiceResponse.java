package cn.hl.pay.domain.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author HULIN
 * @date 2018年4月24日
 */
public class ServiceResponse<T> extends ResponseEntity<T> {

	public ServiceResponse(T data) {
		super(data,HttpStatus.OK);
	}
	
	public ServiceResponse(T data, HttpStatus status) {
		super(data, status);
	}

}
