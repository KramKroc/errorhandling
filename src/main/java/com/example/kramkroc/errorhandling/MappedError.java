package com.example.kramkroc.errorhandling;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO defining the details of an exception instance.
 */
@XmlRootElement(name = "mappedError")
public class MappedError {

    private static final Logger LOG = LoggerFactory.getLogger(MappedError.class);

    @XmlElement(name = "message")
    private final String message;

    @XmlElement(name = "statusCode")
    private final int statusCode;

    /**
     * To allow deserialisation in jaxb
     */
    public MappedError() {
        this(null, 0);
    }

    /**
     * JSON creator for Jackson
     */
    @JsonCreator
    public MappedError(@JsonProperty("message") final String message,
                       @JsonProperty("statusCode") final int statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
        LOG.info(">>> {}", this);
    }

    /**
     * @return the error message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * @return the HTTP status code e.g. 401
     */
    @JsonProperty("statusCode")
    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus httpStatusCode() {
        return HttpStatus.valueOf(statusCode);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("MappedError [message=")
               .append(message)
               .append(", statusCode=")
               .append(statusCode)
               .append("]");
        return builder.toString();
    }
}
