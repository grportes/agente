package br.com.arcom.powerbatch.infra.dialect;

import org.hibernate.dialect.SQLiteDialect;

import java.sql.Types;

public class AgenteSQLiteDialect extends SQLiteDialect {

    public AgenteSQLiteDialect() {

        super();

        registerColumnType(Types.NULL, "null");
        registerHibernateType(Types.NULL, "null");
    }
}
