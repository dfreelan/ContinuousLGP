/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

import continuouslgp.Engine.FloatMath;

/**
 *
 * @author dfreelan
 */
public class NaiiveSimplex extends Simplex{

    public NaiiveSimplex(int length) {
        super(length);
    }
    
    @Override
    float[] getUniformSimplexSample(){
        float weights[] = new float[length];
        float sum = 0;
        
        for (int i = 0; i < length; i++)
        {
            weights[i] = generator.nextFloat();
        }
        
        FloatMath.normalize(weights);
        
        return weights;
    }
    public void applyMutation(float strength) {
       
        
        //applies the mutation part
        for(int i = 0; i<weights.length; i++){
            weights[i] = weights[i]*(1-strength) + mutateVector[i]*(strength);
            if(weights[i]<0.0f){
                System.err.println("weights were bad");
                System.exit(0);
            }
        }
        //in case of floating point error and such, renormalize.
         FloatMath.normalize(weights);
       
    }
    
}
