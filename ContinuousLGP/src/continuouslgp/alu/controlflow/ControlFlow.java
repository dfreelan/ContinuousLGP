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
public interface ControlFlow {
    public ProgramCounter[] doControlFlow(ProgramCounter current, float[] registers, int src, int dest);
}
