package dashboard;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Dashboard {
	public static void putNumber(DashboardPanels PanelName, String key, double value){
		NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).setNumber(value);
    }  
    
    public static void putString(DashboardPanels PanelName, String key, String value){
		NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).setString(value);
    }  

    public static void putBoolean(DashboardPanels PanelName, String key, boolean value){
		NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).setBoolean(value);
    }  
    
    public static double getNumber(DashboardPanels PanelName, String key, double defaultValue){
		return NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).getDouble(defaultValue);
    }  
    
    public static String getString(DashboardPanels PanelName, String key, String defaultValue){
		return NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).getString(defaultValue);
    } 
    
    public static boolean getBoolean(DashboardPanels PanelName, String key, boolean defaultValue){
		return NetworkTableInstance.getDefault().getTable(PanelName.toString()).getEntry(key).getBoolean(defaultValue);
	} 
}
