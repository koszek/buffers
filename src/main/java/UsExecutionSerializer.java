import com.example.flatbuffers.*;
import com.google.flatbuffers.FlatBufferBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class UsExecutionSerializer implements Serializer<UsStreetExecution> {

    @Override
    public byte[] serialize(String topic, UsStreetExecution execution) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int frontOffice = serializeFrontOffice(execution.getFrontOffice(), builder);
        int sourceSystem = serializeSourceSystem(execution.getSourceSystem(), builder);
        int order = serializeOrder(execution.getOrder(), builder);
        int exchange = serializeExchange(execution.getExchange(), builder);
        int exec = serializeExecution(execution.getExecution(), builder);
        FbUsStreetExecution.startFbUsStreetExecution(builder);
        FbUsStreetExecution.addFrontOffice(builder, frontOffice);
        FbUsStreetExecution.addSourceSystem(builder, sourceSystem);
        FbUsStreetExecution.addOrder(builder, order);
        FbUsStreetExecution.addExchange(builder, exchange);
        FbUsStreetExecution.addExecution(builder, exec);
        int fbExecution = FbUsStreetExecution.endFbUsStreetExecution(builder);
        builder.finish(fbExecution);
        return builder.sizedByteArray();
    }

    private int serializeFrontOffice(FrontOffice frontOffice, FlatBufferBuilder builder) {
        int executionId = builder.createString(frontOffice.getExecutionId());
        int orderId = builder.createString(frontOffice.getOrderId());
        return FbFrontOffice.createFbFrontOffice(builder, executionId, orderId);
    }

    private int serializeSourceSystem(SourceSystem sourceSystem, FlatBufferBuilder builder) {
        int id = builder.createString(sourceSystem.getId());
        int executionId = builder.createString(sourceSystem.getExecutionId());
        int orderId = builder.createString(sourceSystem.getOrderId());
        return FbSourceSystem.createFbSourceSystem(builder, id, executionId, orderId);
    }

    private int serializeOrder(Order order, FlatBufferBuilder builder) {
        return FbOrder.createFbOrder(builder, (byte) order.getSide().ordinal(), (byte) order.getOrderType().ordinal(),
                (byte) order.getTimeInForce().ordinal(), (byte) order.getCapacity().ordinal(), order.getInstrumentId());
    }

    private int serializeExchange(Exchange exchange, FlatBufferBuilder builder) {
        int name = builder.createString(exchange.getName());
        int executionId = builder.createString(exchange.getExecutionId());
        int orderId = builder.createString(exchange.getOrderId());
        return FbExchage.createFbExchage(builder, name, executionId, orderId);
    }

    private int serializeExecution(Execution execution, FlatBufferBuilder builder) {
      return FbExecution.createFbExecution(builder, execution.getId(), (byte) execution.getType().ordinal(),
              execution.getLastQty(), execution.getLastPrice(), execution.getTransactTime(), execution.getBookId());
    }
}
