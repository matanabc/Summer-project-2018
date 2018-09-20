package dashboard;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;

/**
 * The {@link Dashboard} class is the bridge between robot programs and the Dashboard on
 * the laptop.
 *
 * <p>When a value is put into the Dashboard here, it pops up on the Dashboard on the
 * laptop. Users can put values into and get values from the Dashboard.
 * 
 * 
 * @author Matan Steinmetz.
 */
public class Dashboard {

	private static class Data {
		Data(Sendable sendable) {
			m_sendable = sendable;
		}

		final Sendable m_sendable;
		final SendableBuilderImpl m_builder = new SendableBuilderImpl();
	}

	/**
	 * A table linking tables in the SmartDashboard to the {@link Sendable} objects they
	 * came from.
	 */
	private static final Map<String, Data> tablesToDataDriverPanel = new HashMap<>();
	private static final Map<String, Data> tablesToDataTestPanel = new HashMap<>();
	private static final Map<String, Data> tablesToDataMainPanel = new HashMap<>();


	/**
	 * Maps the specified key to the specified value in this table. The key can not be null. The value
	 * can be retrieved by calling the get method with a key that is equal to the original key.
	 *
	 * @param key  the key
	 * @param data the value
	 * @throws IllegalArgumentException If key is null
	 */
	public static synchronized void putData(DashboardPanels panelName, String key, Sendable data) {
		Data sddata = null;//tablesToData.get(key);4
		Map<String, Data> tablesToData;
		
		if(DashboardPanels.DRIVER_PANEL.toString().equals(panelName.toString())) {
			sddata = tablesToDataDriverPanel.get(key);
			tablesToData = tablesToDataDriverPanel;
		} else if(DashboardPanels.TEST_PANEL.toString().equals(panelName.toString())) {
			sddata = tablesToDataTestPanel.get(key);
			tablesToData = tablesToDataTestPanel;
		} else {
			sddata = tablesToDataMainPanel.get(key);
			tablesToData = tablesToDataMainPanel;
		}
		
		if (sddata == null || sddata.m_sendable != data) {
			if (sddata != null) {
				sddata.m_builder.stopListeners();
			}
			sddata = new Data(data);
			tablesToData.put(key, sddata);
			NetworkTable dataTable = NetworkTableInstance.getDefault().getTable(panelName.toString()).getSubTable(key);
			sddata.m_builder.setTable(dataTable);
			data.initSendable(sddata.m_builder);
			sddata.m_builder.updateTable();
			sddata.m_builder.startListeners();
			dataTable.getEntry(".name").setString(key);
		}
	}

	/**
	 * Maps the specified key (where the key is the name of the {@link NamedSendable}
	 * to the specified value in this table. The value can be retrieved by
	 * calling the get method with a key that is equal to the original key.
	 *
   * @param panelName the panel
	 * @param value the value
	 * @throws IllegalArgumentException If key is null
	 */
	public static void putData(DashboardPanels panelName, Sendable value) {
		putData(panelName, value.getName(), value);
	}

	/**
	 * Returns the value at the specified key.
	 *
	 * @param key the key
	 * @return the value
	 * @throws IllegalArgumentException  if the key is null
	 */
	public static synchronized Sendable getData(DashboardPanels panelName, String key) {		
		Data data = null;//tablesToData.get(key);
		
		if(DashboardPanels.DRIVER_PANEL.toString().equals(panelName.toString())) {
			data = tablesToDataDriverPanel.get(key);
		} else if(DashboardPanels.TEST_PANEL.toString().equals(panelName.toString())) {
			data = tablesToDataTestPanel.get(key);
		} else {
			data = tablesToDataMainPanel.get(key);
		}
		
		if (data == null) {
			throw new IllegalArgumentException(panelName.toString() + " data does not exist: " + key);
		} else {
			return data.m_sendable;
		}
	}

