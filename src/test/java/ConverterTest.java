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
        try (UsExecutionLazyDeserializer deserializer = new UsExecutionLazyDeserializer()) {
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
        execution.setFrontOffice(createRandomFrontOffice());
        execution.setSourceSystem(createRandomSourceSystem());
        execution.setOrder(createRandomOrder());
        execution.setExchange(createRandomExchange());
        execution.setExecution(createRandomExec());
        return execution;
    }

    private FrontOffice createRandomFrontOffice() {
        FrontOffice frontOffice = new FrontOffice();
        frontOffice.setExecutionId(randomString(10));
        frontOffice.setOrderId(randomString(10));
        return frontOffice;
    }

    private SourceSystem createRandomSourceSystem() {
        SourceSystem sourceSystem = new SourceSystem();
        sourceSystem.setId(randomString(10));
        sourceSystem.setExecutionId(randomString(10));
        sourceSystem.setOrderId(randomString(10));
        return sourceSystem;
    }

    private Order createRandomOrder() {
        Order order = new Order();
        order.setSide(randomEnum(Side.class));
        order.setOrderType(randomEnum(OrderType.class));
        order.setTimeInForce(randomEnum(TimeInForce.class));
        order.setCapacity(randomEnum(Capacity.class));
        order.setInstrumentId(random.nextLong());
        return order;
    }

    private Exchange createRandomExchange() {
        Exchange exchange = new Exchange();
        exchange.setName(randomString(10));
        exchange.setExecutionId(randomString(10));
        exchange.setOrderId(randomString(10));
        return exchange;
    }

    private Execution createRandomExec() {
        Execution execution = new Execution();
        execution.setId(random.nextInt());
        execution.setType(randomEnum(ExecutionType.class));
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
