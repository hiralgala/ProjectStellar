package com.example.restservice;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class SnippetController {

    ExpiringMap<String, SnippetModel> map = ExpiringMap.builder().variableExpiration().build();
//    map.put("foo", "bar", ExpirationPolicy.ACCESSED, 5, TimeUnit.MINUTES);
//    map.put("baz", "pez", ExpirationPolicy.CREATED, 10, TimeUnit.MINUTES);

    @GetMapping(path = "/snippets/{name}")
    public ResponseEntity<String> getSnippet(@PathVariable String name) {
        if(map.containsKey(name)) {
            map.setExpiration(name, 30, TimeUnit.SECONDS);
            SnippetModel s = map.get(name);
            s.accessSnippet();

            return new ResponseEntity<String>(new GsonBuilder().create().toJson(s), HttpStatus.OK);
        }
        String error = "Not Found";
        return new ResponseEntity<String>(new GsonBuilder().create().toJson(error), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/snippets", method = RequestMethod.POST)
    public ResponseEntity<String> postSnippet(@RequestBody String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        int expires_in = jsonObject.get("expires_in").getAsInt();
        String snippet = jsonObject.get("snippet").getAsString();

        SnippetModel s = new SnippetModel(name, expires_in, snippet);
        map.put(name, s, ExpirationPolicy.CREATED, expires_in, TimeUnit.SECONDS);
        return new ResponseEntity<String>(new GsonBuilder().create().toJson(s), HttpStatus.CREATED);
    }
}
