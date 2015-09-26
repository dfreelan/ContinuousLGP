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
import continuouslgp.program.ContinuousLine;

public abstract class LineExecutor  {
    RegisterProfile profile;
    public LineExecutor(RegisterProfile profile){
        this.profile = profile;
    }
    
    abstract ProgramCounter[] doLine(ContinuousLine line, float[] registers);
}
