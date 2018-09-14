package dashboard;

public enum DashboardPanels {
	
	DRIVER_PANEL ("DriverPanel"),
	TEST_PANEL ("TestPanel"),
	MAIN ("SmartDashboard"),
	LIVE_WINDOW ("LiveWindow");

    private final String name;       

    private DashboardPanels(String s) {
        name = s;
    }

    public String toString() {
       return this.name;
    }
}