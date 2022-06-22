import com.example.flatbuffers.FBUsStreetExecution;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;

public class UsExecutionDeserializer implements Deserializer<UsStreetExecution> {

    @Override
    public UsStreetExecution deserialize(String topic, byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        FBUsStreetExecution fbExecution = FBUsStreetExecution.getRootAsFBUsStreetExecution(byteBuffer);
        UsStreetExecution execution = new UsStreetExecution();
        execution.setExecutionId(fbExecution.executionId());
        execution.setOrderId(fbExecution.orderId());
        execution.setSourceSystemId(fbExecution.sourceSystemId());
        execution.setSide(Side.values()[fbExecution.side()]);
        execution.setOrderType(OrderType.values()[fbExecution.orderType()]);
        execution.setTimeInForce(TimeInForce.values()[fbExecution.timeInForce()]);
        execution.setCapacity(Capacity.values()[fbExecution.capacity()]);
        execution.setInstrumentId(fbExecution.instrumentId());
        execution.setExchangeName(fbExecution.exchangeName());
        execution.setInternalId(fbExecution.internalId());
        execution.setExecutionType(ExecutionType.values()[fbExecution.executionType()]);
        execution.setLastQty(fbExecution.lastQty());
        execution.setLastPrice(fbExecution.lastPrice());
        execution.setTransactTime(fbExecution.transactTime());
        execution.setBookId(fbExecution.bookId());
        return execution;
    }
}
