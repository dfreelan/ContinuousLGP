/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp.alu.controlflow;

import continuouslgp.machine.ProgramCounter;

/**
 *
 * @author dfreelan
 */
public class Goto implements ControlFlow {

    @Override
    public ProgramCounter[] doControlFlow(ProgramCounter current, float[] registers, int src, int dest) {
        int line = (src*registers.length+dest);
        ProgramCounter[] newPCs = new ProgramCounter[1];
        //
        newPCs[0] = new ProgramCounter(current);
        
        newPCs[0].line = line;
        
        return newPCs;
    }
    
}
