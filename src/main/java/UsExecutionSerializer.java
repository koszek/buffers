import com.example.flatbuffers.FBUsStreetExecution;
import com.google.flatbuffers.FlatBufferBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class UsExecutionSerializer implements Serializer<UsStreetExecution> {

    @Override
    public byte[] serialize(String topic, UsStreetExecution execution) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int executionId = builder.createString(execution.getExecutionId());
        int orderId = builder.createString(execution.getOrderId());
        int sourceSystemId = builder.createString(execution.getSourceSystemId());
        int exchangeName = builder.createString(execution.getExchangeName());

        FBUsStreetExecution.startFBUsStreetExecution(builder);
        FBUsStreetExecution.addExecutionId(builder, executionId);
        FBUsStreetExecution.addOrderId(builder, orderId);
        FBUsStreetExecution.addSourceSystemId(builder, sourceSystemId);
        FBUsStreetExecution.addSide(builder, (byte) execution.getSide().ordinal());
        FBUsStreetExecution.addOrderType(builder, (byte) execution.getOrderType().ordinal());
        FBUsStreetExecution.addTimeInForce(builder, (byte) execution.getTimeInForce().ordinal());
        FBUsStreetExecution.addCapacity(builder, (byte) execution.getCapacity().ordinal());
        FBUsStreetExecution.addInstrumentId(builder, execution.getInstrumentId());
        FBUsStreetExecution.addExchangeName(builder, exchangeName);
        FBUsStreetExecution.addInternalId(builder, execution.getInternalId());
        FBUsStreetExecution.addExecutionType(builder, (byte) execution.getExecutionType().ordinal());
        FBUsStreetExecution.addLastQty(builder, execution.getLastQty());
        FBUsStreetExecution.addLastPrice(builder, execution.getLastPrice());
        FBUsStreetExecution.addTransactTime(builder, execution.getTransactTime());
        FBUsStreetExecution.addBookId(builder, execution.getBookId());
        int fbExecution = FBUsStreetExecution.endFBUsStreetExecution(builder);
        builder.finish(fbExecution);
        return builder.sizedByteArray();
    }
}
