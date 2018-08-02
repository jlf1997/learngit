package com.cimr.boot.utils;

public class  IdGener {
	
	private SnowflakeIdWorker idForRejectOrder;
	private SnowflakeIdWorker idForOrder;
	private SnowflakeIdWorker id;

	
    private IdGener(){
    	idForOrder = new SnowflakeIdWorker(0, 0);
    	idForRejectOrder = new SnowflakeIdWorker(1, 0);
    	id = new SnowflakeIdWorker(2, 0);
    }
    public static IdGener getInstance(){
        return Singleton.INSTANCE.getInstance();
    }
    
    private static enum Singleton{
        INSTANCE;
        
        private IdGener singleton;
        //JVM会保证此方法绝对只调用一次
        private Singleton(){
            singleton = new IdGener();
        }
        public IdGener getInstance(){
            return singleton;
        }
    }
    
    /**
     * 获取订单编号
     * @return
     */
    public  long getOrderId() {
    	return idForOrder.nextId();
    }
    /**
     * 获取退货单号
     * @return
     */
    public  long getRejectOrderId() {
    	return idForRejectOrder.nextId();
    }
    
    public long getNormalId() {
    	return id.nextId();
    }
	
	
	


	    
}
