package br.com.arcom.powerbatch.models.commons.constantes;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

public enum StatusProcessamento {

    AGUARDANDO((short) 1),
    EXECUCAO((short) 2),
    ERRO((short) 3),
    FINALIZADO((short) 3)
    ;

    private Short value;

    StatusProcessamento(Short value) {
        this.value = value;
    }

    public Short getValue() {

        return value;
    }

    public static StatusProcessamento getEnum( final Short value ) {

         return isNull(value)
            ? null
            : Arrays
                .stream( StatusProcessamento.values() )
                .filter( statusProcessamento -> Objects.equals(statusProcessamento.getValue(), value) )
                .findFirst()
                .orElse( null );
    }

    public static Short getValor( final StatusProcessamento statusProcessamento ) {

        return isNull( statusProcessamento ) ? null : statusProcessamento.getValue();
    }
}
