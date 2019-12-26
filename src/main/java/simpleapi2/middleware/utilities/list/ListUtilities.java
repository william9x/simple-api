package simpleapi2.middleware.utilities.list;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ListUtilities implements IListUtilities{
    @Override
    public ArrayList<?> copyToArrayList(ArrayList<?> arrayListSource, ArrayList<?> arrayListTarget) {



//        for (Object source: arrayListSource ) {
//            Object target = new Object();
//            BeanUtils.copyProperties(source , target);
//            arrayListTarget.add(target);
//        }

        return null;
    }

}
