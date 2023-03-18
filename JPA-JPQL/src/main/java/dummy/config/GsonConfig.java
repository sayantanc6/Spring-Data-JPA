package dummy.config;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

@Configuration
public class GsonConfig implements WebMvcConfigurer{

	@Bean 
	public Gson gson() { 
	    return new GsonBuilder().registerTypeAdapterFactory(DateTypeAdapter.FACTORY)
				    .registerTypeAdapterFactory(SqlDateTypeAdapter.FACTORY)
				    .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
					@Override
					public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
					    Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
					    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
					}
				    })
				   .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
					@Override
						public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
						return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
					}
				    })
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { 
	    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setWriteAcceptCharset(false);
	    stringConverter.setSupportedMediaTypes(Collections
	        .singletonList(MediaType.TEXT_PLAIN));
	    converters.add(stringConverter);
	    converters.add(new ByteArrayHttpMessageConverter());
	    converters.add(new SourceHttpMessageConverter<>());
	    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
	    gsonHttpMessageConverter.setGson(gson());
	    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays
	        .asList(MediaType.APPLICATION_JSON));
	    converters.add(gsonHttpMessageConverter);
	}
}
