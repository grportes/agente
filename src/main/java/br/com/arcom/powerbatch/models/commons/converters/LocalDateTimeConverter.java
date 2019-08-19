package br.com.arcom.powerbatch.models.commons.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    @Override
    public String convertToDatabaseColumn( final LocalDateTime value ) {

        return isNull( value ) ? null : value.toString();
    }

    @Override
    public LocalDateTime convertToEntityAttribute( final String value ) {

        return isNull( value ) ? null : parse(value);
    }
}
