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
public class ContinuousMachine {
    boolean useGlobalRegisters = false;
    boolean resetPcs = false;
    int maxPCs;
    int curPcs = 0;
    ProgramCounter[] pcs;
    RegisterProfile profile;
    float[] registers;
    int execType = 0;
    Engine myEngine;
    public ContinuousMachine(int maxPCs, int numRegisters, Engine myEngine, int execType){
       this.execType = execType;
        pcs = new ProgramCounter[maxPCs];
        pcs[0] = new ProgramCounter(myEngine,0,1.0f,execType, profile);
    }
    public void hardRestart(){
        
    }
    public void softRestart(){
        if(resetPcs){
           curPcs = 0;
           pcs[0] = new ProgramCounter(myEngine,0,1.0f,execType, profile);

        }
    }
}
