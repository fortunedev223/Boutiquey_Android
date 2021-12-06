package webkul.opencart.mobikul.helper;

import java.util.HashMap;

/**
 * Created by manish.choudhary on 19/2/18.
 */

public interface RetrofitResponseValid<T,V> {
    void getResponse(HashMap<T,V> getResponse);
    enum T{
        VALID,
        ERROR,
        FAULT
    }
}
