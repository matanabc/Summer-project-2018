/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.Triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class TestSysteamTriger extends Trigger {

  private String NTName;

  public TestSysteamTriger(String NTName){
    this.NTName = NTName;
  }

  @Override
  public boolean get() {
    return SmartDashboard.getBoolean(NTName, false);
  }
}
