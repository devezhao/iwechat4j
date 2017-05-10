package cn.devezhao.iwechat4j.utils;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 获取到非正常/期望结果
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class UnexpectedResultException extends NestableRuntimeException {
	private static final long serialVersionUID = -537097522546875791L;

	public UnexpectedResultException() {
		super();
	}

	public UnexpectedResultException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnexpectedResultException(String msg) {
		super(msg);
	}

	public UnexpectedResultException(Throwable cause) {
		super(cause);
	}
}
