package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */

    public static void assign (Object to, Object from) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodFrom = "get";
        String methodTo = "set";
        List<String> gettersFrom = getMethods(from.getClass(), methodFrom);
        List<String> settersTo = getMethods(to.getClass(), methodTo);
        gettersFrom.retainAll(settersTo);
        for (String methodName: gettersFrom) {
            Method getterFrom = from.getClass().getMethod(methodFrom.concat(methodName));
            Method setterTo = to.getClass().getMethod(methodTo.concat(methodName), getterFrom.getReturnType());
            setterTo.invoke(to, getterFrom.invoke(from));
        }
    }

    private static List<String> getMethods(Class<?> aClass, String methodName) {
        List<String> arrayOfMethods = new ArrayList<>();
        int beginIndex = methodName.length();
        for (Method method: aClass.getMethods()) {
            if (method.getName().substring(0, beginIndex).equals(methodName)) {
                arrayOfMethods.add(method.getName().substring(beginIndex));
            }
        }
        return arrayOfMethods;
    }
}
