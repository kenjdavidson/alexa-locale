package kjd.alexa.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides common JSON utility methods for the Locale project.
 * 
 * @author kendavidson
 *
 * @since 1.0.0
 */
public final class JsonUtils {
	
	private ObjectMapper mapper;
	
	protected JsonUtils(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	public <T> T deserialize(byte[] content, Class<T> clazz) {		
		try {
			return mapper.readValue(content, clazz);
		} catch (IOException e) {
			return null;
		}
	}
	
	public <T> byte[] serialize(T object) {
		try {
			return mapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			return new byte[0];
		}
	}
	
	public static JsonUtilsBuilder builder() {
		return new JsonUtilsBuilder(new ObjectMapper());
	}
	
	@SuppressWarnings("unused")
	public static final class JsonUtilsBuilder {
		private ObjectMapper mapper;
		
		private JsonUtilsBuilder(ObjectMapper mapper) {
			this.mapper = mapper;
		}
					
		public JsonUtilsBuilder enable(Feature... features) {
			this.mapper.enable(features);
			return this;
		}
		
		public JsonUtilsBuilder disable(Feature...features) {
			this.mapper.disable(features);
			return this;
		}
		
		public JsonUtils build() {
			return new JsonUtils(this.mapper);
		}
	}
}
