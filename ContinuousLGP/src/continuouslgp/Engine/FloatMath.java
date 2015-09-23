/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.Engine;

/**
 *
 * @author dfreelan
 */
public class FloatMath {
    public static float[] normalize(float[] arr, float sum){
        float total = 0;
        for(int i = 0; i<arr.length; i++){
            sum+=arr[i];
        }
        for(int i = 0; i<arr.length; i++){
            arr[i] /= (total/sum);
        }
        return arr;
    }
    public static float[] normalize(float[] arr){
        return normalize(arr, 1.0f);
    }
}
