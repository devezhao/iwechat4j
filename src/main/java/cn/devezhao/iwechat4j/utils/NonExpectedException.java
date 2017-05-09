package cn.devezhao.iwechat4j.utils;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class NonExpectedException extends NestableRuntimeException {
	private static final long serialVersionUID = -537097522546875791L;

	public NonExpectedException() {
		super();
	}

	public NonExpectedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NonExpectedException(String msg) {
		super(msg);
	}

	public NonExpectedException(Throwable cause) {
		super(cause);
	}
}
