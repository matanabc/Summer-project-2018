package utils;

public class DashBoard {

	
	
	
	
	  private abstract static class BasePanel{
	    protected static String tableName="BASE_TABLE";

	    public static void putNumber(String key, double value){
	      //NetworkTableInstance.getDefault().getTable(tableName).getEntry(key).setNumber(value);
	    }     
	  }

	  public static class DriverPanel extends BasePanel{
	    public DriverPanel(){
	      tableName = "DRIVER_TABLE";
	    }
	 }

	 public static class TestPanel extends BasePanel{
	    public TestPanel(){
	      tableName = "TEST_TABLE";
	    }
	  }
	 

}


