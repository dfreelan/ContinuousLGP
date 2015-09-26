/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

/**
 *
 * @author dfreelan
 */
class RegisterProfile {
    int length;
    int readOnlyRegisters;
    boolean[] persistentRegister;
    
    //the value of the register is fix
    Integer[] readOnlyRegisterValues; //taking advantage of object, since it can be null
   
}
