package dashboard;

/** 
  * in DashboardPanels you can get all panels name for Dashboard 
  * @author Matan Steinmetz.
  */
public enum DashboardPanels {
	
	DRIVER_PANEL ("DriverPanel"),
	TEST_PANEL ("LiveWindow"),
	MAIN ("SmartDashboard"),

    private final String name;       

    private DashboardPanels(String s) {
        name = s;
    }

    public String toString() {
       return this.name;
    }
}