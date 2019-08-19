package br.com.arcom.powerbatch.models.commons.converters;

import br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.getEnum;
import static java.util.Objects.isNull;

@Converter( autoApply = true )
public class StatusProcessamentoConverter implements AttributeConverter<StatusProcessamento, Short> {

    @Override
    public Short convertToDatabaseColumn( final StatusProcessamento value ) {

        return isNull( value ) ? null : value.getValue() ;
    }

    @Override
    public StatusProcessamento convertToEntityAttribute( final Short value ) {

        return getEnum( value );
    }
}
