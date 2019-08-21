package br.com.arcom.powerbatch.infra.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public final class UtilDate {

    private static final DateTimeFormatter FORMATO = ofPattern("dd/MM/yy hh:mm");

    public static String convString( final LocalDateTime dataHora ) {

        return dataHora != null ? dataHora.format(FORMATO) : "";
    }
}
