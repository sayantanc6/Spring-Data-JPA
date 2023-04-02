package dummy.config;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import dummy.model.AuthorModel;

public class AuthorModelListDeser implements JsonDeserializer<List<AuthorModel>> {

	@Override
	public List<AuthorModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
 
		return new Gson().fromJson(json, new TypeToken<List<AuthorModel>>(){}.getType()); 
	}

}
