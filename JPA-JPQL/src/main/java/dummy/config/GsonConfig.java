package dummy.config;

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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dummy.model.AuthorModel;
import dummy.model.BookModel;

@Configuration
@EnableWebMvc
public class GsonConfig implements WebMvcConfigurer{

	@Bean
	public Gson gson() {
		return new GsonBuilder()
				.setObjectToNumberStrategy(new MyNumberStrategy())
				.addSerializationExclusionStrategy(new ExcludeNullStrategy())
				.registerTypeAdapter(new TypeToken<List<AuthorModel>>() {}.getType(), new AuthorModelListDeser())
				.registerTypeAdapter(new TypeToken<List<BookModel>>() {}.getType(), new BookModelListDeser())
				.create();
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { 
	    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setWriteAcceptCharset(false);
	    stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
	    converters.add(stringConverter);
	    converters.add(new ByteArrayHttpMessageConverter());
	    converters.add(new SourceHttpMessageConverter<>());
	    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
	    gsonHttpMessageConverter.setGson(gson());
	    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
	    converters.add(gsonHttpMessageConverter);
	}
}
