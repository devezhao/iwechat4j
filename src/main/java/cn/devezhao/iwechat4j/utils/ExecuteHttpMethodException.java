package cn.devezhao.iwechat4j.utils;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 执行 HTTP 失败
 * 
 * @author zhaofang123@gmail.com
 * @since 05/01/2017
 */
public class ExecuteHttpMethodException extends NestableRuntimeException {
	private static final long serialVersionUID = 1350464686207313368L;

	public ExecuteHttpMethodException(String message) {
		super(message);
	}

	public ExecuteHttpMethodException(Throwable ex) {
		super(ex);
	}

	public ExecuteHttpMethodException(String message, Throwable ex) {
		super(message, ex);
	}
}