	/**
	 * Gets the entry for the specified key.
   * @param panelName the panel
	 * @param key the key name
	 * @return Network table entry.
	 */
	public static NetworkTableEntry getEntry(DashboardPanels panelName, String key) {
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key);
	}

	/**
	 * Checks the table and tells if it contains the specified key.
	 *
   * @param panelName the panel
	 * @param key the key to search for
	 * @return true if the table as a value assigned to the given key
	 */
	public static boolean containsKey(DashboardPanels panelName, String key) {
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).containsKey(key);
	}

	/**
	 * Get the keys stored in the SmartDashboard table of NetworkTables.
	 *
   * @param panelName the panel
	 * @param types bitmask of types; 0 is treated as a "don't care".
	 * @return keys currently in the table
	 */
	public static Set<String> getKeys(DashboardPanels panelName, int types) {
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getKeys(types);
	}

	/**
	 * Get the keys stored in the SmartDashboard table of NetworkTables.
	 *
   * @param panelName the panel
	 * @return keys currently in the table.
	 */
	public static Set<String> getKeys(DashboardPanels panelName) {
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getKeys();
	}

	/**
	 * Makes a key's value persistent through program restarts.
	 * The key cannot be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 */
	public static void setPersistent(DashboardPanels panelName, String key) {
		getEntry(panelName, key).setPersistent();
	}

	/**
	 * Stop making a key's value persistent through program restarts.
	 * The key cannot be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 */
	public static void clearPersistent(DashboardPanels panelName, String key) {
		getEntry(panelName, key).clearPersistent();
	}

	/**
	 * Returns whether the value is persistent through program restarts.
	 * The key cannot be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 * @return True if the value is persistent.
	 */
	public static boolean isPersistent(DashboardPanels panelName, String key) {
		return getEntry(panelName, key).isPersistent();
	}

	/**
	 * Sets flags on the specified key in this table. The key can
	 * not be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 * @param flags the flags to set (bitmask)
	 */
	public static void setFlags(DashboardPanels panelName, String key, int flags) {
		getEntry(panelName, key).setFlags(flags);
	}

	/**
	 * Clears flags on the specified key in this table. The key can
	 * not be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 * @param flags the flags to clear (bitmask)
	 */
	public static void clearFlags(DashboardPanels panelName, String key, int flags) {
		getEntry(panelName, key).clearFlags(flags);
	}

	/**
	 * Returns the flags for the specified key.
	 *
   * @param panelName the panel
	 * @param key the key name
	 * @return the flags, or 0 if the key is not defined
	 */
	public static int getFlags(DashboardPanels panelName, String key) {
		return getEntry(panelName, key).getFlags();
	}

	/**
	 * Deletes the specified key in this table. The key can
	 * not be null.
	 *
   * @param panelName the panel
	 * @param key the key name
	 */
	public static void delete(DashboardPanels panelName, String key) {
		NetworkTableInstance.getDefault().getTable(panelName.toString()).delete(key);
	}

	/**
	 * Put a boolean in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putBoolean(DashboardPanels panelName, String key, boolean value) {
		return getEntry(panelName, key).setBoolean(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultBoolean(DashboardPanels panelName, String key, boolean defaultValue) {
		return getEntry(panelName, key).setDefaultBoolean(defaultValue);
	}

	/**
	 * Returns the boolean the key maps to. If the key does not exist or is of
	 *     different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static boolean getBoolean(DashboardPanels panelName, String key, boolean defaultValue) {
		return getEntry(panelName, key).getBoolean(defaultValue);
	}

	/**
	 * Put a number in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putNumber(DashboardPanels panelName, String key, double value) {
		return getEntry(panelName, key).setDouble(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultNumber(DashboardPanels panelName, String key, double defaultValue) {
		return getEntry(panelName, key).setDefaultDouble(defaultValue);
	}

	/**
	 * Returns the number the key maps to. If the key does not exist or is of
	 *     different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static double getNumber(DashboardPanels panelName, String key, double defaultValue) {
		return getEntry(panelName, key).getDouble(defaultValue);
	}

	/**
	 * Put a string in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putString(DashboardPanels panelName, String key, String value) {
		return getEntry(panelName, key).setString(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultString(DashboardPanels panelName, String key, String defaultValue) {
		return getEntry(panelName, key).setDefaultString(defaultValue);
	}

	/**
	 * Returns the string the key maps to. If the key does not exist or is of
	 *     different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static String getString(DashboardPanels panelName, String key, String defaultValue) {
		return getEntry(panelName, key).getString(defaultValue);
	}

	/**
	 * Put a boolean array in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putBooleanArray(DashboardPanels panelName, String key, boolean[] value) {
		return getEntry(panelName, key).setBooleanArray(value);
	}

	/**
	 * Put a boolean array in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putBooleanArray(DashboardPanels panelName, String key, Boolean[] value) {
		return getEntry(panelName, key).setBooleanArray(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultBooleanArray(DashboardPanels panelName, String key, boolean[] defaultValue) {
		return getEntry(panelName, key).setDefaultBooleanArray(defaultValue);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultBooleanArray(DashboardPanels panelName, String key, Boolean[] defaultValue) {
		return getEntry(panelName, key).setDefaultBooleanArray(defaultValue);
	}

	/**
	 * Returns the boolean array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static boolean[] getBooleanArray(DashboardPanels panelName, String key, boolean[] defaultValue) {
		return getEntry(panelName, key).getBooleanArray(defaultValue);
	}

	/**
	 * Returns the boolean array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static Boolean[] getBooleanArray(DashboardPanels panelName, String key, Boolean[] defaultValue) {
		return getEntry(panelName, key).getBooleanArray(defaultValue);
	}

	/**
	 * Put a number array in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putNumberArray(DashboardPanels panelName, String key, double[] value) {
		return getEntry(panelName, key).setDoubleArray(value);
	}

	/**
	 * Put a number array in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putNumberArray(DashboardPanels panelName, String key, Double[] value) {
		return getEntry(panelName, key).setNumberArray(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultNumberArray(DashboardPanels panelName, String key, double[] defaultValue) {
		return getEntry(panelName, key).setDefaultDoubleArray(defaultValue);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultNumberArray(DashboardPanels panelName, String key, Double[] defaultValue) {
		return getEntry(panelName, key).setDefaultNumberArray(defaultValue);
	}

	/**
	 * Returns the number array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static double[] getNumberArray(DashboardPanels panelName, String key, double[] defaultValue) {
		return getEntry(panelName, key).getDoubleArray(defaultValue);
	}

	/**
	 * Returns the number array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static Double[] getNumberArray(DashboardPanels panelName, String key, Double[] defaultValue) {
		return getEntry(panelName, key).getDoubleArray(defaultValue);
	}

	/**
	 * Put a string array in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putStringArray(DashboardPanels panelName, String key, String[] value) {
		return getEntry(panelName, key).setStringArray(value);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultStringArray(DashboardPanels panelName, String key, String[] defaultValue) {
		return getEntry(panelName, key).setDefaultStringArray(defaultValue);
	}

	/**
	 * Returns the string array the key maps to. If the key does not exist or is
	 *     of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static String[] getStringArray(DashboardPanels panelName, String key, String[] defaultValue) {
		return getEntry(panelName, key).getStringArray(defaultValue);
	}

	/**
	 * Put a raw value (byte array) in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putRaw(DashboardPanels panelName, String key, byte[] value) {
		return getEntry(panelName, key).setRaw(value);
	}

	/**
	 * Put a raw value (bytes from a byte buffer) in the table.
   * 
   * @param panelName the panel
	 * @param key the key to be assigned to
	 * @param value the value that will be assigned
	 * @param len the length of the value
	 * @return False if the table key already exists with a different type
	 */
	public static boolean putRaw(DashboardPanels panelName, String key, ByteBuffer value, int len) {
		return getEntry(panelName, key).setRaw(value, len);
	}

	/**
	 * Gets the current value in the table, setting it if it does not exist.
   * 
   * @param panelName the panel
	 * @param key the key
	 * @param defaultValue the default value to set if key does not exist.
	 * @return False if the table key exists with a different type
	 */
	public static boolean setDefaultRaw(DashboardPanels panelName, String key, byte[] defaultValue) {
		return getEntry(panelName, key).setDefaultRaw(defaultValue);
	}

	/**
	 * Returns the raw value (byte array) the key maps to. If the key does not
	 *     exist or is of different type, it will return the default value.
   * 
   * @param panelName the panel
	 * @param key the key to look up
	 * @param defaultValue the value to be returned if no value is found
	 * @return the value associated with the given key or the given default value
	 *     if there is no value associated with the key
	 */
	public static byte[] getRaw(DashboardPanels panelName, String key, byte[] defaultValue) {
		return getEntry(panelName, key).getRaw(defaultValue);
	}

	/**
	 * Puts all sendable data to the dashboard.
	 */
	public static synchronized void updateValues() {
		for (Data data : tablesToDataDriverPanel.values()) {
			data.m_builder.updateTable();
		}
		
		for (Data data : tablesToDataMainPanel.values()) {
			data.m_builder.updateTable();
		}
		
		for (Data data : tablesToDataTestPanel.values()) {
			data.m_builder.updateTable();
		}
	}









  //------------------------
	/*public static void putNumber(DashboardPanels panelName, String key, double value){
		NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).setNumber(value);
    }  
    
    public static void putString(DashboardPanels panelName, String key, String value){
		NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).setString(value);
    }  

    public static void putBoolean(DashboardPanels panelName, String key, boolean value){
		NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).setBoolean(value);
    }  
    
    public static double getNumber(DashboardPanels panelName, String key, double defaultValue){
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).getDouble(defaultValue);
    }  
    
    public static String getString(DashboardPanels panelName, String key, String defaultValue){
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).getString(defaultValue);
    } 
    
    public static boolean getBoolean(DashboardPanels panelName, String key, boolean defaultValue){
		return NetworkTableInstance.getDefault().getTable(panelName.toString()).getEntry(key).getBoolean(defaultValue);
	} */
}
