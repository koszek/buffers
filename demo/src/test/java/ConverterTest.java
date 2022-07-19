import helper.UsStreetExecutionReportDeserializer;
import helper.UsStreetExecutionReportSerializer;
import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ConverterTest {

    private final Random random = new Random();

    @Test
    public void testSerde() {
        List<UsStreetExecutionReport> executions = createExecutions();

        List<byte[]> serializedExecutions = new ArrayList<>(executions.size());
        try (UsStreetExecutionReportSerializer serializer = new UsStreetExecutionReportSerializer()) {
            for (UsStreetExecutionReport execution : executions) {
                byte[] bytes = serializer.serialize(null, execution);
                serializedExecutions.add(bytes);
            }
        }

        try (UsStreetExecutionReportDeserializer deserializer = new UsStreetExecutionReportDeserializer()) {
            for (int i = 0; i < serializedExecutions.size(); i++) {
                byte[] bytes = serializedExecutions.get(i);
                UsStreetExecutionReport execution = deserializer.deserialize(null, bytes);
                assertEquals(executions.get(i).toString(), execution.toString());
            }
        }

//        try (UsExecutionLazyDeserializer deserializer = new UsExecutionLazyDeserializer()) {
//            for (int i = 0; i < serializedExecutions.size(); i++) {
//                byte[] bytes = serializedExecutions.get(i);
//                UsStreetExecutionReport execution = deserializer.deserialize(null, bytes);
//                assertEquals(executions.get(i).toString(), execution.toString());
//            }
//        }
    }

    private List<UsStreetExecutionReport> createExecutions() {
        List<UsStreetExecutionReport> executions = new ArrayList<>();
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        executions.add(createRandomExecution());
        return executions;
    }

    private UsStreetExecutionReport createRandomExecution() {
        UsStreetExecutionReport execution = new UsStreetExecutionReport();
        execution.setFrontOffice(createRandomFrontOffice());
        execution.setOrder(createRandomOrder());
        execution.setCounterparty(createRandomCounterparty());
        execution.setExecutionReport(createRandomExecutionReport());
        return execution;
    }

    private FrontOffice createRandomFrontOffice() {
        FrontOffice frontOffice = new FrontOffice();
        frontOffice.setExecutionId(randomString(10));
        frontOffice.setOrderId(randomString(10));
        frontOffice.setExecutionSystem(createRandomExecutionSystem());
        return frontOffice;
    }

    private ExecutionSystem createRandomExecutionSystem() {
        ExecutionSystem executionSystem = new ExecutionSystem();
        executionSystem.setExecutionId(randomString(10));
        executionSystem.setClientName(randomString(10));
        executionSystem.setName(randomString(10));
        executionSystem.setOrderId(randomString(10));
        return executionSystem;
    }

    private Counterparty createRandomCounterparty() {
        Counterparty counterparty = new Counterparty();
        counterparty.setCounterpartyType(randomEnum(CounterpartyType.class));
        counterparty.setCode(randomString(10));
        counterparty.setCounterpartyCodeType(randomEnum(CounterpartyCodeType.class));
        counterparty.setExecutionId(randomString(10));
        counterparty.setOrderId(randomString(10));
        return counterparty;
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
        order.setSecurityId(randomString(5));
        order.setInstrumentId(random.nextLong());
        order.setPortfolioCode(randomString(5));
        order.setBookId(random.nextInt());
        return order;
    }

    private Exchange createRandomExchange() {
        Exchange exchange = new Exchange();
        exchange.setName(randomString(10));
        exchange.setExecutionId(randomString(10));
        exchange.setOrderId(randomString(10));
        return exchange;
    }

    private ExecutionReport createRandomExecutionReport() {
        ExecutionReport executionReport = new ExecutionReport();
        executionReport.setId(random.nextInt());
        executionReport.setExecutionType(randomEnum(ExecutionType.class));
        executionReport.setLastQty(random.nextInt());
        executionReport.setLastPrice(random.nextDouble());
        executionReport.setTransactTime(random.nextLong());
        executionReport.setLastMarket(randomString(10));
        return executionReport;
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
