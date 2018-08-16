package com.cimr.boot.model.dialect;

public class FastCustomSQLDialect extends org.hibernate.dialect.MySQL5InnoDBDialect{
	
	 public FastCustomSQLDialect() {

		 super();
		 this.registerFunction("bitand", new BitAndFunction());
		 this.registerFunction("bitor", new BitOrFunction());
	}

}
