import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConverterTest {

    private Random random = new Random();

    @Test
    public void testSerde() {
        List<UsStreetExecution> executions = createExecutions();
        System.out.println("Original executions");
        for (UsStreetExecution execution : executions) {
            System.out.println(execution);
        }

        List<byte[]> serializedExecutions = new ArrayList<>(executions.size());
        try (UsExecutionSerializer serializer = new UsExecutionSerializer()) {
            for (UsStreetExecution execution : executions) {
                byte[] bytes = serializer.serialize(null, execution);
                serializedExecutions.add(bytes);
            }
        }

        System.out.println("Deserialized executions");
        try (UsExecutionDeserializer deserializer = new UsExecutionDeserializer()) {
            for (byte[] bytes : serializedExecutions) {
                UsStreetExecution execution = deserializer.deserialize(null, bytes);
                System.out.println(execution);
            }
        }

        System.out.println("Lazily deserialized executions");
        try (UsExecutionDeserializer deserializer = new UsExecutionDeserializer()) {
            for (byte[] bytes : serializedExecutions) {
                UsStreetExecution execution = deserializer.deserialize(null, bytes);
                System.out.println(execution);
            }
        }
    }

    private List<UsStreetExecution> createExecutions() {
        List<UsStreetExecution> executions = new ArrayList<>();
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        return executions;
    }

    private UsStreetExecution createRandomExecution() {
        UsStreetExecution execution = new UsStreetExecution();
        execution.setExecutionId(randomString(10));
        execution.setOrderId(randomString(10));
        execution.setSourceSystemId(randomString(10));
        execution.setSide(randomEnum(Side.class));
        execution.setOrderType(randomEnum(OrderType.class));
        execution.setTimeInForce(randomEnum(TimeInForce.class));
        execution.setCapacity(randomEnum(Capacity.class));
        execution.setInstrumentId(random.nextLong());
        execution.setExchangeName(randomString(20));
        execution.setInternalId(random.nextLong());
        execution.setExecutionType(randomEnum(ExecutionType.class));
        execution.setLastQty(random.nextInt());
        execution.setLastPrice(random.nextDouble());
        execution.setTransactTime(random.nextLong());
        execution.setBookId(random.nextLong());
        return execution;
    }

    protected <T extends Enum<T>> T randomEnum(Class<T> enumClass) {
        int pos = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[pos];
    }

    public String randomString(int maxLength) {
        int leftLimit = 97;
        int rightLimit = 122;
        int length = random.nextInt(maxLength + 1);

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
