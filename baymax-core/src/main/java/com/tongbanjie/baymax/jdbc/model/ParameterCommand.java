package com.tongbanjie.baymax.jdbc.model;

/**
 * 参数上下文
 * 
 * <pre>
 * 包含两个对象：
 * 1. parameterMethod枚举类型，表明对应的set方法类型，比如setString
 * 2. args数组，代表传递给setXXX的参数内容
 * </pre>
 * 
 */
public class ParameterCommand {

	/**
	 * setString()等方法
	 */
	private ParameterMethod parameterMethod;
	
	/**
	 * 用于执行真正statement方法的参数 args[0]: parameterIndex args[1]: 参数值 args[2]: length
	 * 适用于：setAsciiStream、setBinaryStream、setCharacterStream、setUnicodeStream
	 * 。。。
	 */
	private Object[] args;

	/**
	 * 保存的用于路由的参数
	 */
	private Object parttionArg;

	public ParameterCommand() {
	}

	/**
	 * 给ParameterStatement设置的SQL参数
	 * 
	 * @param parameterMethod
	 * @param args
	 * @param parttionArg
	 *            如果这个参数在路由表达式中需要用到,则设置这个参数的值
	 */
	public ParameterCommand(ParameterMethod parameterMethod, Object[] args, Object parttionArg) {
		this.parameterMethod = parameterMethod;
		this.args = args;
		this.parttionArg = parttionArg;
	}

	public ParameterCommand(ParameterMethod parameterMethod, Object[] args) {
		this(parameterMethod, args, null);
	}

	public ParameterMethod getParameterMethod() {
		return parameterMethod;
	}

	public void setParameterMethod(ParameterMethod parameterMethod) {
		this.parameterMethod = parameterMethod;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(parameterMethod).append("(");
		if(args != null && args.length > 0){
			for (int i = 0; i < args.length; ++i) {
				buffer.append(args[i]);
				if (i != args.length - 1) {
					buffer.append(", ");
				}
			}
		}
		buffer.append(")");
		return buffer.toString();
	}

	public Object getParttionArg() {
		return parttionArg;
	}

	public void setParttionArg(Object parttionArg) {
		this.parttionArg = parttionArg;
	}
}
