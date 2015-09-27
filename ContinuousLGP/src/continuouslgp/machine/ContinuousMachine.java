/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.Engine;
import continuouslgp.program.ContinuousProgram;

/**
 *
 * @author dfreelan
 */
public class ContinuousMachine {
    boolean useGlobalRegisters = false;
    boolean resetPcs = false;
    int maxPCs;
    int curPcs = 0;
    public ProgramCounter[] pcs;
    RegisterProfile profile;
    float[] registers;
    int execType = 0;
    ContinuousProgram program;
    public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        this.execType = execType;
        this.profile = profile;
        this.maxPCs = maxPCs;
        this.program = p;
        pcs = new ProgramCounter[maxPCs];
        profile.length = numRegisters;
        pcs[0] = new ProgramCounter(0,1.0f,execType, profile);
    
    }
    public void mutate(float strength){
        program.mutate(strength);
    }
    public void changeStrength(float strength){
        program.changeStrength(strength);
    }
    public void doStep(){
        for(ProgramCounter pc: pcs){
            pc.doInstruction(program.lines[pc.line]);
        }
    }
    public void hardRestart(){
        pcs[0] = new ProgramCounter(0,1.0f,execType, profile);
    }
    public void softRestart(){
        if(resetPcs){
           curPcs = 1;
           pcs[0] = new ProgramCounter(0,1.0f,execType, profile);

        }
    }
}
