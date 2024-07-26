package com.secret.platform.rate_product;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateSetDeserializer extends JsonDeserializer<RateSet> {

    @Autowired
    private RateSetRepository rateSetRepository; // Replace with your actual repository

    @Override
    public RateSet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Long id = node.asLong();
        return rateSetRepository.findById(id).orElse(null); // Implement your repository method
    }
}
