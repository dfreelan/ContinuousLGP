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
    int length;
    int readOnlyRegisters;
    boolean[] persistentRegister;
    int totalLinesOfCode;
    Engine engine;
    public RegisterProfile(Engine engine){
        this.engine = engine;
    }
    //the value of the register is fix
    Integer[] readOnlyRegisterValues; //taking advantage of object, since it can be null
   
}
