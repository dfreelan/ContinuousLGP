/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.Engine;
import continuouslgp.program.ContinuousLine;

/**
 *
 * @author dfreelan
 */
public class ProgramCounter {
    LineExecutor exec;
    int line;
    float weight;
    float[] registers;   
    RegisterProfile profile; 
    Engine engine;
    public ProgramCounter[] doInstruction(ContinuousLine line){
        return exec.doLine(line, registers);
    }
    public ProgramCounter(Engine engine, int line, float weight, int executorType, RegisterProfile profile){
        this.line = line;
        this.engine = engine;
        this.profile = profile;
        this.weight = weight;
        exec = getInstance(executorType);
    }
    LineExecutor getInstance(int type){
        switch(type){
            case 0:
                return new CombinationalExecutor(profile);
            default:
                return null;
        }
    }
}
