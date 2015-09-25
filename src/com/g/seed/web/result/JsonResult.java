package com.g.seed.web.result;

import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;

import com.g.seed.util.Delegate;
import com.g.seed.web.exception.DataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class JsonResult extends Result {
	private static final long serialVersionUID = 1L;
	private JsonElement data;
	
	public JsonResult(String resultCode, String resultDesc) {
		super(resultCode, resultDesc);
	}
	
	public Gson gson() {
		return new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanDeserializer()).create();
	}
	
	private JsonElement getNoNullData() {
		if (data == null)
			throw new DataException("We need non null data, but the data is null.", null);
		return data;
	}
	
	public JsonElement getData() {
		return data;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
	public <T> T getData(final JsonElement je, final Class<T> clazz) {
		return exceptionCatch(new Delegate<T>() {
			@Override
			public T invoke() {
				return gson().fromJson(je, clazz);
			}
		});
	}
	
	public <T> T getData(Class<T> clazz) {
		return getData(getNoNullData(), clazz);
	}
	
	public <T> T getData(String name, Class<T> clazz) {
		JsonObject jo = getNoNullData().getAsJsonObject();
		return getData(jo.get(name), clazz);
	}
	
	public <T> T getData(final String name, final Type type) {
		return exceptionCatch(new Delegate<T>() {
			@Override
			public T invoke() {
				return gson().fromJson(getNoNullData().getAsJsonObject().get(name), type);
				
			}
		});
	}
	
	public <T> T getData(final Type type) {
		return exceptionCatch(new Delegate<T>() {
			@Override
			public T invoke() {
				return gson().fromJson(getNoNullData(), type);
			}
		});
	}
	
	public <T> T exceptionCatch(Delegate<T> delegate) {
		try {
			T data = delegate.invoke();
			if (nonNull && data == null)
				throw new DataException("expected nonNull but was null", null);
			return data;
		} catch (JsonParseException e) {
			throw new DataException(e, String.valueOf(getData()));
		}
	}
	
	public void setData(JsonElement jsondata) {
		this.data = jsondata;
	}
	
	public JsonResult nonNull(boolean nonNull) {
		this.nonNull = nonNull;
		return this;
	}
	
	public boolean isNonNull() {
		return this.nonNull;
	}
	
	class BooleanDeserializer implements JsonDeserializer<Boolean> {
		
		@Override
		public Boolean deserialize(JsonElement json, Type targetType, JsonDeserializationContext jdc) throws JsonParseException {
			try {
				String value = json.getAsJsonPrimitive().getAsString();
				if (NumberUtils.isNumber(value))
					return Boolean.valueOf(value.toLowerCase(Locale.getDefault()).equals("1"));
				return ((Boolean) jdc.deserialize(json, targetType));
			} catch (ClassCastException e) {
				throw new JsonParseException("Cannot parse json data '" + json.toString() + "'", e);
			}
		}
	}
}
