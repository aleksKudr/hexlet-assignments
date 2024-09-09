package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method : Address.class.getDeclaredMethods()) {

            // Проверяем, есть ли у метода аннотация @LogExecutionTime
            if (method.isAnnotationPresent(Inspect.class)) {

                var startTime = System.currentTimeMillis();

                try {
                    // Выполняем метод с аннотацией LogExecutionTime
                    method.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                System.out.println("Method " + method.getName() + " returns a value of type " + method.getReturnType().getSimpleName());
            }
        }
        // END
    }
}
