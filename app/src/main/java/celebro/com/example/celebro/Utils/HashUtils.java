package celebro.com.example.celebro.Utils;

import java.util.Locale;

/**
 * Created by 502575517 on 2/10/2016.
 */
public class HashUtils {
    public  static  String computeWeakHash (String string){
        return String.format(Locale.US, "%08x%08x", string.hashCode(), string.length());
    }
}
