/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.Engine;

/**
 *
 * @author dfreelan
 */
public class RegisterProfile {
    public int length;
    int readOnlyRegisters;
    boolean[] persistentRegister;
 
    public int totalLinesOfCode;
    public Engine engine;
    public RegisterProfile(Engine engine, int totalLinesOfCode){
        this.totalLinesOfCode = totalLinesOfCode;
        this.engine = engine;
    }
    //the value of the register is fix
    Integer[] readOnlyRegisterValues; //taking advantage of object, since it can be null
   
}
