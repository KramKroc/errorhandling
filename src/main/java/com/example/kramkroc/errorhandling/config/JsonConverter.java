package com.example.kramkroc.errorhandling.config;

import java.lang.reflect.Type;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * Delivers an {@link org.springframework.http.converter.HttpMessageConverter} bean to convert
 * between schema-generated POJOs and JSON.
 * <p>
 * The converter is configured to expect a 'root' value and to honour JAXB annotations.
 */

@Configuration
public class JsonConverter extends MappingJackson2HttpMessageConverter {
    private static final Logger LOG = LoggerFactory.getLogger(JsonConverter.class);

    private static final AnnotationIntrospector JAXB_ANNOTATION_INTROSPECTOR = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());

    @Bean
    public MappingJackson2HttpMessageConverter jsonMessageConverter() {
        LOG.debug(">>> jsonMessageConverter");
        final MappingJackson2HttpMessageConverter converter = new JsonConverter();
        converter.setObjectMapper(jsonObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.setAnnotationIntrospector(JAXB_ANNOTATION_INTROSPECTOR);
        return mapper;
    }

    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        return super.canRead(mediaType) && checkForXMLAnnotation(clazz);
    }

    @Override
    public boolean canWrite(final Type type, final Class<?> clazz, final MediaType mediaType) {
        return super.canWrite(mediaType) && checkForXMLAnnotation(clazz);
    }

    private static boolean checkForXMLAnnotation(final Class<?> clazz) {
        boolean result = false;

        if (clazz.isAnnotationPresent(XmlRootElement.class)) {
            result = true;
        }

        LOG.warn("Checking if XMLRootElement annotation found in class - returning {}", result);

        return result;
    }

}
