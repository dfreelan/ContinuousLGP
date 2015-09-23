/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

/**
 *
 * @author dfreelan
 */
public class Torus extends WeightVector {
    
    float locationOnTorus = 0.0f;
    public Torus(int length) {
        super(length);
    }

    @Override
    public void Initialize() {
        
        float pseudoIndex = generator.nextFloat()*(length+1);
        locationOnTorus = pseudoIndex;
        
        mutateVector = new float[1];
    }
    
    @Override
    public float[] mutate(float strength) {
        mutateVector[0] = generator.nextFloat()*(length+1);
        oldWeights = weights.clone();
        applyMutation(strength);
        return weights;
    }

    @Override
    public float[] changeStrength(float strength) {
        weights = oldWeights.clone();
        applyMutation(strength);
        return weights;
    }
    
    private void applyMutation(float strength) {
        locationOnTorus = mutateVector[0]*(1-strength) + strength*locationOnTorus;
        weights = generateWeightsFromLocation(locationOnTorus);
    }

    private float[] generateWeightsFromLocation(float location){
        float[] arr = new float[length];
        float decimalPart = location - (float)Math.floor(location);
        int integerPart = (int)location;
        arr[integerPart] = 1 - decimalPart;
        arr[(integerPart+1)%length] = decimalPart;
        return arr;
    }
    
}
