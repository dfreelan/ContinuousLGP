/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp;

import continuouslgp.machine.ContinuousMachine;
import continuouslgp.program.ContinuousProgram;

/**
 *
 * @author dfreelan
 */
public class ContinuousLGP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //public ContinuousProgram(int length, int lineLength, int srcLength, int destLength, int instrType, int srcType, int destType){

        ContinuousProgram prog = new ContinuousProgram(10,4,7,7,0,0,0);
        ContinuousMachine machine = new ContinueousMachine();
    }
    static Operator[] getOperators(){
        
    }
}
