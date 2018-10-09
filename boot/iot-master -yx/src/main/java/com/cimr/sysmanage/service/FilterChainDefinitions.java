package com.cimr.sysmanage.service;

public abstract interface FilterChainDefinitions
{
  public abstract String loadFilterChainDefinitions();
  
  public abstract void reCreateFilterChains();
}
