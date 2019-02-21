package cn.lemon.proxy;

import java.io.Serializable;

public class RpcResponseProxy implements Serializable{
	private int id;
	private String msg;
	private Object resultObj;
	private int ststus;

	public static Builder Builder() {
		return new Builder();
	}

	public static class Builder {
		private RpcResponseProxy proxy = new RpcResponseProxy();

		public Builder Id(int id) {
			proxy.id = id;
			return this;
		}

		public Builder Msg(String msg) {
			proxy.msg = msg;
			return this;
		}

		public Builder Status(int ststus) {
			proxy.ststus = ststus;
			return this;
		}

		public Builder Result(Object result) {
			proxy.resultObj = result;
			return this;
		}

		public RpcResponseProxy Build() {
			return this.proxy;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResultObj() {
		return resultObj;
	}

	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}

	public int getStstus() {
		return ststus;
	}

	public void setStstus(int ststus) {
		this.ststus = ststus;
	}

}
