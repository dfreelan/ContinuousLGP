/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.program.ContinuousLine;

/**
 *
 * @author dfreelan
 */
public class CombinationalExecutor extends LineExecutor {

    public CombinationalExecutor(RegisterProfile profile){
        super(profile);
    }
    @Override
    ProgramCounter[] doLine(ContinuousLine line, float[] registers) {
        float[][] res1 = generateCommands(line,registers);
        return null;
    }

    private float[][] generateCommands(ContinuousLine line, float[] registers) {
        float[][] lineInfo = line.getLineValues();
        return null;
    }

    
}
